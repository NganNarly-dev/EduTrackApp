package com.example.edutrackapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.*;

public class AddActivity extends AppCompatActivity {
    private Button cancel_button;
    private Button save_button;
    private EditText add_title;
    private EditText add_note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        finding();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setEven();
    }
    void finding(){
        add_title = findViewById(R.id.add_title);
        add_note = findViewById(R.id.note);
        cancel_button = findViewById(R.id.cancel_button);
        save_button = findViewById(R.id.save_button);
    }
    void setEven(){
        add_title.setOnClickListener(v -> {
            add_title.setText("");
        });
        add_note.setOnClickListener(v -> {
            add_note.setText("");
        });
        cancel_button.setOnClickListener(v -> {
            finish();
        });
        save_button.setOnClickListener(v -> {
            String title = add_title.getText().toString().trim();
            String note = add_note.getText().toString();

            if (title.isEmpty()) {
                Toast.makeText(AddActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            Plan plan = new Plan(title,"8:00", "9:30", note);
            PlanManager.addPlan(plan);
            Toast.makeText(this, "Đã thêm: " + title, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}