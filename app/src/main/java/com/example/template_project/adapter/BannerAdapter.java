package com.example.template_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.template_project.R;
import com.example.template_project.model.Banner;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {
    private List<Banner> mListBanner;

    public BannerAdapter(List<Banner> mListBanner) {
        this.mListBanner = mListBanner;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_banner, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        Banner banner = mListBanner.get(position);
        if(banner == null){
            return;
        }
        holder.banner.setImageResource(banner.getResourceId());
    }

    @Override
    public int getItemCount() {
        if(mListBanner != null){
            return  mListBanner.size();
        }
        return 0;
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder{
        private ImageView banner;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner);
        }
    }
}
