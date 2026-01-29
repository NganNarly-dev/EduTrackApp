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

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView recyclerView1;
    List<Plan> planList;
    PlanAdapter adapter;
    private Button add_button;
    private DayAdapter dayAdapter;
    private int todayPosition = 0;
    private String currentSelectedDate = null;
    List<Plan> fullPlanList = new ArrayList<>();
    List<Plan> filteredPlanList = new ArrayList<>();


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
        //planList = PlanManager.getPlanList();
        fullPlanList = new ArrayList<>(PlanManager.getPlanList());
        filteredPlanList = new ArrayList<>();
        // Gắn adapter
        adapter = new PlanAdapter(filteredPlanList, plan -> {
            // Xử lý khi item được click
            Intent intent = new Intent(MainActivity.this, DetailedActivity.class);
            intent.putExtra("Title", plan.getTitle());
            intent.putExtra("timeStart", plan.getTimeStart());
            intent.putExtra("timeEnd", plan.getTimeEnd());
            intent.putExtra("note", plan.getNote());
            intent.putExtra("date", plan.getDate());
            startActivity(intent);
        });
        // Tạo danh sách ngày (15 ngày: 7 ngày trước + hôm nay + 7 ngày sau)
        List<DayAdapter.DayItem> dayItems = buildDayList(7);
        todayPosition = 7;
        currentSelectedDate = dayItems.get(todayPosition).date;
        // Khởi tạo DayAdapter
        dayAdapter = new DayAdapter(dayItems, todayPosition, (position, day) -> {
            currentSelectedDate = day.date;
            filterPlansByDate(currentSelectedDate);
        });

        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView1.setAdapter(dayAdapter);
        recyclerView1.scrollToPosition(todayPosition);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        filterPlansByDate(currentSelectedDate);
        setupDraggableButton(add_button);
        setEvent();
    }

    private void setupDraggableButton(View button) {
        final float[] dX = {0};
        final float[] dY = {0};
        final float[] startX = {0};
        final float[] startY = {0};
        final boolean[] isDragging = {false};

        button.setOnTouchListener((view, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    dX[0] = view.getX() - event.getRawX();
                    dY[0] = view.getY() - event.getRawY();
                    startX[0] = event.getRawX();
                    startY[0] = event.getRawY();
                    isDragging[0] = false;
                    return true;

                case MotionEvent.ACTION_MOVE:
                    isDragging[0] = true;
                    float newX = event.getRawX() + dX[0];
                    float newY = event.getRawY() + dY[0];

                    // Giới hạn trong màn hình
                    View parent = (View) view.getParent();
                    newX = Math.max(0, Math.min(newX, parent.getWidth() - view.getWidth()));
                    newY = Math.max(0, Math.min(newY, parent.getHeight() - view.getHeight()));

                    view.setX(newX);
                    view.setY(newY);
                    return true;

                case MotionEvent.ACTION_UP:
                    float deltaX = Math.abs(event.getRawX() - startX[0]);
                    float deltaY = Math.abs(event.getRawY() - startY[0]);

                    if (deltaX < 10 && deltaY < 10) {
                        Intent intent = new Intent(MainActivity.this, AddActivity.class);
                        startActivity(intent);
                    }
                    return true;
            }
            return false;
        });
    }
    private List<DayAdapter.DayItem> buildDayList(int daysAround) {
        List<DayAdapter.DayItem> list = new ArrayList<>();
        SimpleDateFormat sdfDay = new SimpleDateFormat("EEE", Locale.ENGLISH);
        SimpleDateFormat sdfNumber = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat sdfFullDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -daysAround);

        for (int i = 0; i < daysAround * 2 + 1; i++) {
            String dayName = sdfDay.format(cal.getTime());
            String dayNumber = sdfNumber.format(cal.getTime());
            String fullDate = sdfFullDate.format(cal.getTime());
            list.add(new DayAdapter.DayItem(dayName, dayNumber, fullDate));
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        return list;
    }
    private void filterPlansByDate(String yyyyMMdd) {
        if (yyyyMMdd == null) return;
        filteredPlanList.clear();
        for (Plan p : fullPlanList) {
            String planDate = normalizePlanDate(p.getDate());
            if (planDate != null && planDate.equals(yyyyMMdd)) {
                filteredPlanList.add(p);
            }
        }

        // Sắp xếp theo thời gian bắt đầu (HH:mm) tăng dần
        Collections.sort(filteredPlanList, new Comparator<Plan>() {
            @Override
            public int compare(Plan p1, Plan p2) {
                String time1 = p1.getTimeStart().replace(":", "");
                String time2 = p2.getTimeStart().replace(":", "");
                try {
                    int t1 = Integer.parseInt(time1);
                    int t2 = Integer.parseInt(time2);
                    return Integer.compare(t1, t2);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        });
        adapter.notifyDataSetChanged();
    }
    private String normalizePlanDate(String raw) {
        if (raw == null) return null;
        List<SimpleDateFormat> candidates = Arrays.asList(
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()),
                new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()),
                new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        );
        for (SimpleDateFormat fmt : candidates) {
            try {
                fmt.setLenient(false);
                Date d = fmt.parse(raw);
                if (d != null) {
                    SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    return out.format(d);
                }
            } catch (ParseException ignored) {}
        }
        return null;
    }
    @Override
    protected void onResume() {
        super.onResume();
        fullPlanList.clear();
        fullPlanList.addAll(PlanManager.getPlanList());

        if (currentSelectedDate != null) {
            filterPlansByDate(currentSelectedDate);
        } else {
            filteredPlanList.clear();
            filteredPlanList.addAll(fullPlanList);
            adapter.notifyDataSetChanged();
        }
    }
    void finding(){
        recyclerView1 = findViewById(R.id.recycler_view_day);
        recyclerView = findViewById(R.id.recycler_view_item);
        add_button = findViewById(R.id.add_button);
    }
    void setEvent() {
        recyclerView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DetailedActivity.class);
            startActivity(intent);
        });
//        add_button.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, AddActivity.class);
//            startActivity(intent);
//        });
    }
}

