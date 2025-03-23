package com.example.template_project.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateItem {
    private String date;  // Dạng yyyy-MM-dd (dùng để gọi API)
    private String displayDate; // Dạng hiển thị: "CN, 24/03"

    public DateItem(String date, String displayDate) {
        this.date = date;
        this.displayDate = displayDate;
    }

    public String getDate() {
        return date;
    }

    public String getDisplayDate() {
        return displayDate;
    }
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        return sdf.format(date);
    }
}
