package com.example.template_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.template_project.Api.CreateOrder;
import com.example.template_project.R;
import com.example.template_project.SharedPreferences.PrefUser;
import com.example.template_project.adapter.BookedFoodAdapter;
import com.example.template_project.model.BookedFood;
import com.example.template_project.model.FeatureMovie;
import com.example.template_project.model.Food;
import com.example.template_project.model.Showtime;
import com.example.template_project.model.Ticket;
import com.example.template_project.model.TicketRequest;
import com.example.template_project.retrofit.MovieApi;
import com.example.template_project.retrofit.RetrofitService;
import com.example.template_project.retrofit.TicketApi;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class BookingFragment extends Fragment {
    private ImageView img_movie_booking;
    private TextView tv_name_cinema_booking, tv_movie_name, tv_scope_booking, tv_scope_desc_booking, tv_time_start, tv_room, tv_language_booking
            , tv_seats, tv_name_user, tv_email_user, tv_total_price_booking, tv_2, tv_date_booking;
    private Button btn_next_booking;
    private ImageButton btn_back_booking;
    private RecyclerView rc_food_booking;
    private ArrayList<BookedFood> bookedFoods;
    private Showtime showtime;
    private int priceSeat, priceFood;
    private ArrayList<String> selectedSeats;
    private ArrayList<Food> foods;
    private BookedFoodAdapter adapter;
    private PrefUser prefUser;
    DecimalFormat decimalFormat = new DecimalFormat("#,### 'đ'");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_booking, container, false);
        prefUser = new PrefUser(requireContext());
        String userId = prefUser.getId();

        tv_name_cinema_booking = view.findViewById(R.id.tv_name_cinema_booking);
        tv_movie_name = view.findViewById(R.id.tv_movie_name);
        tv_scope_booking = view.findViewById(R.id.tv_scope_booking);
        tv_scope_desc_booking = view.findViewById(R.id.tv_scope_desc_booking);
        tv_time_start = view.findViewById(R.id.tv_time_start);
        tv_room = view.findViewById(R.id.tv_room);
        tv_language_booking = view.findViewById(R.id.tv_language_booking);
        tv_seats = view.findViewById(R.id.tv_seats);
        tv_name_user = view.findViewById(R.id.tv_name_user);
        tv_email_user = view.findViewById(R.id.tv_email_user);
        tv_2 = view.findViewById(R.id.tv_2);
        tv_total_price_booking = view.findViewById(R.id.tv_total_price_booking);
        img_movie_booking = view.findViewById(R.id.img_movie_booking);
        tv_date_booking = view.findViewById(R.id.tv_date_booking);
        rc_food_booking = view.findViewById(R.id.rc_food_booking);
        btn_back_booking = view.findViewById(R.id.btn_back_booking);
        btn_next_booking = view.findViewById(R.id.btn_next_booking);
        btn_back_booking.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        if (getArguments() != null) {
            bookedFoods = (ArrayList<BookedFood>) getArguments().getSerializable("BOOKED_FOODS");
            showtime = (Showtime) getArguments().getSerializable("SHOWTIME_DATA");
            selectedSeats = getArguments().getStringArrayList("SELECTED_SEATS");
            priceSeat = getArguments().getInt("PRICE_SEATS");
            priceFood = getArguments().getInt("PRICE_FOODS");
            foods = (ArrayList<Food>) getArguments().getSerializable("FOOD_LIST");
            if (bookedFoods != null && !bookedFoods.isEmpty()) {
                tv_2.setVisibility(View.VISIBLE);
                rc_food_booking.setVisibility(View.VISIBLE);
            } else {
                tv_2.setVisibility(View.GONE);
                rc_food_booking.setVisibility(View.GONE);
            }
        }
        adapter = new BookedFoodAdapter(getContext(), bookedFoods, foods);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rc_food_booking.setLayoutManager(layoutManager);
        rc_food_booking.setAdapter(adapter);
        displayBookingDetails();

        List<TicketRequest.BookedFoodRequest> bookedFoodRequests = new ArrayList<>();
        for (BookedFood bookedFood : bookedFoods) {
            bookedFoodRequests.add(new TicketRequest.BookedFoodRequest(bookedFood.getId(), bookedFood.getQuantity()));
        }
        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setUserId(userId);
        Log.d("userid:", userId);
        ticketRequest.setBookedFoods(bookedFoodRequests);
        ticketRequest.setSeats(selectedSeats);
        ticketRequest.setPrice(priceFood + priceSeat);
        ticketRequest.setShowtimeId(showtime.getId());

        btn_next_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG_TICKET", "Showtime ID: " + ticketRequest.getShowtimeId());
                Log.d("DEBUG_TICKET", "User ID: " + ticketRequest.getUserId());
                Log.d("DEBUG_TICKET", "Price: " + ticketRequest.getPrice());
                Log.d("DEBUG_TICKET", "Seats: " + ticketRequest.getSeats().toString());
