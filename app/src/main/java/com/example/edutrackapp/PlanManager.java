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
    public static void updatePlan(int index, Plan plan) {
        if (index >= 0 && index < planList.size()) {
            planList.set(index, plan);
        }
    }
    public static Plan getPlan(int index) {
        if (index >= 0 && index < planList.size()) {
            return planList.get(index);
        }
        return null;
    }
}
