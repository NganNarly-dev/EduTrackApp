package com.example.edutrackapp;

import java.util.ArrayList;
import java.util.List;

public class PlanManager {
    private static List<Plan> planList = new ArrayList<>();

    public static List<Plan> getPlanList() {
        return planList;
    }
    public static void addPlan(Plan plan) {
        planList.add(plan);
    }
}
