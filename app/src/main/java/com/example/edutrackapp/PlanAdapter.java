package com.example.edutrackapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {
    private List<Plan> planList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Plan plan);
    }

    public PlanAdapter(List<Plan> planList, OnItemClickListener listener) {
        this.planList = planList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new PlanViewHolder(view);
    }
    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        Plan plan = planList.get(position);
        holder.tvTitle.setText(plan.getTitle());
        holder.tvTime.setText(plan.getTimeStart() + " - " + plan.getTimeEnd());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(plan);
            }
        });
    }
    @Override
    public int getItemCount() {
        return planList.size();
    }
    public static class PlanViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvTime;
        AppCompatCheckBox customCheckBox;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title);
            tvTime = itemView.findViewById(R.id.time);
            customCheckBox = itemView.findViewById(R.id.customCheckBox);
        }
    }
}
