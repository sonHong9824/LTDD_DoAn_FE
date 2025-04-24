package com.example.template_project.adapter;

import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.template_project.LoginActivity;
import com.example.template_project.R;
import com.example.template_project.fragment.MovieDetailFragment;
import com.example.template_project.fragment.SeatFragment;
import com.example.template_project.model.Genre;
import com.example.template_project.model.Movie;
import com.example.template_project.model.MovieSummary;

import java.util.ArrayList;
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
        switch (position) {
            case 1:
                holder.img_number.setImageResource(R.drawable.number1);
                break;
            case 2:
                holder.img_number.setImageResource(R.drawable.number2);
                break;
            case 3:
                holder.img_number.setImageResource(R.drawable.number3);
                break;
            case 4:
                holder.img_number.setImageResource(R.drawable.number4);
                break;
            case 5:
                holder.img_number.setImageResource(R.drawable.number5);
                break;
        }
        Glide.with(holder.img_feature.getContext())
                .load(movie.getMovie().getPosterUrl()) // URL của ảnh
                .placeholder(R.drawable.placeholder_img) // Ảnh mặc định khi tải
                .error(R.drawable.error_img) // Ảnh lỗi nếu URL sai
                .into(holder.img_feature); // Đưa vào ImageView
        Log.d("MovieAdapter", "Image URL: " + movie.getMovie().getPosterUrl());
        holder.txt_title.setText(movie.getMovie().getTitle());
        holder.txt_scope.setText(movie.getMovie().getScope());

        List<Genre> genres = movie.getMovie().getGenres();
        List<String> genreNames = new ArrayList<>();
        for (Genre genre : genres) {
            genreNames.add(genre.getName()); // Lấy tên thể loại
        }
        holder.txt_genre.setText(TextUtils.join(", ", genreNames));

        if(movie.getMovie().getStatus().equals("NOW_SHOWING")){
            Double rating = movie.getAverageRating();
            Long totalReviews = movie.getTotalReviews();
            if (totalReviews == 0)
            {
                holder.txt_rating.setVisibility(View.GONE);

            } else {
                String ratingText = String.format("%.1f", rating) + "/" + "5 " + "(" +
                        totalReviews + " đánh giá)";
                holder.txt_rating.setText(ratingText);
                holder.txt_rating.setVisibility(View.VISIBLE);
            }
        }else {
            holder.txt_rating.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();

            MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("MOVIE_SUMMARY", movie);
            movieDetailFragment.setArguments(bundle);

            // Chuyển fragment
            ((AppCompatActivity) context).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, movieDetailFragment)
                    .addToBackStack(null)
                    .commit();
        });

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
         private final TextView txt_title, txt_scope, txt_rating, txt_genre;
         private final ImageView img_number;
        public FeatureViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_feature);
            img_feature = itemView.findViewById(R.id.img_feature);
            img_number = itemView.findViewById(R.id.img_number);
            txt_scope = itemView.findViewById(R.id.tv_scope_feature);
            txt_rating = itemView.findViewById(R.id.tv_rating_feature);
            txt_genre = itemView.findViewById(R.id.tv_genre_feature);
        }
    }
}
