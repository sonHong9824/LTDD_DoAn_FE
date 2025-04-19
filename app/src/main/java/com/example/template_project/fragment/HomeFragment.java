package com.example.template_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.template_project.MainActivity;
import com.example.template_project.R;
import com.example.template_project.adapter.BannerAdapter;
import com.example.template_project.adapter.CatagoryAdapter;
import com.example.template_project.adapter.FeatureAdapter;
import com.example.template_project.adapter.MovieShowingAdapter;
import com.example.template_project.model.Banner;
import com.example.template_project.model.Catagory;
import com.example.template_project.model.Genre;
import com.example.template_project.model.Movie;
import com.example.template_project.model.MovieSummary;
import com.example.template_project.retrofit.MovieApi;
import com.example.template_project.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private View mView;

    private ViewPager2 viewPager2, mViewPage2_feature;
    private RecyclerView rcvCatagory;
    private CatagoryAdapter catagoryAdapter;

    private CircleIndicator3 circleIndicator3;
    private List<Banner> mListBanner;
    private List<MovieSummary> mListFeature;
    private List<Movie> mListMovieShowing;
    private Handler handler = new Handler();
    private FeatureAdapter featureAdapter;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(viewPager2.getCurrentItem() == mListBanner.size()-1){
                viewPager2.setCurrentItem(0);
            } else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        // Ánh xạ View
        viewPager2 = mView.findViewById(R.id.view_pager_banner);
        mViewPage2_feature = mView.findViewById(R.id.view_pager_feature);
        circleIndicator3 = mView.findViewById(R.id.circle_indicator_banner);
        rcvCatagory = mView.findViewById(R.id.rcv_catagory);

        // Adapter cho category
        catagoryAdapter = new CatagoryAdapter(requireContext(), new MovieShowingAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(MovieSummary movieSummary) {
                MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance(movieSummary);
                ((MainActivity) requireActivity()).replaceFragment(movieDetailFragment);
            }
        });

        rcvCatagory.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        rcvCatagory.setAdapter(catagoryAdapter);
        getListCatagory();

        // Cấu hình ViewPager feature
        mViewPage2_feature.setOffscreenPageLimit(3);
        mViewPage2_feature.setClipChildren(false);
        mViewPage2_feature.setClipToPadding(false);
        ViewGroup viewGroup = (ViewGroup) mViewPage2_feature.getChildAt(0);
        if (viewGroup instanceof RecyclerView) {
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
        }

        // Transformer cho hiệu ứng scale
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer((page, position) -> {
            float scale = 0.85f + (1 - Math.abs(position)) * 0.1f;
            page.setScaleX(scale);
            page.setScaleY(scale);
            page.setPivotX(page.getWidth() * 0.5f);
        });
        mViewPage2_feature.setPageTransformer(transformer);

        // Cấu hình banner
        mListBanner = getListBanner();
        BannerAdapter bannerAdapter = new BannerAdapter(mListBanner);
        viewPager2.setAdapter(bannerAdapter);
        circleIndicator3.setViewPager(viewPager2);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == mListBanner.size() - 1) {
                    viewPager2.postDelayed(() -> viewPager2.setCurrentItem(0, false), 300);
                }
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });

        // Gọi API phim nổi bật
        fetchFeatureMovies();

        return mView;
    }


    private void getListCatagory() {
        RetrofitService retrofitService = new RetrofitService();
        MovieApi movieApi = retrofitService.getRetrofit().create(MovieApi.class);

        List<Catagory> listCatagory = new ArrayList<>();

        movieApi.getComingSoonMovies().enqueue(new Callback<List<MovieSummary>>() {
            @Override
            public void onResponse(Call<List<MovieSummary>> call, Response<List<MovieSummary>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listCatagory.add(new Catagory("Phim sắp chiếu", response.body()));
                    checkAndUpdateAdapter(listCatagory);
                } else {
                    Log.e("API_RESPONSE", "Coming Soon Response Failed - Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<MovieSummary>> call, Throwable t) {
                Log.e("API_ERROR", "Coming Soon Error: " + t.getMessage());
            }
        });

        movieApi.getNowShowingMovies().enqueue(new Callback<List<MovieSummary>>() {
            @Override
            public void onResponse(Call<List<MovieSummary>> call, Response<List<MovieSummary>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listCatagory.add(new Catagory("Phim hay đang chiếu", response.body()));
                    checkAndUpdateAdapter(listCatagory);
                } else {
                    Log.e("API_RESPONSE", "Now Showing Response Failed - Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<MovieSummary>> call, Throwable t) {
                Log.e("API_ERROR", "Now Showing Error: " + t.getMessage());
            }
        });


    }

    // Kiểm tra nếu cả 2 danh sách đã được thêm thì cập nhật Adapter
    private void checkAndUpdateAdapter(List<Catagory> listCatagory) {
        if (listCatagory.size() == 2) { // Đợi cả hai danh sách được tải
            catagoryAdapter.setData(listCatagory);
            catagoryAdapter.notifyDataSetChanged();
        }
    }

    private void fetchFeatureMovies() {
        RetrofitService retrofitService = new RetrofitService();
        MovieApi movieApi = retrofitService.getRetrofit().create(MovieApi.class);

        movieApi.getFeatureMovies().enqueue(new Callback<List<MovieSummary>>() {
            @Override
            public void onResponse(Call<List<MovieSummary>> call, Response<List<MovieSummary>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MovieSummary> originalList = response.body();

                    if (originalList.size() >= 1) {
                        List<MovieSummary> loopedList = new ArrayList<>();
                        // Thêm phần tử cuối lên đầu
                        loopedList.add(originalList.get(originalList.size() - 1));
                        // Thêm danh sách chính
                        loopedList.addAll(originalList);
                        // Thêm phần tử đầu xuống cuối
                        loopedList.add(originalList.get(0));

                        featureAdapter = new FeatureAdapter(loopedList);
                        mViewPage2_feature.setAdapter(featureAdapter);
                        mViewPage2_feature.setCurrentItem(1, false); // Bắt đầu từ item chính

                        setupFeatureViewPagerLooping(); // Đăng ký listener sau khi adapter đã set
                    }
                } else {
                    Log.e("API_RESPONSE", "Feature Movies Failed - Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<MovieSummary>> call, Throwable t) {
                Log.e("API_ERROR", "Feature Movies Error: " + t.getMessage());
            }
        });
    }
    private void setupFeatureViewPagerLooping() {
        mViewPage2_feature.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (featureAdapter == null || featureAdapter.getItemCount() < 3) return;

                mViewPage2_feature.post(() -> {
                    int itemCount = featureAdapter.getItemCount();
                    if (position == 0) {
                        mViewPage2_feature.setCurrentItem(itemCount - 2, false);
                    } else if (position == itemCount - 1) {
                        mViewPage2_feature.setCurrentItem(1, false);
                    }
                });
            }
        });
    }


    private List<Banner> getListBanner(){
        List<Banner> list = new ArrayList<>();
        list.add(new Banner(R.drawable.banner1));
        list.add(new Banner(R.drawable.banner2));
        list.add(new Banner(R.drawable.banner3));
        list.add(new Banner(R.drawable.banner4));
        list.add(new Banner(R.drawable.banner5));
        list.add(list.get(0));

        return list;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }


}
