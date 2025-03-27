package com.example.template_project.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.template_project.R;
import com.example.template_project.YouTubePlayerActivity;
import com.example.template_project.model.Genre;
import com.example.template_project.model.MovieSummary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MovieDetailFragment extends Fragment {
    private MovieSummary movieSummary;
    private ImageView imgMovieDetail;
    private ImageButton btn_trailer,  btn_back;
    private TextView tvName, tvGenre, tvRating, tvTotalRating,tvDescription, tvScope, tvScopeDesc, tvDate, tvDuration, tvLang;
    private Button btn_book;
    private String trailer_id, movieStatus;

    private static final String ARG_MOVIE_SUMMARY = "MOVIE_SUMMARY";

    public static MovieDetailFragment newInstance(MovieSummary movieSummary) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE_SUMMARY, movieSummary);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        // Ánh xạ view
        imgMovieDetail = view.findViewById(R.id.img_movieDetail);
        tvName = view.findViewById(R.id.tv_name);
        tvGenre = view.findViewById(R.id.tv_genre);
        tvRating = view.findViewById(R.id.tv_rating);
        tvTotalRating = view.findViewById(R.id.tv_total_rating);
        tvDescription = view.findViewById(R.id.tv_desc);
        tvScope = view.findViewById(R.id.tv_scope);
        tvScopeDesc = view.findViewById(R.id.tv_scope_desc);
        tvDate = view.findViewById(R.id.tv_date);
        tvDuration = view.findViewById(R.id.tv_duration);
        tvLang = view.findViewById(R.id.tv_language);
        btn_trailer = view.findViewById(R.id.btn_trailer);
        btn_book = view.findViewById(R.id.btn_book);
        btn_back = view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        // Nhận dữ liệu từ arguments
        if (getArguments() != null) {
            movieSummary = (MovieSummary) getArguments().getSerializable(ARG_MOVIE_SUMMARY);
            if (movieSummary != null) {
                displayMovieDetails(movieSummary);
            }
        }

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowtimeFragment showtimeFragment = new ShowtimeFragment();

                // Tạo Bundle và truyền dữ liệu
                Bundle bundle = new Bundle();
                bundle.putSerializable("MOVIE_DATA", movieSummary.getMovie());

                showtimeFragment.setArguments(bundle);

                // Chuyển Fragment
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, showtimeFragment) // ID của container chứa fragment
                        .addToBackStack(null) // Cho phép quay lại fragment trước đó
                        .commit();
            }
        });

        btn_trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trailer_id != null && !trailer_id.isEmpty()) {
                    Intent intent = new Intent(requireContext(), YouTubePlayerActivity.class);
                    intent.putExtra("VIDEO_ID", trailer_id);
                    startActivity(intent);
                } else {
                    Log.e("MovieDetail", "Trailer ID is null or empty!");
                }
            }
        });
        if (!"NOW_SHOWING".equals(movieStatus)) {
            btn_book.setVisibility(View.GONE);
        }

        return view;
    }

    private void displayMovieDetails(MovieSummary movieSummary) {
        tvName.setText(movieSummary.getMovie().getTitle());
        tvDescription.setText(movieSummary.getMovie().getDescription());
        tvRating.setText(tvRating.getContext().getString(R.string.rating, movieSummary.getAverageRating()));
        tvTotalRating.setText(tvTotalRating.getContext().getString(R.string.total_raing, movieSummary.getTotalReviews()));
        tvDate.setText(formatDate(movieSummary.getMovie().getReleaseDate()));
        tvDuration.setText(tvDuration.getContext().getString(R.string.duration, movieSummary.getMovie().getDuration()));
        tvLang.setText(movieSummary.getMovie().getLanguage());
        trailer_id = movieSummary.getMovie().getTrailerUrl();
        movieStatus = movieSummary.getMovie().getStatus();

        String scope = movieSummary.getMovie().getScope();
        tvScope.setText(scope);

        if (scope.equals("P")) {
            tvScopeDesc.setText("Phim được phép phổ biến đến người xem ở mọi độ tuổi");
        } else {
            scope = scope.substring(0, scope.length() - 1);
            tvScopeDesc.setText("Phim được phép phổ biến đến người xem từ đủ " + scope + " tuổi trở lên");
        }

        if (movieSummary.getTotalReviews() == 0){
            tvRating.setVisibility(View.GONE);
            tvTotalRating.setVisibility(View.GONE);
        } else {
            tvRating.setVisibility(View.VISIBLE);
            tvTotalRating.setVisibility(View.VISIBLE);
        }


        Log.d("MovieDetail", "Title: " + movieSummary.getMovie().getTitle());
        Log.d("MovieDetail", "Language: " + movieSummary.getMovie().getLanguage());
        Log.d("MovieDetail", "Scope: " + movieSummary.getMovie().getBackdropUrl());

        // Load ảnh bằng Glide
        Glide.with(this)
                .load(movieSummary.getMovie().getPosterUrl())
                .placeholder(R.drawable.placeholder_img)
                .error(R.drawable.error_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgMovieDetail);
        Glide.with(this)
                .load(movieSummary.getMovie().getBackdropUrl())
                .placeholder(R.drawable.placeholder_img)
                .error(R.drawable.error_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(btn_trailer);

        // Lấy danh sách thể loại
        List<Genre> genreList = movieSummary.getMovie().getGenres();
        if (genreList != null && !genreList.isEmpty()) {
            List<String> genreNames = new ArrayList<>();
            for (Genre genre : genreList) {
                genreNames.add(genre.getName()); // Lấy tên thể loại
            }
            tvGenre.setText(TextUtils.join(", ", genreNames));
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        getParentFragmentManager().popBackStack();
                    }
                }
        );
    }
    public String formatDate(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()); // Hoặc "dd/MM/yyyy"

        try {
            Date date = inputFormat.parse(inputDate); // Chuyển chuỗi thành Date
            return outputFormat.format(date); // Chuyển Date thành chuỗi với định dạng mới
        } catch (ParseException e) {
            e.printStackTrace();
            return inputDate; // Trả về ngày cũ nếu có lỗi
        }
    }
}
