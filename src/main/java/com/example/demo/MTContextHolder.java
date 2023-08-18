package com.example.demo;

public class MTContextHolder {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void setMTLabel(Integer mtLabel) {
        threadLocal.set(mtLabel);
    }

    public static Integer getMTLabel() {
        return threadLocal.get();
    }

    public static void clearMTLabel() {
        threadLocal.remove();
    }
}
