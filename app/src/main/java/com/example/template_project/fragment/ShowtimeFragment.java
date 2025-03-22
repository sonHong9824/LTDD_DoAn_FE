package com.example.template_project.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.template_project.R;
import com.example.template_project.adapter.ExShowtimeAdapter;
import com.example.template_project.model.Cinema;
import com.example.template_project.model.Movie;
import com.example.template_project.model.MovieSummary;
import com.example.template_project.model.Showtime;
import com.example.template_project.retrofit.MovieApi;
import com.example.template_project.retrofit.RetrofitService;
import com.example.template_project.retrofit.ShowtimeApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

        expandableListView = view.findViewById(R.id.ex_showtime);
        fetchShowtimes(); // Gọi fetchShowtimes() sau khi gán dữ liệu

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Cinema cinema = mListCinema.get(groupPosition);
                Showtime showtime = mListShowtime.get(cinema).get(childPosition);

                // Hiển thị thông tin suất chiếu khi click
                Toast.makeText(getContext(), "Suất chiếu: " + showtime.getShowtime() + " - Rạp: " + cinema.getName(), Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        return view;
    }

    private void fetchShowtimes() {
        if (movie == null) {
            Log.e("ShowtimeFragment", "fetchShowtimes: Movie is NULL!");
            return; // Không gọi API nếu movie null
        }

        String movieId = movie.getId();
        String date = "2025-03-22";

        RetrofitService retrofitService = new RetrofitService();
        ShowtimeApi api = retrofitService.getRetrofit().create(ShowtimeApi.class);

        api.getShowtimes_group(movieId, date).enqueue(new Callback<List<Showtime>>() {
            @Override
            public void onResponse(Call<List<Showtime>> call, Response<List<Showtime>> response) {
                if (response.isSuccessful() && response.body() != null) {
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
