package com.example.edutrackapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder>{
    private final List<DayItem> dayList;
    private final OnDayClickListener listener;
    private int selected = 0;
    private Button btnDay;

    public interface OnDayClickListener {
        void onDayClick(int position, DayItem day);
    }
    public static class DayItem {
        public final String dayName;
        public final String dayNumber;
        public final String date;

        public DayItem(String dayName, String dayNumber, String date) {
            this.dayName = dayName;
            this.dayNumber = dayNumber;
            this.date = date;
        }
        public String getDayName() { return dayName; }
        public String getDayNumber() { return dayNumber; }
        public String getDate() { return date; }
    }


    public DayAdapter(List<DayItem> dayList, int selected, OnDayClickListener listener) {
        this.dayList = dayList;
        this.selected = selected;
        this.listener = listener;
    }
    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day, parent, false);
        return new DayViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        DayItem day = dayList.get(position);
        holder.bind(day, position);
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }
    public void setSelected(int pos) {
        if (pos < 0 || pos >= dayList.size()) return;
        int old = selected;
        selected = pos;
        notifyItemChanged(old);
        notifyItemChanged(selected);
    }
    class DayViewHolder extends RecyclerView.ViewHolder {
        private final Button btnDay;

        DayViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDay = itemView.findViewById(R.id.tv_day_name);
        }

        void bind(DayItem day, int position) {
            btnDay.setText(day.dayName + "\n" + day.dayNumber);

            if (position == selected) {
                btnDay.setBackgroundResource(R.drawable.button_day_selected);
                btnDay.setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                btnDay.setBackgroundResource(R.drawable.button_day);
                btnDay.setTextColor(Color.BLACK);
            }
            btnDay.setOnClickListener(v -> {
                setSelected(position);
                if (listener != null) listener.onDayClick(position, day);
            });
        }
    }
}
