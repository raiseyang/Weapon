package com.raise.weapon_base;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

/**
 * HandlerThread封装类
 * 用于各种callback机制的回调，防止被执行的闭包阻塞调用线程
 * 1. 创建自己的实例；
 * 2. 可配置是否在主线程执行闭包；
 * 3. 注意：回调完成，需要调用end()结束该HandlerThread
 * 使用方法:
 * <code>
 * mHandlerThread = ListenerHandlerThread.create("common_download", false);
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
        synchronized (this) {
            handler = new Handler(getLooper());
            notify();
        }
    }

    /**
     * 发送被执行的Runnable
     */
    public void post(final Runnable runnable) {
        if (isUIThread) {
            UIThreadUtil.post(runnable);
        }else {
            if (handler == null) {
                // 等待创建
                if (isAlive()) {
                    synchronized (this) {
                        try {
                            wait(5 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (handler != null){
                handler.post(runnable);
            }else {
                Log.w("ListenerHandlerThread", "post() handler == null,name=" + getName());
            }
        }
    }

    public void end() {
        quitSafely();
    }
}
