package com.example.template_project.fragment;

import android.os.Bundle;
import android.os.Handler;
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
import com.example.template_project.adapter.MovieShowingminAdapter;
import com.example.template_project.model.Banner;
import com.example.template_project.model.Catagory;
import com.example.template_project.model.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.MissingFormatArgumentException;

import me.relex.circleindicator.CircleIndicator3;

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
        catagoryAdapter.setData(getListCatagory());
        rcvCatagory.setAdapter(catagoryAdapter);

        mListBanner = getListBanner();
        mListFeature = getListFeature();

        FeatureAdapter featureAdapter = new FeatureAdapter(mListFeature);
        mViewPage2_feature.setAdapter(featureAdapter);

        mViewPage2_feature.setOffscreenPageLimit(3);
        mViewPage2_feature.setClipChildren(false);
        mViewPage2_feature.setClipToPadding(false);



        mViewPage2_feature.setCurrentItem(1, false);
        // Lắng nghe sự kiện cuộn
        mViewPage2_feature.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                int itemCount = featureAdapter.getItemCount();
                if (position == 0) {
                    mViewPage2_feature.postDelayed(() -> mViewPage2_feature.setCurrentItem(itemCount - 2, false), 300);
                } else if (position == itemCount - 1) {
                    mViewPage2_feature.postDelayed(() -> mViewPage2_feature.setCurrentItem(1, false), 300);
                }
            }
        });

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float scaleFactor = 0.75f + (1 - Math.abs(position)) * 0.25f;
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);

                float rotation = position * -20f;
                page.setRotationY(rotation);

                if (position < 0) {
                    page.setPivotX(page.getWidth() * 0.9f);
                } else {
                    page.setPivotX(page.getWidth() * 0.1f);
                }
            }
        });
        mViewPage2_feature.setPageTransformer(compositePageTransformer);


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

    private List<Catagory> getListCatagory() {
        List<Catagory> list = new ArrayList<>();
        List<Movie> listMoive = new ArrayList<>();

        listMoive.add(new Movie("CAPTAIN AMERICA: THẾ GIỚI MỚI", "https://iguov8nhvyobj.vcdn.cloud/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/c/a/captain_america_th_gi_i_m_i_-_official_poster.jpg"
                , Arrays.asList("Hành Động", "Khoa Học Viễn Tưởng")));
        listMoive.add(new Movie("CAPTAIN AMERICA: THẾ GIỚI MỚI", "https://iguov8nhvyobj.vcdn.cloud/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/c/a/captain_america_th_gi_i_m_i_-_official_poster.jpg"
                , Arrays.asList("Hành Động", "Khoa Học Viễn Tưởng")));
        listMoive.add(new Movie("CAPTAIN AMERICA: THẾ GIỚI MỚI", "https://iguov8nhvyobj.vcdn.cloud/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/c/a/captain_america_th_gi_i_m_i_-_official_poster.jpg"
                , Arrays.asList("Hành Động", "Khoa Học Viễn Tưởng")));
        listMoive.add(new Movie("CAPTAIN AMERICA: THẾ GIỚI MỚI", "https://iguov8nhvyobj.vcdn.cloud/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/c/a/captain_america_th_gi_i_m_i_-_official_poster.jpg"
                , Arrays.asList("Hành Động", "Khoa Học Viễn Tưởng")));

        list.add(new Catagory("Phim hay đang chiếu", listMoive));
        list.add(new Catagory("Phim sắp chiếu", listMoive));

        return list;
    }

    private  List<Movie> getListFeature(){
        List<Movie> list = new ArrayList<>();
        list.add(new Movie( "3",R.drawable.movie3));

        list.add(new Movie("1",R.drawable.movie1));
        list.add(new Movie("2",R.drawable.movie2));
        list.add(new Movie("3",R.drawable.movie3));

        list.add(new Movie("1",R.drawable.movie1));

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
