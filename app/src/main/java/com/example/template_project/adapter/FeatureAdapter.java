package com.example.template_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.template_project.R;
import com.example.template_project.model.Movie;

import java.util.List;

public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.FeatureViewHolder> {

    private final List<Movie> mListMovie;

    public FeatureAdapter(List<Movie> mListMovie) {
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
        Movie movie = mListMovie.get(position);
        if(movie == null){
            return;
        }
        holder.img_feature.setImageResource(movie.getResourceId());
        holder.txt_title.setText(movie.getTitle());

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
         private final TextView txt_title;
        public FeatureViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_feature);
            img_feature = itemView.findViewById(R.id.img_feature);

        }
    }
}
