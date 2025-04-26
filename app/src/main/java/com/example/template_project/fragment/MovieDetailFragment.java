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
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.template_project.LoginActivity;
import com.example.template_project.R;
import com.example.template_project.SharedPreferences.PrefUser;
import com.example.template_project.YouTubePlayerActivity;
import com.example.template_project.adapter.ReviewAdapter;
import com.example.template_project.model.FeatureMovie;
import com.example.template_project.model.Genre;
import com.example.template_project.model.MovieSummary;
import com.example.template_project.model.Review;
import com.example.template_project.retrofit.MovieApi;
import com.example.template_project.retrofit.RetrofitService;
import com.example.template_project.retrofit.ReviewApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailFragment extends Fragment {
    private MovieSummary movieSummary;
    private ImageView imgMovieDetail;
    private ImageButton btn_trailer,  btn_back;
    private TextView tvName, tvGenre, tvRating, tvTotalRating,tvDescription, tvScope, tvScopeDesc, tvDate, tvDuration, tvLang, tvRatingReview, tvTotalRatingReview, tvWriteReview;
    private Button btn_book;
    private String trailer_id, movieStatus;
    private ConstraintLayout constraintLayout;
    private RecyclerView recyclerView_review;
    private List<Review> reviewList;
    private PrefUser prefUser;
    private ReviewAdapter reviewAdapter;
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
        prefUser = new PrefUser(getContext());
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
        constraintLayout = view.findViewById(R.id.constraint_review);
        recyclerView_review = view.findViewById(R.id.rc_review);
        tvRatingReview = view.findViewById(R.id.tv_rating_review);
        tvTotalRatingReview = view.findViewById(R.id.tv_total_rating_review);
        tvWriteReview = view.findViewById(R.id.tv_write_review);
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

        RetrofitService retrofitService = new RetrofitService();
        MovieApi movieApi = retrofitService.getRetrofit().create(MovieApi.class);
        movieApi.increase(movieSummary.getMovie().getId()).enqueue(new Callback<FeatureMovie>() {
            @Override
            public void onResponse(Call<FeatureMovie> call, Response<FeatureMovie> response) {
                if (response.isSuccessful()) {
                    FeatureMovie updatedFeatureMovie = response.body();
                    Log.d("API", "Phim đã được tăng điểm: " + updatedFeatureMovie.getScore());
                } else {
                    Log.e("API", "Lỗi khi tăng điểm phim: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<FeatureMovie> call, Throwable t) {
                Log.e("API", "Lỗi kết nối: " + t.getMessage());
            }
        });

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
        getReview();

        tvTotalRatingReview.setText(tvTotalRatingReview.getContext().getString(R.string.total_raing, movieSummary.getTotalReviews()));
        tvRatingReview.setText(tvRatingReview.getContext().getString(R.string.rating, movieSummary.getAverageRating()));
        tvWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReviewFragment reviewFragment = new ReviewFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("MOVIE_DATA", movieSummary.getMovie());

                reviewFragment.setArguments(bundle);

                // Chuyển Fragment
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, reviewFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void getReview() {
        RetrofitService retrofitService = new RetrofitService();
        ReviewApi reviewApi = retrofitService.getRetrofit().create(ReviewApi.class);
        reviewApi.getReviews(movieSummary.getMovie().getId()).enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (response.isSuccessful() && response.body() != null){
                    reviewList = response.body();

                    reviewAdapter = new ReviewAdapter(reviewList, getContext());
                    recyclerView_review.setAdapter(reviewAdapter);
                    recyclerView_review.setLayoutManager(new LinearLayoutManager(getContext()));

                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.e("ReviewAPI", "Error fetching reviews", t);
            }
        });

        if (prefUser.isUserLoggedOut()) {
            Toast.makeText(getContext(), "Vui lòng đăng nhập để chọn suất chiếu", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        } else {

        }
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
