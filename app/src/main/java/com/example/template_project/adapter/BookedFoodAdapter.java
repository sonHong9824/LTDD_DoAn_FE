package com.example.template_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.template_project.R;
import com.example.template_project.model.BookedFood;
import com.example.template_project.model.Food;

import java.text.DecimalFormat;
import java.util.List;

public class BookedFoodAdapter extends RecyclerView.Adapter<BookedFoodAdapter.BookedFoodViewHolder>{
    private Context context;
    private List<BookedFood> bookedFoods;
    private List<Food> allFoods;

    public BookedFoodAdapter(Context context, List<BookedFood> bookedFoods, List<Food> allFoods) {
        this.context = context;
        this.bookedFoods = bookedFoods;
        this.allFoods = allFoods;
    }

    @NonNull
    @Override
    public BookedFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food_booking, parent, false);
        return new BookedFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookedFoodViewHolder holder, int position) {
        BookedFood bookedFood = bookedFoods.get(position);
        Food food = findFoodById(bookedFood.getId());

        if (food != null) {
            holder.tv_name_food.setText(food.getName());
            int price = food.getPrice();
            String formatted = new DecimalFormat("#,### đ").format(price);
            holder.tv_price_food.setText(formatted);
            holder.tv_quantity.setText(String.valueOf(bookedFood.getQuantity()));

            Glide.with(holder.itemView.getContext())
                    .load(food.getpictureUrl())
                    .placeholder(R.drawable.placeholder_img)
                    .error(R.drawable.error_img)
                    .into(holder.img_food);
        }
    }

    private Food findFoodById(String id) {
        for (Food food : allFoods) {
            if (food.getId().equals(id)) return food;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        if(bookedFoods != null){
            return  bookedFoods.size();
        }
        return 0;
    }

    public class BookedFoodViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_food;
        private TextView tv_name_food, tv_price_food, tv_quantity;

        public BookedFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            img_food = itemView.findViewById(R.id.img_food);
            tv_name_food = itemView.findViewById(R.id.tv_name_food);
            tv_price_food = itemView.findViewById(R.id.tv_price_food);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
        }
    }
}
