package com.example.edutrackapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Plan> planList;
    PlanAdapter adapter;
    private Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        finding();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        planList = PlanManager.getPlanList();

        // Gắn adapter
        adapter = new PlanAdapter(planList, plan -> {
            // Xử lý khi item được click
            Intent intent = new Intent(MainActivity.this, DetailedActivity.class);
            intent.putExtra("Title", plan.getTitle());
            intent.putExtra("timeStart", plan.getTimeStart());
            intent.putExtra("timeEnd", plan.getTimeEnd());
            intent.putExtra("note", plan.getNote());
            intent.putExtra("date", plan.getDate());
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        setEvent();
    }
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
    void finding(){
        recyclerView = findViewById(R.id.recycler_view_item);
        add_button = findViewById(R.id.add_button);
    }
    void setEvent() {
        recyclerView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DetailedActivity.class);
            startActivity(intent);
        });
        add_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });
    }
}

