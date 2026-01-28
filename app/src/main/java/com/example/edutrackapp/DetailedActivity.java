package com.example.edutrackapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class DetailedActivity extends AppCompatActivity {
    private Button fix_button;
    private Button delete_button;
    private FrameLayout back_button;
    private TextView add_title;
    private TextView lich;
    private TextView time_start;
    private TextView time_end;
    private TextView note;
    List<Plan> planList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed);
        finding();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        planList = PlanManager.getPlanList();
        // Lấy dữ liệu từ intent
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        String title = intent.getStringExtra("Title");
        String timeStart = intent.getStringExtra("timeStart");
        String timeEnd = intent.getStringExtra("timeEnd");
        String note_detail = intent.getStringExtra("note");
        String date = intent.getStringExtra("date");
        if (title == null || timeStart == null || timeEnd == null) {
            finish();
            return;
        }
        add_title.setText(title);
        lich.setText(date);
        time_start.setText(timeStart);
        time_end.setText(timeEnd);
        note.setText(note_detail);

        setEven();
    }
    void finding(){
        add_title = findViewById(R.id.add_title);
        lich = findViewById(R.id.lich);
        time_start = findViewById(R.id.time_start);
        time_end = findViewById(R.id.time_end);
        note = findViewById(R.id.note);
        fix_button = findViewById(R.id.fix_button);
        delete_button = findViewById(R.id.delete_button);
        back_button = findViewById(R.id.back);
    }
    void setEven(){
        fix_button.setOnClickListener(v -> {
            Intent intent = new Intent(DetailedActivity.this, AddActivity.class);
            intent.putExtra("index", 0);
            intent.putExtra("Title", add_title.getText().toString());
            intent.putExtra("timeStart", time_start.getText().toString());
            intent.putExtra("timeEnd", time_end.getText().toString());
            intent.putExtra("note", note.getText().toString());
            intent.putExtra("date", lich.getText().toString());
            startActivity(intent);
        });
        delete_button.setOnClickListener(v -> {
            planList.remove(0);
            Toast.makeText(DetailedActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
            finish();
        });
        back_button.setOnClickListener(v -> {
            finish();
        });
    }
}