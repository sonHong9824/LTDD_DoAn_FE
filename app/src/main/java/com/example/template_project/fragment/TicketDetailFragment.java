package com.example.template_project.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.template_project.R;
import com.example.template_project.adapter.BookedFoodAdapter;
import com.example.template_project.adapter.FoodAdapter;
import com.example.template_project.adapter.FoodTicketAdapter;
import com.example.template_project.model.Food;
import com.example.template_project.model.Ticket;
import com.example.template_project.retrofit.FoodApi;
import com.example.template_project.retrofit.RetrofitService;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketDetailFragment extends Fragment {
    private TextView tv_cinema_ve, tv_movie_ve, tv_lang_ve, tv_ma_ve, tv_time_ve, tv_date_ve, tv_room_ve, tv_seat_ve, tv_cinema_ve_2, tv_cinema_desc_ve;
    private RecyclerView rc_food_ve;
    private ImageView img_backdrop;
    private ImageView btn_back;
    private Ticket ticket;
    private FoodTicketAdapter adapter;
    private ArrayList<Food> foodList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_detail, container, false);

        tv_cinema_ve = view.findViewById(R.id.tv_cinema_ve);
        tv_movie_ve = view.findViewById(R.id.tv_movie_ve);
        tv_lang_ve = view.findViewById(R.id.tv_lang_ve);
        tv_ma_ve = view.findViewById(R.id.tv_ma_ve);
        tv_time_ve = view.findViewById(R.id.tv_time_ve);
        tv_date_ve = view.findViewById(R.id.tv_date_ve);
        tv_room_ve = view.findViewById(R.id.tv_room_ve);
        tv_seat_ve = view.findViewById(R.id.tv_seat_ve);
        tv_cinema_ve_2 = view.findViewById(R.id.tv_cinema_ve_2);
        tv_cinema_desc_ve = view.findViewById(R.id.tv_cinema_desc_ve);
        btn_back = view.findViewById(R.id.btn_back_ticket_detail);
        btn_back.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        rc_food_ve = view.findViewById(R.id.rc_food_ve);
        img_backdrop = view.findViewById(R.id.img_backdrop);

        if(getArguments() != null){
            ticket = (Ticket) getArguments().getSerializable("ticket");
        }
        loadFood();
        loadTicket();
        return view;
    }

    private void loadTicket() {
        if (ticket.getBookedFoods() != null) {
            Log.d("TICKET_BOOKED_FOODS", "Danh sách món ăn đã đặt: " + ticket.getBookedFoods().size());
        } else {
            Log.d("TICKET_BOOKED_FOODS", "Danh sách món ăn đã đặt rỗng");
        }
        tv_cinema_ve.setText(ticket.getShowtime().getCinema().getName());
        tv_movie_ve.setText(ticket.getShowtime().getMovie().getTitle());
        tv_lang_ve.setText(ticket.getShowtime().getMovie().getLanguage());
        tv_ma_ve.setText(String.valueOf(ticket.getId()));
        tv_time_ve.setText(ticket.getShowtime().getFormattedTimeRange());
        tv_date_ve.setText(ticket.getShowtime().getDateFormatted());
        tv_room_ve.setText(ticket.getShowtime().getRoom());
        tv_seat_ve.setText(ticket.getSeatListString());
        tv_cinema_ve_2.setText(ticket.getShowtime().getCinema().getName());
        tv_cinema_desc_ve.setText(ticket.getShowtime().getCinema().getLocation());
        Log.d("SEAT_LIST", "Danh sách ghế trong ticket: " + ticket.getSeatListString());
        Log.d("SEAT_COUNT", "Số lượng ghế: " + (ticket.getBookedSeat() != null ? ticket.getBookedSeat().size() : 0));

        Glide.with(this)
                .load(ticket.getShowtime().getMovie().getPosterUrl())
                .placeholder(R.drawable.placeholder_img)
                .error(R.drawable.error_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_backdrop);

        adapter = new FoodTicketAdapter(getContext(), ticket.getBookedFoods());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rc_food_ve.setLayoutManager(layoutManager);
        rc_food_ve.setAdapter(adapter);
    }
    private void loadFood() {
        RetrofitService retrofitService = new RetrofitService();
        FoodApi foodApi = retrofitService.getRetrofit().create(FoodApi.class);
        foodApi.getAllFood().enqueue(new Callback<ArrayList<Food>>() {
            @Override
            public void onResponse(Call<ArrayList<Food>> call, Response<ArrayList<Food>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    foodList = response.body();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Food>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi tải dữ liệu!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
