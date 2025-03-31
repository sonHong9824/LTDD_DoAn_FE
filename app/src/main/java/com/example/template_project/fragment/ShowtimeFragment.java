package com.example.template_project.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.template_project.R;
import com.example.template_project.adapter.DateAdapter;
import com.example.template_project.adapter.ExShowtimeAdapter;
import com.example.template_project.model.Cinema;
import com.example.template_project.model.DateItem;
import com.example.template_project.model.Movie;
import com.example.template_project.model.MovieSummary;
import com.example.template_project.model.Showtime;
import com.example.template_project.retrofit.MovieApi;
import com.example.template_project.retrofit.RetrofitService;
import com.example.template_project.retrofit.ShowtimeApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowtimeFragment extends Fragment {
    private Movie movie;
    private ExpandableListView expandableListView;
    private List<Cinema> mListCinema;
    private Map<Cinema, List<Showtime>> mListShowtime;
    private ExShowtimeAdapter exShowtimeAdapter;
    private DateAdapter dateAdapter;
    private TextView tv_noShowtime, tv_movie;
    private ImageButton btn_back;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_showtime, container, false);

        // Nhận dữ liệu từ Bundle
        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable("MOVIE_DATA"); // Gán dữ liệu cho biến toàn cục
            if (movie != null) {
                Log.d("ShowtimeFragment", "Movie Title: " + movie.getTitle());
            } else {
                Log.e("ShowtimeFragment", "Movie is NULL!");
            }
        } else {
            Log.e("ShowtimeFragment", "Arguments are NULL!");
        }

        RecyclerView recyclerViewDates = view.findViewById(R.id.recyclerViewDates);
        recyclerViewDates.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        expandableListView = view.findViewById(R.id.ex_showtime);
        tv_noShowtime = view.findViewById(R.id.tv_NoShowtime);
        btn_back = view.findViewById(R.id.btn_back_showtime);
        btn_back.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        tv_movie = view.findViewById(R.id.tv_movie_showtime);
        tv_movie.setText(movie.getTitle());

        List<DateItem> dateList = generateDateList();
        dateAdapter = new DateAdapter(requireContext(), dateList, selectedDate -> {
            fetchShowtimes(selectedDate); // Gọi API khi chọn ngày
        });
        recyclerViewDates.setAdapter(dateAdapter);


        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            Cinema cinema = mListCinema.get(groupPosition);
            Showtime showtime = mListShowtime.get(cinema).get(childPosition);

            Toast.makeText(getContext(), "Suất chiếu: " + showtime.getShowtime() + " - Rạp: " + cinema.getName(), Toast.LENGTH_SHORT).show();

            SeatFragment seatFragment = new SeatFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("SHOWTIME_DATA", showtime);
            seatFragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, seatFragment)
                    .addToBackStack(null)
                    .commit();

            return true;
        });
        return view;
    }

    private List<DateItem> generateDateList() {
        List<DateItem> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < 14; i++) {
            Date date = calendar.getTime();
            String apiDate = DateItem.formatDate(date, "yyyy-MM-dd"); // Format để gửi API
            String displayDate = DateItem.formatDate(date, "EEE, dd/MM"); // Hiển thị: T2, 25/03

            dateList.add(new DateItem(apiDate, displayDate));
            calendar.add(Calendar.DAY_OF_MONTH, 1); // Tiến tới ngày tiếp theo
        }

        return dateList;
    }
    private void fetchShowtimes(String date) {
        if (movie == null) {
            Log.e("ShowtimeFragment", "fetchShowtimes: Movie is NULL!");
            return; // Không gọi API nếu movie null
        }

        String movieId = movie.getId();

        RetrofitService retrofitService = new RetrofitService();
        ShowtimeApi api = retrofitService.getRetrofit().create(ShowtimeApi.class);

        api.getShowtimes_group(movieId, date).enqueue(new Callback<List<Showtime>>() {
            @Override
            public void onResponse(Call<List<Showtime>> call, Response<List<Showtime>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Showtime> showtimes = response.body();
                    if (showtimes.isEmpty()) {
                        tv_noShowtime.setVisibility(View.VISIBLE); // Hiển thị thông báo
                        expandableListView.setVisibility(View.GONE);
                        return;
                    } else {
                        tv_noShowtime.setVisibility(View.GONE);
                        expandableListView.setVisibility(View.VISIBLE);
                    }

                    if (mListShowtime == null) {
                        mListShowtime = new HashMap<>();
                    } else {
                        mListShowtime.clear();
                    }

                    if (mListCinema == null) {
                        mListCinema = new ArrayList<>();
                    } else {
                        mListCinema.clear();
                    }

                    for (Showtime showtime : response.body()) {
                        Cinema cinema = showtime.getCinema();

                        // Kiểm tra xem rạp phim đã có trong danh sách chưa
                        if (!mListShowtime.containsKey(cinema)) {
                            mListShowtime.put(cinema, new ArrayList<>());
                            mListCinema.add(cinema);
                        }

                        // Thêm suất chiếu vào danh sách của rạp tương ứng
                        mListShowtime.get(cinema).add(showtime);
                    }

                    // Cập nhật Adapter
                    exShowtimeAdapter = new ExShowtimeAdapter(mListCinema, mListShowtime);
                    expandableListView.setAdapter(exShowtimeAdapter);
                } else {
                    Log.e("API_RESPONSE", "Không có dữ liệu! Code: " + response.code());
                    Toast.makeText(getContext(), "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Showtime>> call, Throwable t) {
                Log.e("API_ERROR", "Lỗi kết nối: " + t.getMessage());
                Toast.makeText(getContext(), "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
