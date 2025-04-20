package com.example.template_project.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.template_project.R;
import com.example.template_project.SharedPreferences.PrefUser;
import com.example.template_project.adapter.FoodAdapter;
import com.example.template_project.adapter.TicketAdapter;
import com.example.template_project.model.Ticket;
import com.example.template_project.retrofit.RetrofitService;
import com.example.template_project.retrofit.TicketApi;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTicketFragment extends Fragment {
    private List<Ticket> mListTicket;
    private RecyclerView recyclerView;
    private TicketAdapter ticketAdapter;
    private PrefUser prefUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myticket, container, false);
        recyclerView = view.findViewById(R.id.rc_ticket);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        prefUser = new PrefUser(requireContext());

        getTickets();

        return view;
    }

    private void getTickets() {
        RetrofitService retrofitService = new RetrofitService();
        TicketApi ticketApi = retrofitService.getRetrofit().create(TicketApi.class);
        ticketApi.findbyuser(prefUser.getId()).enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mListTicket = response.body();
                    ticketAdapter = new TicketAdapter(mListTicket, getContext(), new TicketAdapter.OnTicketClickListener() {
                        @Override
                        public void onTicketClick(Ticket ticket) {
                            TicketDetailFragment fragment = new TicketDetailFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("ticket", ticket);
                            fragment.setArguments(bundle);

                            requireActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    });
                    recyclerView.setAdapter(ticketAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi khi tải dữ liệu vé", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
