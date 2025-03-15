package com.example.template_project.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.template_project.R;


public class CinemaHolder extends RecyclerView.ViewHolder{
    TextView name, location;

    public CinemaHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.txtCinemaName);
        location = itemView.findViewById(R.id.txtCinemaLocation);
    }
}
