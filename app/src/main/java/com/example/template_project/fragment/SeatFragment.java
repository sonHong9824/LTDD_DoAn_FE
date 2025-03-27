package com.example.template_project.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.template_project.R;
import com.example.template_project.adapter.SeatAdapter;
import com.example.template_project.model.Movie;
import com.example.template_project.model.Seat;
import com.example.template_project.model.Showtime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SeatFragment extends Fragment {
    private Showtime showtime;
    private RecyclerView recyclerViewSeats;
    private SeatAdapter seatAdapter;
    private List<Seat> mListSeat;
    private TextView tv_cinema, tv_scope, tv_movie_2, tv_showtime_seat, tv_date_seat, tv_lang_seat;
    private ImageButton btn_back;
    private Button btn_next;
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seats, container, false);

        if (getArguments() != null) {
            showtime = (Showtime) getArguments().getSerializable("SHOWTIME_DATA");
            if (showtime != null) {
                Movie movie = showtime.getMovie(); // Lấy movie từ showtime

                if (movie != null) {
                    Log.d("SeatFragment", "Nhận phimm: " + movie.getTitle());
                } else {
                    Log.e("SeatFragment", "Lỗi: Movie NULL!");
                }
            } else {
                Log.e("SeatFragment", "Lỗi: Showtime NULL!");
            }
        } else {
            Log.e("SeatFragment", "Lỗi: Arguments NULL!");
        }

        tv_cinema = view.findViewById(R.id.tv_cinema_seat);
        tv_cinema.setText(showtime.getCinema().getName());
        btn_back = view.findViewById(R.id.btn_back_seat);
        btn_back.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        recyclerViewSeats = view.findViewById(R.id.rc_seat);
        recyclerViewSeats.setLayoutManager(new GridLayoutManager(getContext(), 8)); // 9 ghế trên một hàng

        mListSeat = generateSeats();
        seatAdapter = new SeatAdapter(mListSeat, getContext());
        recyclerViewSeats.setAdapter(seatAdapter);

        tv_scope = view.findViewById(R.id.tv_scope_seat);
        tv_movie_2 = view.findViewById(R.id.tv_movie_seat);
        tv_showtime_seat = view.findViewById(R.id.tv_showtime_seat);
        tv_date_seat = view.findViewById(R.id.tv_date_seat);
        tv_lang_seat = view.findViewById(R.id.tv_language_seat);
        btn_next = view.findViewById(R.id.btn_next_seat);

        tv_scope.setText(showtime.getMovie().getScope());
        tv_movie_2.setText(showtime.getMovie().getTitle());

        // Tính toán thời gian bắt đầu & kết thúc
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime showtimeStart = showtime.getShowtime();
        String formattedStart = showtimeStart.format(formatter);

        int duration = showtime.getMovie().getDuration();
        LocalDateTime showtimeEnd = showtimeStart.plusMinutes(duration);
        String formattedEnd = showtimeEnd.format(formatter);

        tv_showtime_seat.setText(formattedStart + " ~ " + formattedEnd);

        // Định dạng ngày dd-MM-yyyy
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String showDate = showtime.getShowtime().toLocalDate().format(dateFormatter);

        tv_date_seat.setText(showDate);

        tv_lang_seat.setText(showtime.getMovie().getLanguage());

        return view;
    }

    private List<Seat> generateSeats() {
        List<Seat> seats = new ArrayList<>();
        String[] rows = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for (String row : rows) {
            for (int i = 1; i <= 8; i++) {
                seats.add(new Seat(row + i));
            }
        }
        return seats;
    }
}
