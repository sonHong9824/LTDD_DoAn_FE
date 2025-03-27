package com.example.template_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.template_project.R;
import com.example.template_project.model.Seat;

import java.util.ArrayList;
import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {
    private List<Seat> mListSeat;
    private Context context;
    private List<Seat> selectedSeats = new ArrayList<>(); // Danh sách ghế đã chọn


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

        boolean isDef = seat.getSeatName().startsWith("A") || seat.getSeatName().startsWith("B"); // Kiểm tra ghế VIP
        boolean isSelected = selectedSeats.contains(seat); // Kiểm tra ghế có được chọn không

        if (isSelected) {
            holder.btnSeat.setBackgroundResource(R.drawable.seat_select);
            holder.btnSeat.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            if (isDef) {
                holder.btnSeat.setBackgroundResource(R.drawable.seat_selector);
                holder.btnSeat.setTextColor(ContextCompat.getColor(context, R.color.default_text_color));
            } else {
                holder.btnSeat.setBackgroundResource(R.drawable.seat_vip_selector);
                holder.btnSeat.setTextColor(ContextCompat.getColor(context, R.color.vip_text_color));
            }
        }

        // Xử lý sự kiện khi nhấn vào ghế
        holder.btnSeat.setOnClickListener(v -> {
            if (selectedSeats.contains(seat)) {
                selectedSeats.remove(seat); // Nếu đã chọn, thì bỏ chọn
            } else {
                selectedSeats.add(seat); // Nếu chưa chọn, thì thêm vào danh sách
            }
            notifyItemChanged(position); // Cập nhật lại giao diện ghế
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
        TextView btnSeat;
        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            btnSeat = itemView.findViewById(R.id.btn_seat);
        }
    }
}
