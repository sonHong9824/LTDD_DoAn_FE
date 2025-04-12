package com.example.template_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.template_project.R;
import com.example.template_project.model.Cinema;
import com.example.template_project.model.MovieSummary;

import java.util.List;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.ViewHolder>{
    private List<Cinema> cinemaList;
    private OnCinemaClickListener listener;
    public interface OnCinemaClickListener {
        void onMovieClick(Cinema cinema);
    }
    public CinemaAdapter(List<Cinema> cinemaList) {
        this.cinemaList = cinemaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cinema_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cinema cinema= cinemaList.get(position);
        holder.name.setText(cinema.getName());
        holder.location.setText(cinema.getLocation());
    }

    @Override
    public int getItemCount() {
        return (cinemaList != null) ? cinemaList.size() : 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtCinemaName);
            location = itemView.findViewById(R.id.txtCinemaLocation);
        }
    }
}
