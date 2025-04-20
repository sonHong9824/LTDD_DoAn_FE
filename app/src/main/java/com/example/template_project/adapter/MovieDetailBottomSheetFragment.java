package com.example.template_project.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.template_project.R;
import com.example.template_project.YouTubePlayerActivity;
import com.example.template_project.model.Genre;
import com.example.template_project.model.MovieShowtime;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailBottomSheetFragment extends BottomSheetDialogFragment {

    private MovieShowtime movieShowtime;

    public static MovieDetailBottomSheetFragment newInstance(MovieShowtime movieShowtime) {
        MovieDetailBottomSheetFragment fragment = new MovieDetailBottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable("movie_data", movieShowtime); // hoặc Parcelable nếu bạn đã có
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail_bottom_sheet, container, false);

        ImageView btnClose = view.findViewById(R.id.btnClose);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtDescription = view.findViewById(R.id.txtDesc);
        TextView txt_genre = view.findViewById(R.id.txtGenre);
        TextView txt_duration = view.findViewById(R.id.txtDuration);
        ImageView btnPlayTraler = view.findViewById(R.id.img_trailer_play);
        movieShowtime = (MovieShowtime) getArguments().getSerializable("movie_data");
        String trailer = movieShowtime.getMovie().getTrailerUrl();

        if (getArguments() != null) {

            txtTitle.setText(movieShowtime.getMovie().getTitle());
            txt_genre.setText("Thể loại: " + TextUtils.join(", ", getGenreNames(movieShowtime)));
            txtDescription.setText("Mô tả: " + movieShowtime.getMovie().getDescription());
            txtTitle.setText(movieShowtime.getMovie().getTitle());
            txt_duration.setText("Thời lượng: " + movieShowtime.getMovie().getDuration() + " phút");

        }

        btnClose.setOnClickListener(v -> dismiss());

        btnPlayTraler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trailer != null && !trailer.isEmpty()) {
                    Intent intent = new Intent(requireContext(), YouTubePlayerActivity.class);
                    intent.putExtra("VIDEO_ID", trailer);
                    startActivity(intent);
                } else {
                    Log.e("MovieDetail", "Trailer ID is null or empty!");
                }
            }
        });
        return view;
    }

    private List<String> getGenreNames(MovieShowtime movieShowtime) {
        List<String> names = new ArrayList<>();
        for (Genre g : movieShowtime.getMovie().getGenres()) {
            names.add(g.getName());
        }
        return names;
    }
}
