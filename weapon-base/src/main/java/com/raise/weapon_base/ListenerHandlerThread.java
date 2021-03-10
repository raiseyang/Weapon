package com.raise.weapon_base;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;

/**
 * HandlerThread封装类
 * 用于各种callback机制的回调，防止被执行的闭包阻塞调用线程
 * 1. 创建自己的实例；
 * 2. 可配置是否在主线程执行闭包；
 * 3. 注意：回调完成，需要调用end()结束该HandlerThread
 * 使用方法:
 * <code>
 * mHandlerThread = ListenerHandlerThread.create("download", false);
 * mHandlerThread.start();
 * mHandlerThread.setUIThread(true);
 * mHandlerThread.post(new Runnable() {})
 * mHandlerThread.end();
 * </cdoe>
 */
public final class ListenerHandlerThread extends HandlerThread {

    private Handler handler;
    private boolean isUIThread;

    private ListenerHandlerThread(String name, boolean isUIThread) {
        super(name);
        this.isUIThread = isUIThread;
    }

    /**
     * 创建一个ListenerHandlerThread实例
     *
     * @param threadName 实例名称
     */
    public static ListenerHandlerThread create(String threadName, boolean isUIThread) {
        return new ListenerHandlerThread(threadName, isUIThread);
    }

    /**
     * 是否在UI线程回调
     *
     * @param UIThread true if callback on ui thread.
     */
    public void setUIThread(boolean UIThread) {
        isUIThread = UIThread;
    }

    @Override
    protected void onLooperPrepared() {
        handler = new Handler(getLooper());
    }

    /**
     * 发送被执行的Runnable
     */
    public void post(final Runnable runnable) {
        postDelayed(runnable, 0);
    }

    /**
     * 发送被执行的Runnable
     */
    public void postDelayed(final Runnable runnable, final long timeInMills) {
        if (isUIThread) {
            UIThreadUtil.post(timeInMills, runnable);
        } else {
            while (handler == null) {
//                LLog.i("ListenerHandlerThread","postDelayed() handler == null");
                SystemClock.sleep(100);
            }
            handler.postDelayed(runnable, timeInMills);
        }
    }

    public void end() {
        quitSafely();
    }
}
