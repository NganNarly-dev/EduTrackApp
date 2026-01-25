package com.example.edutrackapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.*;

public class AddActivity extends AppCompatActivity {
    private Button cancel_button;
    private Button save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    void finding(){
        cancel_button = findViewById(R.id.cancel_button);
        save_button = findViewById(R.id.save_button);
    }
    void setEven(){
        cancel_button.setOnClickListener(v -> {
            finish();
        });
        save_button.setOnClickListener(v -> {
           Intent intent = new Intent(AddActivity.this, MainActivity.class);
           startActivity(intent);
        });
    }
}