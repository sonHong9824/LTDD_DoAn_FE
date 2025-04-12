package com.example.template_project.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.template_project.R;
import com.example.template_project.fragment.SeatFragment;
import com.example.template_project.model.Showtime;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ViewHolder>{
    List<Showtime> showtimes;

    public ShowtimeAdapter(List<Showtime> showtimes) {
        this.showtimes = showtimes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showtime, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Showtime showtime = showtimes.get(position);
        if (showtime == null)
        {
            return;
        }
        holder.txt_start_time.setText(showtime.showtimeStart());
        holder.txt_end_time.setText(showtime.showtimeEnd());

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();

            Toast.makeText(context, "Suất chiếu: " + showtime.getShowtime(), Toast.LENGTH_SHORT).show();

            // Truyền dữ liệu vào SeatFragment
            SeatFragment seatFragment = new SeatFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("SHOWTIME_DATA", showtime);
            seatFragment.setArguments(bundle);

            // Chuyển fragment
            ((AppCompatActivity) context).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, seatFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }
    @Override
    public int getItemCount() {
        return showtimes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_start_time;
        private TextView txt_end_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_start_time = itemView.findViewById(R.id.tv_showtimeStart);
            txt_end_time = itemView.findViewById(R.id.tv_showtimeEnd);
        }
    }
}
