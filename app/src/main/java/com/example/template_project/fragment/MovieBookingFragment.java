package com.example.template_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.template_project.MainActivity;
import com.example.template_project.R;
import com.example.template_project.adapter.CinemaAdapter;
import com.example.template_project.adapter.MovieBookingAdapter;
import com.example.template_project.adapter.MovieShowingAdapter;
import com.example.template_project.model.Cinema;
import com.example.template_project.model.MovieSummary;
import com.example.template_project.retrofit.MovieApi;
import com.example.template_project.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieBookingFragment extends Fragment {


    RecyclerView recyclerView;
    MovieBookingAdapter movieBookingAdapter;
    List<MovieSummary> movieSummaryList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_booking, container, false);
        recyclerView = view.findViewById(R.id.movieList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadMocvies();
        return view;
    }
    private void loadMocvies()
    {
        RetrofitService retrofitService = new RetrofitService();
        MovieApi movieApi = retrofitService.getRetrofit().create(MovieApi.class);
        movieApi.getNowShowingMovies().enqueue(new Callback<List<MovieSummary>>() {
            @Override
            public void onResponse(Call<List<MovieSummary>> call, Response<List<MovieSummary>> response) {
                movieSummaryList = response.body();
                movieBookingAdapter = new MovieBookingAdapter(movieSummaryList, new MovieBookingAdapter.OnMovieClickListener() {
                    @Override
                    public void onMovieClick(MovieSummary movieSummary) {
                        MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance(movieSummary);
                        ((MainActivity) requireActivity()).replaceFragment(movieDetailFragment);
                    }
                });
                recyclerView.setAdapter(movieBookingAdapter);
            }

            @Override
            public void onFailure(Call<List<MovieSummary>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load movies", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
