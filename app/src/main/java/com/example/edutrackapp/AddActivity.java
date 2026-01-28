package com.example.edutrackapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.*;

public class AddActivity extends AppCompatActivity {
    private Button cancel_button;
    private Button save_button;
    private EditText add_title;
    private EditText add_note;
    private EditText add_time_start;
    private EditText add_time_end;
    private View icon_lich;
    private TextView lichText;
    private int editIndex = -1;



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

        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }else{
            editIndex = intent.getIntExtra("index", -1);

            String title = intent.getStringExtra("Title");
            String timeStart = intent.getStringExtra("timeStart");
            String timeEnd = intent.getStringExtra("timeEnd");
            String note_detail = intent.getStringExtra("note");
            String date = intent.getStringExtra("date");

            if (title != null) add_title.setText(title);
            if (note_detail != null) add_note.setText(note_detail);
            if (timeStart != null) add_time_start.setText(timeStart);
            if (timeEnd != null) add_time_end.setText(timeEnd);
            if (date != null) lichText.setText(date);
        }
        setEven();
    }
    void finding(){
        lichText = findViewById(R.id.lich);
        icon_lich = findViewById(R.id.icon_lich);
        add_title = findViewById(R.id.add_title);
        add_note = findViewById(R.id.note);
        add_time_start = findViewById(R.id.time_start);
        add_time_end = findViewById(R.id.time_end);
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
        add_time_start.setOnClickListener(v -> {
            add_time_start.setText("");
        });
        add_time_end.setOnClickListener(v -> {
            add_time_end.setText("");
        });
        cancel_button.setOnClickListener(v -> {
            finish();
        });
        icon_lich.setOnClickListener(v -> {
            showCalendarDialog(lichText);
        });
        save_button.setOnClickListener(v -> {
            String title = add_title.getText().toString().trim();
            String note = add_note.getText().toString();
            String timeStart = add_time_start.getText().toString();
            String timeEnd = add_time_end.getText().toString();
            String date = lichText.getText().toString();


            if (title.isEmpty() || timeStart.isEmpty() || timeEnd.isEmpty()) {
                Toast.makeText(AddActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            Plan plan = new Plan(title, timeStart, timeEnd, note, date);
            if (editIndex >= 0 && PlanManager.getPlan(editIndex) != null) {
                PlanManager.updatePlan(editIndex, plan);
                Toast.makeText(this, "Đã cập nhật: " + title, Toast.LENGTH_SHORT).show();
            } else {
                PlanManager.addPlan(plan);
                Toast.makeText(this, "Đã thêm: " + title, Toast.LENGTH_SHORT).show();
            }
            Intent result = new Intent();
            result.putExtra("editedIndex", editIndex);
            setResult(RESULT_OK, result);
            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
    private void showCalendarDialog(TextView lichText) {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Chọn ngày")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("E, dd/MM", Locale.getDefault());
            String date = sdf.format(new Date(selection));
            lichText.setText(date);
        });
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

}