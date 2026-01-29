package com.example.edutrackapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
        addTimeFormatWatcher(add_time_start);
        addTimeFormatWatcher(add_time_end);

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
            if (date != null) {
                try {
                    SimpleDateFormat sdfSave = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    SimpleDateFormat sdfDisplay = new SimpleDateFormat("E, dd/MM", Locale.getDefault());
                    Date d = sdfSave.parse(date);
                    if (d != null) {
                        lichText.setText(sdfDisplay.format(d));
                        lichText.setTag(date); // gán lại tag để lưu đúng định dạng
                    }
                } catch (Exception e) {
                    lichText.setText(date); // fallback nếu lỗi
                }
            }

        }
        setEven();
    }
    private void addTimeFormatWatcher(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting = false;
            private int cursorPosition = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!isFormatting) {
                    cursorPosition = start + after;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isFormatting) return;
                isFormatting = true;

                // Loại bỏ các ký tự không phải số
                String text = s.toString().replaceAll("[^0-9]", "");

                // Giới hạn 4 số (HHmm)
                if (text.length() > 4) {
                    text = text.substring(0, 4);
                }

                // Tự động thêm dấu ":"
                if (text.length() >= 3) {
                    text = text.substring(0, 2) + ":" + text.substring(2);
                }

                // Set text mới
                editText.setText(text);

                // Đặt con trỏ ở cuối
                int newCursorPos = text.length();
                if (cursorPosition == 2 && text.length() >= 3) {
                    newCursorPos = 3; // Nhảy qua dấu ":"
                }
                editText.setSelection(Math.min(newCursorPos, text.length()));

                isFormatting = false;
            }
        });
    }
    private boolean isValidTime(String time) {
        if (!time.matches("\\d{2}:\\d{2}")) return false;
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        return hour >= 0 && hour < 24 && minute >= 0 && minute < 60;
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
            showCancelConfirmationDialog();
        });
        icon_lich.setOnClickListener(v -> {
            showCalendarDialog(lichText);
        });
        save_button.setOnClickListener(v -> {
            String title = add_title.getText().toString().trim();
            String note = add_note.getText().toString();
            String timeStart = add_time_start.getText().toString();
            String timeEnd = add_time_end.getText().toString();
            String date = (String) lichText.getTag();


            if (title.isEmpty() || timeStart.isEmpty() || timeEnd.isEmpty()) {
                Toast.makeText(AddActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isValidTime(timeStart)) {
                add_time_start.setError("Thời gian không hợp lệ (00:00 - 23:59)");
                return;
            }
            if (!isValidTime(timeEnd)) {
                add_time_end.setError("Thời gian không hợp lệ (00:00 - 23:59)");
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
    private void showCancelConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm cancellation")
                .setMessage("Are you sure you want to cancel? Unsaved data will be lost.")
                .setPositiveButton("Cancel", (dialog, which) -> {
                    finish();
                })
                .setNegativeButton("Continue", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
    private void showCalendarDialog(TextView lichText) {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Choose a date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdfSave = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            // Hiển thị dạng E, dd/MM cho user
            SimpleDateFormat sdfDisplay = new SimpleDateFormat("E, dd/MM", Locale.getDefault());

            String dateToSave = sdfSave.format(new Date(selection));
            String dateToDisplay = sdfDisplay.format(new Date(selection));

            // Hiển thị cho user
            lichText.setText(dateToDisplay);
            // Lưu tag để dùng khi save
            lichText.setTag(dateToSave);
        });
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

}