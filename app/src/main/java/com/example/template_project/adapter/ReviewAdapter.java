package com.example.template_project.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.template_project.R;
import com.example.template_project.model.Review;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{
    private List<Review> reviewList;
    private Context context;

    public ReviewAdapter(List<Review> reviewList, Context context) {
        this.reviewList = reviewList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);
        return new ReviewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);

        holder.tv_name.setText(review.getUser().getName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        holder.tv_date.setText(review.getCreatedAt().format(formatter));

        holder.tv_star.setText(review.getRating() + "/5");
        holder.tv_content.setText(review.getComment());
        Log.d("ReviewAdapter", "Comment: " + review.getComment());

        holder.tv_xemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isExpanded = holder.tv_xemthem.getText().toString().equals("Thu gọn");

                if (isExpanded) {
                    // Thu gọn
                    holder.tv_content.setMaxLines(2);
                    holder.tv_content.setEllipsize(TextUtils.TruncateAt.END);
                    holder.tv_xemthem.setText("Xem thêm");
                } else {
                    // Mở rộng
                    holder.tv_content.setMaxLines(Integer.MAX_VALUE);
                    holder.tv_content.setEllipsize(null);
                    holder.tv_xemthem.setText("Thu gọn");
                }
            }
        });
        switch (review.getRating()){
            case 1:
                holder.tv_desc.setText("Kén người mê");
                break;
            case 2:
                holder.tv_desc.setText("Chưa ưng lắm");
                break;
            case 3:
                holder.tv_desc.setText("Tạm ổn");
                break;
            case 4:
                holder.tv_desc.setText("Đáng xem");
                break;
            case 5:
                holder.tv_desc.setText("Cực phẩm!");
                break;
        }

    }

    @Override
    public int getItemCount() {
        if (reviewList != null){
            return reviewList.size();
        }
        return 0;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name, tv_date, tv_star, tv_desc, tv_content, tv_xemthem;
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name_review);
            tv_date = itemView.findViewById(R.id.tv_date_review);
            tv_star = itemView.findViewById(R.id.tv_star_review);
            tv_desc = itemView.findViewById(R.id.tv_desc_review);
            tv_content = itemView.findViewById(R.id.tv_content_review);
            tv_xemthem = itemView.findViewById(R.id.tv_xemthem);
        }
    }
}
