package com.example.template_project.adapter;

import android.text.TextUtils;
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

import java.util.List;

public class MovieShowingminAdapter extends RecyclerView.Adapter<MovieShowingminAdapter.MovieShowingminViewHolder> {
    private List<Movie> mListMove;

    public void setData(List<Movie> list){
        this.mListMove = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieShowingminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showing, parent,false);
        return new MovieShowingminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieShowingminViewHolder holder, int position) {
        Movie movie = mListMove.get(position);
        if(movie == null){
            return;
        }
        Glide.with(holder.imgMoive.getContext())
                .load(movie.getPosterUrl()) // URL của ảnh
                .placeholder(R.drawable.placeholder_img) // Ảnh mặc định khi tải
                .error(R.drawable.error_img) // Ảnh lỗi nếu URL sai
                .into(holder.imgMoive); // Đưa vào ImageView
        Log.d("MovieAdapter", "Image URL: " + movie.getPosterUrl());

        holder.txt_name.setText(movie.getTitle());

        List<String> genres = movie.getGenre();
        holder.txt_genre.setText(TextUtils.join(", ", genres));
    }

    @Override
    public int getItemCount() {
        if(mListMove != null){
            return mListMove.size();
        }
        return 0;
    }

    public class MovieShowingminViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgMoive;
        private TextView txt_name;
        private TextView txt_genre;

        public MovieShowingminViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMoive = itemView.findViewById(R.id.img_showing);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_genre = itemView.findViewById(R.id.txt_genre);

        }
    }
}
