package com.example.template_project.fragment;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.template_project.R;
import com.example.template_project.adapter.DateAdapter;
import com.example.template_project.adapter.ShowtimeMovieAdapter;
import com.example.template_project.model.Cinema;
import com.example.template_project.model.DateItem;
import com.example.template_project.model.MovieShowtime;
import com.example.template_project.retrofit.RetrofitService;
import com.example.template_project.retrofit.ShowtimeApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieShowtimeFragment extends Fragment {
    private DateAdapter dateAdapter;
    private RecyclerView rcShowtimes,recyclerViewDates;
    private ShowtimeMovieAdapter showtimeMovieAdapter;
    private static final String ARG_CINEMA = "CINEMA";
    private TextView tv_noShowtime;
    private Cinema cinema;

    public static MovieShowtimeFragment newInstance(Cinema cinema) {
        MovieShowtimeFragment fragment = new MovieShowtimeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CINEMA, cinema);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_showtime_movie, container, false);

        tv_noShowtime = view.findViewById(R.id.tv_NoShowtime);
        recyclerViewDates = view.findViewById(R.id.recyclerViewDates);
        rcShowtimes = view.findViewById(R.id.recyclerShowtimes);

        recyclerViewDates.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcShowtimes.setLayoutManager(new LinearLayoutManager(getContext()));
        List<DateItem> dateList = generateDateList();
        if (getArguments() != null) {
            cinema = (Cinema) getArguments().getSerializable(ARG_CINEMA);
            if (cinema != null) {
                Log.d("MovieShowtimeFragment", "Cinema ID: " + cinema.getId());
            } else {
                Log.d("MovieShowtimeFragment", "Cinema object is null!");
            }
        }
        dateAdapter = new DateAdapter(requireContext(), dateList, selectedDate -> {
            loadShowtimes(cinema.getId(),selectedDate);
        });

        DateItem firstDate = dateList.get(0);
        loadShowtimes(cinema.getId(), firstDate.getDate());
        recyclerViewDates.setAdapter(dateAdapter);

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

    private void loadShowtimes(String cinemaId, String date)
    {
        RetrofitService retrofitService = new RetrofitService();
        ShowtimeApi showtimeApi = retrofitService.getRetrofit().create(ShowtimeApi.class);
        showtimeApi.getShowtimesByCinemaAndDate(cinemaId, date).enqueue(new Callback<List<MovieShowtime>>() {
            @Override
            public void onResponse(Call<List<MovieShowtime>> call, Response<List<MovieShowtime>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MovieShowtime> showtimes = response.body();
                    if (showtimes.isEmpty()) {
                        tv_noShowtime.setVisibility(View.VISIBLE); // Hiển thị thông báo
                        rcShowtimes.setVisibility(View.GONE); // Ẩn RecyclerView
                        return;
                    } else {
                        tv_noShowtime.setVisibility(View.GONE); // Ẩn thông báo
                        rcShowtimes.setVisibility(View.VISIBLE); // Hiển thị RecyclerView
                    }
                    showtimeMovieAdapter = new ShowtimeMovieAdapter(showtimes);
                    rcShowtimes.setAdapter(showtimeMovieAdapter);
                } else {
                    Log.e(TAG, "Phản hồi không hợp lệ");
                }
            }

            @Override
            public void onFailure(Call<List<MovieShowtime>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load showtimes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
