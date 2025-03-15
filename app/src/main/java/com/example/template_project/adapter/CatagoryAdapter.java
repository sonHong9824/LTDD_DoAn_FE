package com.example.template_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.template_project.R;
import com.example.template_project.model.Catagory;

import java.util.List;

public class CatagoryAdapter extends RecyclerView.Adapter<CatagoryAdapter.CatagoryViewHolder>{
    private Context mContext;
    private List<Catagory> mListCatagory;

    public CatagoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Catagory> list){
        this.mListCatagory = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CatagoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catagory, parent, false);
        return new CatagoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatagoryViewHolder holder, int position) {
        Catagory catagory = mListCatagory.get(position);
        if(catagory == null){
            return;
        }
        holder.txt_name.setText(catagory.getNameCatagory());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL, false);
        holder.rcvShowing.setLayoutManager(linearLayoutManager);

        MovieShowingAdapter movieShowingminAdapter = new MovieShowingAdapter();
        movieShowingminAdapter.setData(catagory.getMovies());
        holder.rcvShowing.setAdapter(movieShowingminAdapter);

    }

    @Override
    public int getItemCount() {
        if(mListCatagory != null){
            return mListCatagory.size();
        }
        return 0;
    }

    public class CatagoryViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_name;
        private RecyclerView rcvShowing;

        public CatagoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_catagory);
            rcvShowing = itemView.findViewById(R.id.rcv_showing);
        }
    }
}
