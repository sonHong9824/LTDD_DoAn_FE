package com.example.template_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.template_project.R;
import com.example.template_project.model.Seat;
import com.example.template_project.model.Ticket;

import java.util.List;
import java.util.zip.Inflater;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
    private List<Ticket> mListTicket;
    private Context context;
    private OnTicketClickListener listener;
    public interface OnTicketClickListener {
        void onTicketClick(Ticket ticket);
    }

    public TicketAdapter(List<Ticket> mListTicket, Context context, OnTicketClickListener listener) {
        this.mListTicket = mListTicket;
        this.context = context;
        this.listener = listener;
    }

    public TicketAdapter(List<Ticket> mListTicket, Context context) {
        this.mListTicket = mListTicket;
        this.context = context;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = mListTicket.get(position);
        holder.tv_name.setText(ticket.getShowtime().getMovie().getTitle());
        holder.tv_cinema.setText(ticket.getShowtime().getCinema().getName());
        holder.tv_time.setText(ticket.getShowtime().getDateFormatted());

        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onTicketClick(ticket);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mListTicket != null){
            return mListTicket.size();
        }
        return 0;
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name, tv_cinema, tv_time;
        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name_ticket);
            tv_cinema = itemView.findViewById(R.id.tv_cinema_ticket);
            tv_time = itemView.findViewById(R.id.tv_time_ticket);
        }
    }
}