//                PaymentFragment paymentFragment = new PaymentFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("AMOUNT", String.valueOf(ticketRequest.getPrice()));
//
//                paymentFragment.setArguments(bundle);
//
//                requireActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.content_frame, paymentFragment)
//                        .addToBackStack(null)
//                        .commit();
                CreateOrder orderApi = new CreateOrder();

                try {
                    JSONObject data = orderApi.createOrder(String.valueOf(ticketRequest.getPrice()));
                    Log.d("Amount", String.valueOf(ticketRequest.getPrice()));
                    String code = data.getString("return_code");

                    if (code.equals("1")) {
                        String token = data.getString("zp_trans_token");
                        ZaloPaySDK.getInstance().payOrder(requireActivity(), token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String s, String s1, String s2) {
                                RetrofitService retrofitService = new RetrofitService();
                                TicketApi ticketApi = retrofitService.getRetrofit().create(TicketApi.class);
                                ticketApi.create(ticketRequest).enqueue(new Callback<Ticket>() {
                                    @Override
                                    public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                                        if (response.isSuccessful() && response.body() != null) {
                                            Ticket ticket = response.body();
                                            Log.d("TICKET_CREATED", "Ticket ID: " + ticket.getId());
                                            new AlertDialog.Builder(requireContext())
                                                    .setTitle("Đặt vé thành công")
                                                    .setMessage("Bạn đã đặt vé thành công! Cảm ơn bạn đã sử dụng dịch vụ.")
                                                    .setCancelable(false)
                                                    .setPositiveButton("OK", (dialog, which) -> {
                                                        HomeFragment homeFragment = new HomeFragment();
                                                        requireActivity().getSupportFragmentManager()
                                                                .beginTransaction()
                                                                .replace(R.id.content_frame, homeFragment)
                                                                .commit();
                                                    })
                                                    .show();
                                        } else {
                                            Log.e("TICKET_ERROR", "Lỗi response: " + response.code());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Ticket> call, Throwable t) {
                                        Log.e("TICKET_API_FAIL", "Lỗi mạng hoặc server: " + t.getMessage());
                                    }
                                });
                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {

                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {

                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private void displayBookingDetails() {
        String nameUser = prefUser.getName();
        String emailUser = prefUser.getEmail();

        tv_name_cinema_booking.setText(showtime.getCinema().getName());
        tv_movie_name.setText(showtime.getMovie().getTitle());
        String scope = showtime.getMovie().getScope();
        tv_scope_booking.setText(scope);
        if (scope.equals("P")) {
            tv_scope_desc_booking.setText("Phim được phép phổ biến đến người xem ở mọi độ tuổi");
        } else {
            scope = scope.substring(0, scope.length() - 1);
            tv_scope_desc_booking.setText("Phim được phép phổ biến đến người xem từ đủ " + scope + " tuổi trở lên");
        }
        tv_time_start.setText(showtime.getFormattedTimeRange());
        tv_room.setText("Cinema " + showtime.getRoom());
        tv_language_booking.setText(showtime.getMovie().getLanguage());
        tv_seats.setText(selectedSeats.toString().replace("[", "").replace("]", ""));
        tv_name_user.setText(nameUser);
        tv_email_user.setText(emailUser);
        Glide.with(this)
                .load(showtime.getMovie().getPosterUrl())
                .placeholder(R.drawable.placeholder_img)
                .error(R.drawable.error_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_movie_booking);
        int price = priceFood + priceSeat;
        tv_total_price_booking.setText(decimalFormat.format(price));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        tv_date_booking.setText(showtime.getShowtime().format(formatter));
    }
}
