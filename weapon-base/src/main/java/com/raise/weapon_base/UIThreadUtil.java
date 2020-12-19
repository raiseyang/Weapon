package com.raise.weapon_base;

import android.os.Handler;
import android.os.Looper;

/**
 * UI线程管理工具
 * 1. 持有主线程handler
 * 2. 判断某个线程是否是主线程
 * 3. 向主线程post()事件
 */
public class UIThreadUtil {

    private static final Handler mainHandler;

    static {
        mainHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 当前线程是否是 UI 线程
     */
    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    /**
     * 回调到主线程
     */
    public static void post(Runnable run) {
        post(0, run);
    }

    /**
     * 回调到主线程
     */
    public static void post(long delay, Runnable run) {
        mainHandler.postDelayed(run, delay);
    }


}
