package com.example.template_project.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.template_project.R;
import com.example.template_project.model.BookedFood;
import com.example.template_project.model.Food;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<Food> foodList;
    private Context context;
    private Map<String, BookedFood> bookedFoodMap; // Lưu danh sách món đã đặt
    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
        this.bookedFoodMap = new HashMap<>();
    }
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.tvName.setText(food.getName());
        holder.tvPrice.setText(food.getPrice());
        holder.tvDesc.setText(food.getdescription());

        // Lấy số lượng món ăn đã đặt
        int quantity = bookedFoodMap.containsKey(food.getId()) ? bookedFoodMap.get(food.getId()).getQuantity() : 0;
        holder.tvQuantity.setText(String.valueOf(quantity));

        if (food.getpictureUrl() == null || food.getpictureUrl().trim().isEmpty()) {
            Log.e("GlideError", "Lỗi: imageUrl bị null hoặc rỗng tại vị trí " + position);
            holder.imgFood.setImageResource(R.drawable.placeholder_img); // Hình mặc định nếu lỗi
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(food.getpictureUrl())
                    .placeholder(R.drawable.placeholder_img)
                    .error(R.drawable.error_img)
                    .into(holder.imgFood);
        }
        if (quantity == 0){
            holder.btnMinus.setVisibility(View.INVISIBLE);
        } else {
            holder.btnMinus.setVisibility(View.VISIBLE);
        }

        // Xử lý nút tăng số lượng
        holder.btnAdd.setOnClickListener(v -> {
            if (!bookedFoodMap.containsKey(food.getId())) {
                bookedFoodMap.put(food.getId(), new BookedFood(food.getId(), 1));
            } else {
                bookedFoodMap.get(food.getId()).increaseQuantity();
            }
            notifyItemChanged(position);
        });

        // Xử lý nút giảm số lượng
        holder.btnMinus.setOnClickListener(v -> {
            if (bookedFoodMap.containsKey(food.getId())) {
                bookedFoodMap.get(food.getId()).decreaseQuantity();
                if (bookedFoodMap.get(food.getId()).getQuantity() == 0) {
                    bookedFoodMap.remove(food.getId()); // Xóa nếu số lượng = 0
                }
            }
            notifyItemChanged(position);
        });
    }
    public Map<String, BookedFood> getBookedFoodMap() {
        return bookedFoodMap;
    }

    @Override
    public int getItemCount() {
        if(foodList != null)
        {
            return foodList.size();
        }
        return 0;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvPrice, tvQuantity, tvDesc;
        ImageView imgFood;
        ImageButton btnMinus, btnAdd;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDesc = itemView.findViewById(R.id.tv_desc_food);
            tvName = itemView.findViewById(R.id.tv_name_food);
            tvPrice = itemView.findViewById(R.id.tv_price_food);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            imgFood = itemView.findViewById(R.id.img_food);
            btnMinus = itemView.findViewById(R.id.btn_minus);
            btnAdd = itemView.findViewById(R.id.btn_add);
        }
    }
}
