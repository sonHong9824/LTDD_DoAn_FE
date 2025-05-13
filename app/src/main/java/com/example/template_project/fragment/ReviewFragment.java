package com.example.template_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.template_project.LoginActivity;
import com.example.template_project.R;
import com.example.template_project.SharedPreferences.PrefUser;
import com.example.template_project.model.Movie;
import com.example.template_project.model.Review;
import com.example.template_project.model.ReviewRequest;
import com.example.template_project.retrofit.RetrofitService;
import com.example.template_project.retrofit.ReviewApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewFragment extends Fragment {
    private Movie movie;
    private ImageView img_poster;
    private TextView tv_name, tv_nhan_de_danh_gia, tv_rating_wreview;
    private RatingBar ratingBar;
    private EditText et_content;
    private Button btn_gui_danh_gia;
    private ImageButton btn_back;
    private PrefUser prefUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write_review, container, false);
        prefUser = new PrefUser(getContext());
        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable("MOVIE_DATA"); // Gán dữ liệu cho biến toàn cục
            if (movie != null) {
                Log.d("ReviewFragment", "Movie Title: " + movie.getTitle());
            } else {
                Log.e("ReviewFragment", "Movie is NULL!");
            }
        } else {
            Log.e("ReviewFragment", "Arguments are NULL!");
        }
        img_poster = view.findViewById(R.id.img_poster_review);
        tv_name = view.findViewById(R.id.tv_name_wreview);
        tv_nhan_de_danh_gia = view.findViewById(R.id.tv_nhan_de_danh_gia);
        tv_rating_wreview = view.findViewById(R.id.rating_wreview);
        ratingBar = view.findViewById(R.id.ratingBar);
        et_content = view.findViewById(R.id.et_content);
        btn_gui_danh_gia = view.findViewById(R.id.btn_gui_danh_gia);
        btn_back = view.findViewById(R.id.btn_back_review);
        btn_back.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        Glide.with(this)
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.placeholder_img)
                .error(R.drawable.error_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_poster);

        tv_name.setText(movie.getTitle());

        tv_nhan_de_danh_gia.setVisibility(View.VISIBLE);
        tv_rating_wreview.setVisibility(View.GONE);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 0) {
                    return;
                } else {
                    tv_nhan_de_danh_gia.setVisibility(View.GONE);
                    tv_rating_wreview.setVisibility(View.VISIBLE);

                    switch ((int) rating){
                        case 1:
                            tv_rating_wreview.setText("1/5 - Kén người mê");
                            break;
                        case 2:
                            tv_rating_wreview.setText("2/5 - Chưa ưng lắm");
                            break;
                        case 3:
                            tv_rating_wreview.setText("3/5 - Tạm ổn");
                            break;
                        case 4:
                            tv_rating_wreview.setText("4/5 - Đáng xem");
                            break;
                        case 5:
                            tv_rating_wreview.setText("5/5 - Cực phẩm!");
                            break;
                    }
                }
            }
        });

        btn_gui_danh_gia.setOnClickListener(v -> {
            if (prefUser.isUserLoggedOut()) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                getContext().startActivity(intent);
                return;
            } else {
                ReviewRequest reviewRequest = new ReviewRequest();
                reviewRequest.setMovieId(movie.getId());
                reviewRequest.setUserId(prefUser.getId());
                reviewRequest.setRating((int) ratingBar.getRating());
                reviewRequest.setComment(String.valueOf(et_content.getText()));

                Log.d("ReviewDebug", "User ID: " + prefUser.getId());
                Log.d("ReviewDebug", "Movie ID: " + movie.getId());
                Log.d("ReviewDebug", "Rating: " + (int) ratingBar.getRating());
                Log.d("ReviewDebug", "Comment: " + et_content.getText().toString());

                RetrofitService retrofitService = new RetrofitService();
                ReviewApi reviewApi = retrofitService.getRetrofit().create(ReviewApi.class);
                reviewApi.create(reviewRequest).enqueue(new Callback<Review>() {
                    @Override
                    public void onResponse(Call<Review> call, Response<Review> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(getContext(), "Đánh giá đã được gửi!", Toast.LENGTH_SHORT).show();

                            et_content.setText("");
                            ratingBar.setRating(0);

                            requireActivity().getSupportFragmentManager().popBackStack();
                        } else {
                            Toast.makeText(getContext(), "Không thể gửi đánh giá. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Review> call, Throwable t) {
                        Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("ReviewAPI", "onFailure: ", t);
                    }
                });
            }
        });
        return view;
    }
}
