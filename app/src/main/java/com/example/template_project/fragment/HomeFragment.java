package com.example.template_project.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.template_project.R;
import com.example.template_project.adapter.BannerAdapter;
import com.example.template_project.adapter.CatagoryAdapter;
import com.example.template_project.adapter.FeatureAdapter;
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
    private List<Movie> mListFeature;
    private List<Movie> mListMovieShowing;
    private Handler handler = new Handler();
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

        viewPager2 = mView.findViewById(R.id.view_pager_banner);
        mViewPage2_feature = mView.findViewById(R.id.view_pager_feature);
        circleIndicator3 = mView.findViewById(R.id.circle_indicator_banner);
        rcvCatagory = mView.findViewById(R.id.rcv_catagory);
        catagoryAdapter = new CatagoryAdapter(requireContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        rcvCatagory.setLayoutManager(linearLayoutManager);

        mListBanner = getListBanner();
        mListFeature = getListFeature();

        FeatureAdapter featureAdapter = new FeatureAdapter(mListFeature);
        mViewPage2_feature.setAdapter(featureAdapter);

        rcvCatagory.setAdapter(catagoryAdapter);
        getListCatagory(); // Gọi API sau khi adapter được gắn

        mViewPage2_feature.setOffscreenPageLimit(3);
        mViewPage2_feature.setClipChildren(false);
        mViewPage2_feature.setClipToPadding(false);
        mViewPage2_feature.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        mViewPage2_feature.setClipChildren(false);
        ViewGroup viewGroup = (ViewGroup) mViewPage2_feature.getChildAt(0);
        if (viewGroup instanceof RecyclerView) {
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
        }


        mViewPage2_feature.setCurrentItem(1, false);
        // Lắng nghe sự kiện cuộn
        mViewPage2_feature.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                int itemCount = featureAdapter.getItemCount();
                if (itemCount < 3) return; // Đảm bảo có đủ item để tránh lỗi

                mViewPage2_feature.post(() -> {
                    if (position == 0) {
                        mViewPage2_feature.setCurrentItem(itemCount - 2, false); // Nhảy về item trước cuối
                    } else if (position == itemCount - 1) {
                        mViewPage2_feature.setCurrentItem(1, false); // Nhảy về item thứ 1
                    }
                });
            }
        });

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40)); // Tăng margin giữa các item
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float scale = 0.85f + (1 - Math.abs(position)) * 0.1f; // Giảm scale nhỏ hơn
                page.setScaleX(scale);
                page.setScaleY(scale);

                // Giữ vị trí trung tâm khi cuộn
                page.setPivotX(page.getWidth() * 0.5f);
            }
        });
        mViewPage2_feature.setPageTransformer(transformer);


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



    private  List<Movie> getListFeature(){
        List<Movie> list = new ArrayList<>();
        list.add(new Movie(
                "1",
                "CAPTAIN AMERICA: THẾ GIỚI MỚI",
                "Bộ phim về Captain America sau khi Steve Rogers giải nghệ.",
                140,
                "2025-07-04",
                "https://iguov8nhvyobj.vcdn.cloud/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/c/a/captain_america_th_gi_i_m_i_-_official_poster.jpg",
                "https://www.youtube.com/watch?v=abcd1234",
                "NOW_SHOWING",
                Arrays.asList(new Genre("1", "Hành động"), new Genre("2", "Khoa học viễn tưởng"))
        ));


        list.add(new Movie(
                "1",
                "CAPTAIN AMERICA: THẾ GIỚI MỚI",
                "Bộ phim về Captain America sau khi Steve Rogers giải nghệ.",
                140,
                "2025-07-04",
                "https://iguov8nhvyobj.vcdn.cloud/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/c/a/captain_america_th_gi_i_m_i_-_official_poster.jpg",
                "https://www.youtube.com/watch?v=abcd1234",
                "NOW_SHOWING",
                Arrays.asList(new Genre("1", "Hành động"), new Genre("2", "Khoa học viễn tưởng"))
        ));
        list.add(new Movie(
                "1",
                "CAPTAIN AMERICA: THẾ GIỚI MỚI",
                "Bộ phim về Captain America sau khi Steve Rogers giải nghệ.",
                140,
                "2025-07-04",
                "https://iguov8nhvyobj.vcdn.cloud/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/c/a/captain_america_th_gi_i_m_i_-_official_poster.jpg",
                "https://www.youtube.com/watch?v=abcd1234",
                "NOW_SHOWING",
                Arrays.asList(new Genre("1", "Hành động"), new Genre("2", "Khoa học viễn tưởng"))
        ));
        return list;
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
