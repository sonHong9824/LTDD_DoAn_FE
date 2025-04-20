package com.example.template_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.template_project.MainActivity;
import com.example.template_project.R;
import com.example.template_project.adapter.CinemaAdapter;
import com.example.template_project.model.Cinema;
import com.example.template_project.retrofit.CinemaApi;
import com.example.template_project.retrofit.RetrofitService;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CinemaBookingFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchView searchView;
    List<Cinema> cinemaList;
    CinemaAdapter cinemaAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cinema_booking, container, false);
        recyclerView = view.findViewById(R.id.cinemaList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadCinemas();

        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListener(newText);
                return true;
            }
        });


        return view;
    }

    private void loadCinemas() {
        RetrofitService retrofitService = new RetrofitService();
        CinemaApi cinemaApi = retrofitService.getRetrofit().create(CinemaApi.class);
        cinemaApi.getAllCinemas().enqueue(new Callback<List<Cinema>>() {
            @Override
            public void onResponse(Call<List<Cinema>> call, Response<List<Cinema>> response) {
                cinemaList = response.body();
                populateListView(cinemaList);
            }

            @Override
            public void onFailure(Call<List<Cinema>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load cinemas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateListView(List<Cinema> cinemaList) {
        cinemaAdapter = new CinemaAdapter(cinemaList);
        recyclerView.setAdapter(cinemaAdapter);
        cinemaAdapter.setOnCinemaClickListener(new CinemaAdapter.OnCinemaClickListener() {
            @Override
            public void onCinemaClick(Cinema cinema) {
                MovieShowtimeFragment movieShowtimeFragment = MovieShowtimeFragment.newInstance(cinema);
                ((MainActivity) requireActivity()).replaceFragment(movieShowtimeFragment);
            }
        });
    }
    private void filterListener(String text) {
        List<Cinema> list = new ArrayList<>();
        for (Cinema cinema : cinemaList) {
            if (cinema.getName().toLowerCase().contains(text.toLowerCase())) {
                list.add(cinema);
            }
        }
        cinemaAdapter.setListenerList(list);
    }
}
