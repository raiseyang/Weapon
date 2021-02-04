package com.raise.weapon_base;

/**
 * 建议在程序入口：
 * CrashHandler.getInstance().setCallback { ex, stackTrace ->
 * // 保存stackTrace
 * }
 * Created by raise.yang on 19/08/22.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler INSTANCE = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private onCrashCallback mCallback;

    private CrashHandler() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 接管未捕获异常
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //exit app
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            collectDeviceInfo(ex, stringBuilder);
            if (mCallback != null) {
                mCallback.dealCrash(ex, stringBuilder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void collectDeviceInfo(Throwable ex, StringBuilder stringBuilder) {
        StackTraceElement[] stack = ex.getStackTrace();
        for (StackTraceElement obj : stack) {
            stringBuilder.append("\tat ");
            stringBuilder.append(obj.toString());
            stringBuilder.append("\n");
        }
        Throwable cause = ex.getCause();
        if (cause != null) {
            collectDeviceInfo(cause, stringBuilder);
        }
    }

    /**
     * 当crash发生时，将会调用该Callback;<br/>
     * 注意：不要做耗时操作
     */
    public void setCallback(onCrashCallback callback) {
        mCallback = callback;
    }

    public interface onCrashCallback {
        void dealCrash(Throwable ex, String stackTrace);
    }
}
