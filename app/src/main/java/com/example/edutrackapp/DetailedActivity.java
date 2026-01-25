package com.example.edutrackapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailedActivity extends AppCompatActivity {
    private Button fix_button;
    private Button delete_button;
    private Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        finding();
        setEven();
    }
    void finding(){
        fix_button = findViewById(R.id.fix_button);
        delete_button = findViewById(R.id.delete_button);
        back_button = findViewById(R.id.back);
    }
    void setEven(){
        fix_button.setOnClickListener(v -> {
            Intent intent = new Intent(DetailedActivity.this, AddActivity.class);
            startActivity(intent);
        });
        delete_button.setOnClickListener(v -> {
            finish();
        });
        back_button.setOnClickListener(v -> {
            finish();
        });
    }
}