package com.example.template_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.template_project.R;
import com.example.template_project.model.Seat;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {
    private List<Seat> mListSeat;
    private Context context;

    public SeatAdapter(List<Seat> mListSeat, Context context) {
        this.mListSeat = mListSeat;
        this.context = context;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_seat, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        Seat seat = mListSeat.get(position);
        holder.btnSeat.setText(seat.getSeatName());
        holder.btnSeat.setSelected(seat.isSelected());

        holder.btnSeat.setOnClickListener(v -> {
            seat.setSelected(!seat.isSelected());
            holder.btnSeat.setSelected(seat.isSelected());
        });
    }

    @Override
    public int getItemCount() {
        if(mListSeat != null){
            return mListSeat.size();
        }
        return 0;
    }

    public class SeatViewHolder extends RecyclerView.ViewHolder{
        Button btnSeat;
        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            btnSeat = itemView.findViewById(R.id.btn_seat);
        }
    }
}
