package com.raise.weapon_ui.floatwindow;


import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

import com.raise.weapon_base.LLog;

/**
 * 支持管理一个悬浮框
 * 参考：https://gitee.com/1960176680/FloatWindow
 * <p>
 * 8.0及以上需申请权限
 * how to use:
 * <code>
 * FloatWindow.Builder(this)
 * .setView(button)
 * .create()
 * .show()
 * </code>
 * Created by raise.yang on 20/05/15.
 */
public class FloatWindow implements IFloatWindow {
    private static final String TAG = "AbFloatWindow";
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private FloatWindow.Builder mBuilder;
    private int mX;
    private int mY;

    private boolean isShowing = false;

    private FloatWindow(FloatWindow.Builder mBuilder) {
        this.mBuilder = mBuilder;
        windowManager = (WindowManager) mBuilder.context.getSystemService(Context.WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        prepareLayoutParams(layoutParams);
    }

    @RequiresPermission("android.permission.SYSTEM_ALERT_WINDOW")
    private void prepareLayoutParams(WindowManager.LayoutParams layoutParams) {
        layoutParams.packageName = mBuilder.context.getPackageName();
        layoutParams.flags = (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

//        if (mBuilder.context instanceof AccessibilityService) {
//            LLog.i(TAG,"prepareLayoutParams() TYPE_ACCESSIBILITY_OVERLAY");
//            layoutParams.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
//        } else
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LLog.i(TAG, "prepareLayoutParams() TYPE_APPLICATION_OVERLAY");
            //8.0及以上需申请权限
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LLog.i(TAG, "prepareLayoutParams() TYPE_PHONE");
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        //设置控件在坐标计算规则，相当于屏幕左上角
        layoutParams.gravity = mBuilder.gravity;
//        layoutParams.gravity = Gravity.CENTER;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        // 显示界面的左上角坐标位置
        layoutParams.x = mX = mBuilder.xOffset;
        layoutParams.y = mY = mBuilder.yOffset;
    }

    @Override
    public void show() {
        if (isShowing) {
        } else {
            isShowing = true;
            windowManager.addView(mBuilder.view, layoutParams);
        }
    }

    @Override
    public void hide() {
        try {
            windowManager.removeView(mBuilder.view);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isShowing = false;
        }
    }

    @Override
    public int getX() {
        return mX;
    }

    @Override
    public int getY() {
        return mY;
    }

    @Override
    public void updatePosition(int x, int y) {
        layoutParams.x = mX = x;
        layoutParams.y = mY = y;
        windowManager.updateViewLayout(mBuilder.view, layoutParams);
    }

    @Override
    public View getView() {
        return mBuilder.view;
    }

    public static class Builder implements IBuilder {

        private final Context context;

        private View view;
        private int gravity = Gravity.START | Gravity.TOP;
        private int xOffset;
        private int yOffset;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        @Override
        public IBuilder setView(@NonNull View view) {
            this.view = view;
            return this;
        }

        @Override
        public IBuilder setView(int layoutId) {
            this.view = LayoutInflater.from(context).inflate(layoutId, null);
            return this;
        }

        @Override
        public IBuilder setX(int x) {
            this.xOffset = x;
            return this;
        }

        @Override
        public IBuilder setY(int y) {
            this.yOffset = y;
            return this;
        }

        @Override
        public IBuilder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        @Override
        public IFloatWindow create() {
            return new FloatWindow(this);
        }
    }
}


