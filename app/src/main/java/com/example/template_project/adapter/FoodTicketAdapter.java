package com.example.template_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.template_project.R;
import com.example.template_project.model.BookedFood;
import com.example.template_project.model.Food;

import java.text.DecimalFormat;
import java.util.List;

public class FoodTicketAdapter extends RecyclerView.Adapter<FoodTicketAdapter.FoodTicketViewHolder>{
    private Context context;
    private List<BookedFood> bookedFoods;

    public FoodTicketAdapter(Context context, List<BookedFood> bookedFoods) {
        this.context = context;
        this.bookedFoods = bookedFoods;
    }

    @NonNull
    @Override
    public FoodTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food_ticket_detail, parent, false);
        return new FoodTicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodTicketViewHolder holder, int position) {
        BookedFood bookedFood = bookedFoods.get(position);

        String quantity = String.valueOf(bookedFood.getQuantity());
        String name = bookedFood.getFood().getName();
        holder.tv_food.setText(quantity + " x " + name);
    }

    @Override
    public int getItemCount() {
        if(bookedFoods != null){
            return  bookedFoods.size();
        }
        return 0;
    }

    public class FoodTicketViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_food;
        public FoodTicketViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_food = itemView.findViewById(R.id.tv_food);
        }
    }
}
