package com.example.template_project.adapter;

import android.content.Context;
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
import com.example.template_project.model.Genre;
import com.example.template_project.model.MovieSummary;

import java.util.ArrayList;
import java.util.List;

public class MovieBookingAdapter extends RecyclerView.Adapter<MovieBookingAdapter.ViewHolder> {
    private List<MovieSummary> mListMove;

    public MovieBookingAdapter(List<MovieSummary> mListMove) {
        this.mListMove = mListMove;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movie_booking, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieSummary movieSummary = mListMove.get(position);
        if(movieSummary == null){
            return;
        }
        Glide.with(holder.imgMoive.getContext())
                .load(movieSummary.getMovie().getPosterUrl()) // URL của ảnh
                .placeholder(R.drawable.placeholder_img) // Ảnh mặc định khi tải
                .error(R.drawable.error_img) // Ảnh lỗi nếu URL sai
                .into(holder.imgMoive); // Đưa vào ImageView
        Log.d("MovieAdapter", "Image URL: " + movieSummary.getMovie().getPosterUrl());
        holder.txt_title.setText(movieSummary.getMovie().getTitle());

        List<Genre> genres = movieSummary.getMovie().getGenres();
        // Chuyển List<Genre> thành List<String> chứa tên thể loại
        List<String> genreNames = new ArrayList<>();
        for (Genre genre : genres) {
            genreNames.add(genre.getName()); // Lấy tên thể loại
        }
        holder.txt_genre.setText(String.format("Khởi chiếu: %s", TextUtils.join(", ", genreNames)));
        holder.txt_releaseDate.setText(String.format("Khởi chiếu: %s", movieSummary.getMovie().getReleaseDate()));
        holder.txt_duration.setText(String.format("Thời lượng: %s phút", movieSummary.getMovie().getDuration()));

        String scope = movieSummary.getMovie().getScope();
        int drawableRes;
        if ("16".equals(scope)) { 
            drawableRes = R.drawable.anh16;
        } else if ("18".equals(scope)) {
            drawableRes = R.drawable.anh18;
        } else {
            drawableRes = R.drawable.error_img;
        }

        holder.imgScope.setImageResource(drawableRes);
    }

    @Override
    public int getItemCount() {
        return mListMove.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgMoive;
        private ImageView imgScope;
        private TextView txt_title;
        private TextView txt_releaseDate;
        private TextView txt_duration;
        private TextView txt_genre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMoive = itemView.findViewById(R.id.imageMovieView);
            imgScope = itemView.findViewById(R.id.imageMovieScope);
            txt_title = itemView.findViewById(R.id.txtMovieTitle);
            txt_releaseDate = itemView.findViewById(R.id.txtMovieReleaseDate);
            txt_duration = itemView.findViewById(R.id.txtMovieDuration);
            txt_genre = itemView.findViewById(R.id.txtMovieGenre);
        }
    }
}
