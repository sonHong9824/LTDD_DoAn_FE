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
import com.example.template_project.model.Genre;
import com.example.template_project.model.MovieSummary;

import java.util.ArrayList;
import java.util.List;

public class MovieShowingAdapter extends RecyclerView.Adapter<MovieShowingAdapter.MovieShowingminViewHolder> {
    private List<MovieSummary> mListMove;
    private OnMovieClickListener listener;
    // Constructor nhận listener
    public MovieShowingAdapter(OnMovieClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<MovieSummary> list){
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

        holder.txt_name.setText(movieSummary.getMovie().getTitle());
        holder.txt_scope.setText(movieSummary.getMovie().getScope());
        Double rating = movieSummary.getAverageRating();
        Long totalReviews = movieSummary.getTotalReviews();

        String ratingText = (rating != null ? String.format("%.1f", rating) : "0") + "/" +
                (totalReviews != null ? totalReviews : "0");

        holder.txt_rating.setText(ratingText);
        List<Genre> genres = movieSummary.getMovie().getGenres();
        // Chuyển List<Genre> thành List<String> chứa tên thể loại
        List<String> genreNames = new ArrayList<>();
        for (Genre genre : genres) {
            genreNames.add(genre.getName()); // Lấy tên thể loại
        }

        // Hiển thị thể loại dưới dạng "Hành động, Khoa học viễn tưởng"
        holder.txt_genre.setText(TextUtils.join(", ", genreNames));

        // Đặt sự kiện click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                Log.d("MovieAdapter", "Clicked on: " + movieSummary.getMovie().getTitle());
                listener.onMovieClick(movieSummary);
            } else {
                Log.e("MovieAdapter", "Listener is null");
            }
        });


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
        private TextView txt_rating;
        private TextView txt_scope;

        public MovieShowingminViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMoive = itemView.findViewById(R.id.img_showing);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_genre = itemView.findViewById(R.id.txt_genre);
            txt_rating = itemView.findViewById(R.id.txt_rating);
            txt_scope = itemView.findViewById(R.id.tv_scope_image);
        }
    }
    public interface OnMovieClickListener {
        void onMovieClick(MovieSummary movieSummary);
    }

}
