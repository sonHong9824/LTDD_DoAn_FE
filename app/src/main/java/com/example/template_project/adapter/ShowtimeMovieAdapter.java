package com.example.template_project.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.template_project.R;
import com.example.template_project.model.Genre;
import com.example.template_project.model.MovieShowtime;

import java.util.ArrayList;
import java.util.List;

public class ShowtimeMovieAdapter extends RecyclerView.Adapter <ShowtimeMovieAdapter.ViewHolder>
{
    private List<MovieShowtime> mListShowtimeMove;
    public ShowtimeMovieAdapter(List<MovieShowtime> mListShowtimeMove) {
        this.mListShowtimeMove = mListShowtimeMove;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showtime_movie, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieShowtime movieShowtime = mListShowtimeMove.get(position);
        if (movieShowtime == null)
        {
            return;
        }
        Glide.with(holder.imgMoive.getContext())
                .load(movieShowtime.getMovie().getPosterUrl()) // URL của ảnh
                .placeholder(R.drawable.placeholder_img) // Ảnh mặc định khi tải
                .error(R.drawable.error_img) // Ảnh lỗi nếu URL sai
                .into(holder.imgMoive); // Đưa vào ImageView
        Log.d("MovieAdapter", "Image URL: " + movieShowtime.getMovie().getPosterUrl());
        holder.txt_title.setText(movieShowtime.getMovie().getTitle());



        List<Genre> genres = movieShowtime.getMovie().getGenres();
        // Chuyển List<Genre> thành List<String> chứa tên thể loại
        List<String> genreNames = new ArrayList<>();
        for (Genre genre : genres) {
            genreNames.add(genre.getName()); // Lấy tên thể loại
        }
        holder.txt_info.setText("Thể loại: " + TextUtils.join(", ", genreNames) + " | " + movieShowtime.getMovie().getDuration());

        ShowtimeTypeAdapter showtimeTypeAdapter = new ShowtimeTypeAdapter(movieShowtime.getShowtimetype());
        holder.rc_showtime_type.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.rc_showtime_type.setNestedScrollingEnabled(false);
        holder.rc_showtime_type.setAdapter(showtimeTypeAdapter);
    }

    @Override
    public int getItemCount() {
        return mListShowtimeMove.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgMoive;
        private TextView txt_title;
        private TextView txt_info;
        private RecyclerView rc_showtime_type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMoive = itemView.findViewById(R.id.imageMovie);
            txt_title = itemView.findViewById(R.id.textTitleMovie);
            txt_info = itemView.findViewById(R.id.textInfo);
            rc_showtime_type = itemView.findViewById(R.id.recyclerShowtimeType);
        }
    }
}
