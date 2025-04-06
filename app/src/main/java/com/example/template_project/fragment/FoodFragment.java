package com.example.template_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.template_project.R;
import com.example.template_project.adapter.FoodAdapter;
import com.example.template_project.model.BookedFood;
import com.example.template_project.model.Food;
import com.example.template_project.model.Showtime;
import com.example.template_project.retrofit.FoodApi;
import com.example.template_project.retrofit.RetrofitService;
import com.example.template_project.retrofit.SeatApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodFragment extends Fragment {
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<Food> foodList = new ArrayList<>();
    private Showtime showtime;
    private List<String> selectedSeats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        if (getArguments() != null) {
            showtime = (Showtime) getArguments().getSerializable("SHOWTIME_DATA");
            selectedSeats = getArguments().getStringArrayList("SELECTED_SEATS");

            if (showtime != null) {
                Log.d("FoodFragment", "Nhận phim: " + showtime.getMovie().getTitle());
            } else {
                Log.e("FoodFragment", "Lỗi: Showtime NULL!");
            }

            if (selectedSeats != null) {
                Log.d("FoodFragment", "Ghế đã chọn: " + selectedSeats);
            } else {
                Log.e("FoodFragment", "Lỗi: Không nhận được danh sách ghế!");
            }
        }

        recyclerView = view.findViewById(R.id.rc_food);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadFood();


        return view;
    }

    private void loadFood() {
        RetrofitService retrofitService = new RetrofitService();
        FoodApi foodApi = retrofitService.getRetrofit().create(FoodApi.class);
        foodApi.getAllFood().enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    foodList = response.body();
                    foodAdapter = new FoodAdapter(getContext(), foodList);
                    recyclerView.setAdapter(foodAdapter);

                    getBookedFoodList();
                    for (int i = 0; i < foodList.size(); i++) {
                        Log.d("FoodDebug", "Food " + i + ": name=" + foodList.get(i).getName() + ", imageUrl=" + foodList.get(i).getpictureUrl());
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi tải dữ liệu!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Lấy danh sách món ăn đã đặt
    private void getBookedFoodList() {
        if (foodAdapter != null) {
            Map<String, BookedFood> bookedFoodMap = foodAdapter.getBookedFoodMap();
            for (BookedFood bookedFood : bookedFoodMap.values()) {
                Log.d("BookedFood", "Món ăn: " + bookedFood.getId() + ", Số lượng: " + bookedFood.getQuantity());
            }
        }
    }
}