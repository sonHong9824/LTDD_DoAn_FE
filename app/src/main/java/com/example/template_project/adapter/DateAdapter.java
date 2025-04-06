package com.example.template_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.template_project.R;
import com.example.template_project.model.DateItem;

import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {
    private Context context;
    private List<DateItem> dateList;
    private OnDateClickListener listener;
    private int selectedPosition = 0;
    public interface OnDateClickListener {
        void onDateClick(String date);
    }

    public DateAdapter(Context context, List<DateItem> dateList, OnDateClickListener listener) {
        this.context = context;
        this.dateList = dateList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        DateItem dateItem = dateList.get(position);
        holder.tvDayOfWeek.setText(dateItem.getDisplayDate().split(",")[0]);
        holder.tvDate.setText(dateItem.getDisplayDate().split(",")[1]);

        // Kiểm tra vị trí đang chọn bằng getAdapterPosition()
        boolean isSelected = (holder.getBindingAdapterPosition() == selectedPosition);

        if(isSelected){
            holder.tvDayOfWeek.setBackgroundResource(R.drawable.data_selected_top);
            holder.tvDayOfWeek.setTextColor(ContextCompat.getColor(context, R.color.date));
            holder.tvDate.setBackgroundResource(R.drawable.date_selected);
            holder.tvDate.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.viewIndicator.setBackgroundColor(ContextCompat.getColor(context, R.color.date));  // Màu khi chọn
            holder.viewIndicator.setVisibility(View.VISIBLE);
        } else {
            holder.tvDayOfWeek.setBackgroundResource(R.drawable.date_normal_top);
            holder.tvDayOfWeek.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.tvDate.setBackgroundResource(R.drawable.date_normal);
            holder.tvDate.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.viewIndicator.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            int adapterPosition =  holder.getBindingAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                selectedPosition = adapterPosition;
                notifyDataSetChanged();
                listener.onDateClick(dateItem.getDate());
            }
        });
    }


    @Override
    public int getItemCount() {
        if(dateList != null){
            return dateList.size();
        }
        return 0;
    }

    public class DateViewHolder extends RecyclerView.ViewHolder{
        TextView tvDayOfWeek, tvDate;
        View viewIndicator;
        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayOfWeek = itemView.findViewById(R.id.tv_day_of_week);
            tvDate = itemView.findViewById(R.id.tv_date);
            viewIndicator = itemView.findViewById(R.id.view_indicator);
        }
    }
}
