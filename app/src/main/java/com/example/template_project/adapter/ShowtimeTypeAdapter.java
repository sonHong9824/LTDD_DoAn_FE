package com.example.template_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.template_project.R;
import com.example.template_project.model.ShowtimeType;

import java.util.List;

public class ShowtimeTypeAdapter extends RecyclerView.Adapter<ShowtimeTypeAdapter.ViewHolder>
{
    private List<ShowtimeType> showtimeGroups;

    public ShowtimeTypeAdapter(List<ShowtimeType> showtimeGroups) {
        this.showtimeGroups = showtimeGroups;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showtime_type, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShowtimeType showtimeType = showtimeGroups.get(position);
        if (showtimeType == null)
        {
            return;
        }
        holder.txt_type.setText(showtimeType.getLanguageFormat());
        ShowtimeAdapter showtimeAdapter = new ShowtimeAdapter(showtimeType.getShowtimes());
        holder.rc_showtime.setLayoutManager(new GridLayoutManager(holder.itemView.getContext(), 2, GridLayoutManager.VERTICAL, false));
        holder.rc_showtime.setNestedScrollingEnabled(false);
        holder.rc_showtime.setAdapter(showtimeAdapter);
    }

    @Override
    public int getItemCount() {
        return showtimeGroups.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_type;
        private RecyclerView rc_showtime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_type = itemView.findViewById(R.id.textType);
            rc_showtime = itemView.findViewById(R.id.recyclerShowtime);
        }
    }
}
