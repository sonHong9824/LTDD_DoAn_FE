package com.example.template_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.template_project.R;
import com.example.template_project.model.Cinema;
import com.example.template_project.model.Showtime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ExShowtimeAdapter extends BaseExpandableListAdapter {

    private List<Cinema> mListCinema;
    private Map<Cinema, List<Showtime>> mListShowtime;

    public ExShowtimeAdapter(List<Cinema> mListCinema, Map<Cinema, List<Showtime>> mListShowtime) {
        this.mListCinema = mListCinema;
        this.mListShowtime = mListShowtime;
    }

    @Override
    public int getGroupCount() {
        if (mListCinema!=null)
        {
            return mListCinema.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int i) {
        if(mListCinema != null && mListShowtime != null){
            return mListShowtime.get(mListCinema.get(i)).size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return mListCinema.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mListShowtime.get(mListCinema.get(i)).get(i);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_group_showtime, viewGroup, false);
        }
        TextView tv_cinema = view.findViewById(R.id.tv_name_cinema);
        Cinema cinema = mListCinema.get(i);
        tv_cinema.setText(cinema.getName());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_showtime, viewGroup, false);
        }

        TextView tv_showtimeStart = view.findViewById(R.id.tv_showtimeStart);
        TextView tv_showtimeEnd = view.findViewById(R.id.tv_showtimeEnd);

        Showtime showtime = mListShowtime.get(mListCinema.get(i)).get(i1);

        // Định dạng thời gian HH:mm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        // Lấy thời gian bắt đầu
        LocalDateTime showtimeStart = showtime.getShowtime();
        String formattedStart = showtimeStart.format(formatter);

        // Cộng thêm thời lượng phim
        int duration = showtime.getMovie().getDuration();
        LocalDateTime showtimeEnd = showtimeStart.plusMinutes(duration);
        String formattedEnd = showtimeEnd.format(formatter);

        // Hiển thị thời gian
        tv_showtimeStart.setText(formattedStart);
        tv_showtimeEnd.setText(formattedEnd);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
