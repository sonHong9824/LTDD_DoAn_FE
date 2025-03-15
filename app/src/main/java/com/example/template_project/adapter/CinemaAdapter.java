package com.example.template_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.template_project.R;
import com.example.template_project.model.Cinema;

import java.util.List;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaHolder>{
    private List<Cinema> cinemaList;

    public CinemaAdapter(List<Cinema> cinemaList) {
        this.cinemaList = cinemaList;
    }

    @NonNull
    @Override
    public CinemaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cinema_booking, parent, false);
        return new CinemaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CinemaHolder holder, int position) {
        Cinema cinema= cinemaList.get(position);
        holder.name.setText(cinema.getName());
        holder.location.setText(cinema.getLocation());
    }

    @Override
    public int getItemCount() {
        return (cinemaList != null) ? cinemaList.size() : 0;
    }

}
