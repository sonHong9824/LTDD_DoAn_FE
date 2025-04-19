package com.example.template_project.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.template_project.R;
import com.example.template_project.model.Movie;
import com.example.template_project.model.MovieSummary;

import java.util.List;

public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.FeatureViewHolder> {

    private final List<MovieSummary> mListMovie;

    public FeatureAdapter(List<MovieSummary> mListMovie) {
        this.mListMovie = mListMovie;
    }

    @NonNull
    @Override
    public FeatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feature, parent, false);
        return new FeatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeatureViewHolder holder, int position) {
        MovieSummary movie = mListMovie.get(position);
        if(movie == null){
            return;
        }
        Glide.with(holder.img_feature.getContext())
                .load(movie.getMovie().getPosterUrl()) // URL của ảnh
                .placeholder(R.drawable.placeholder_img) // Ảnh mặc định khi tải
                .error(R.drawable.error_img) // Ảnh lỗi nếu URL sai
                .into(holder.img_feature); // Đưa vào ImageView
        Log.d("MovieAdapter", "Image URL: " + movie.getMovie().getPosterUrl());
        holder.txt_title.setText(movie.getMovie().getTitle());

        holder.itemView.setScaleX(0.8f);  // Thu nhỏ theo chiều ngang
        holder.itemView.setScaleY(0.8f);

    }

    @Override
    public int getItemCount() {
        if(mListMovie != null){
            return mListMovie.size();
        }
        return 0;
    }

    public static class FeatureViewHolder extends RecyclerView.ViewHolder{
         private final ImageView img_feature;
         private final TextView txt_title;
        public FeatureViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_feature);
            img_feature = itemView.findViewById(R.id.img_feature);

        }
    }
}
