package com.raise.weapon_ui.floatwindow;


import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

/**
 * 支持管理一个悬浮框
 * 参考：https://gitee.com/1960176680/FloatWindow
 * 参考：https://blog.csdn.net/big_sea_m/article/details/104611800
 * <p>
 * 8.0及以上需申请权限
 * 1. 传入的context为AccessibilityService时，可不用申请权限
 * 申请权限：
 * <code>
 * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
 * <code/>
 * 判断是否有弹窗权限：
 * <code>
 * Settings.canDrawOverlays(context);
 * <code/>
 * 跳转到申请弹窗权限settings界面
 * <code>
 * val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
 * val pkg = "package:${packageName}"
 * intent.data = Uri.parse(pkg)
 * startActivityForResult(intent,11)
 * <code/>
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

        if (mBuilder.context instanceof AccessibilityService) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0及以上需申请权限
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
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


