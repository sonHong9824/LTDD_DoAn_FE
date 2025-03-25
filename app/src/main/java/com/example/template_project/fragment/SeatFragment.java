package com.example.template_project.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

public class SeatFragment extends Fragment {
    private Showtime showtime;
    private RecyclerView recyclerViewSeats;
    private SeatAdapter seatAdapter;
    private List<Seat> mListSeat;
    private TextView tv_movie;
    private ImageButton btn_back;
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

        tv_movie = view.findViewById(R.id.tv_movie);
        tv_movie.setText(showtime.getMovie().getTitle());
        btn_back = view.findViewById(R.id.btn_back_seat);
        btn_back.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        recyclerViewSeats = view.findViewById(R.id.rc_seat);
        recyclerViewSeats.setLayoutManager(new GridLayoutManager(getContext(), 8)); // 9 ghế trên một hàng

        mListSeat = generateSeats();
        seatAdapter = new SeatAdapter(mListSeat, getContext());
        recyclerViewSeats.setAdapter(seatAdapter);

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
