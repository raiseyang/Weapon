package com.raise.weapon_ui.floatwindow;

import android.view.View;

/**
 * 浮窗api
 */
public interface IFloatWindow {
    /**
     * 显示浮窗
     */
    void show();

    /**
     * 隐藏浮窗
     */
    void hide();

    /**
     * 获取浮窗的左上角的x坐标
     *
     * @return x坐标值  单位：px
     */
    int getX();

    /**
     * 获取浮窗的左上角的y坐标
     *
     * @return y坐标值  单位：px
     */
    int getY();

    /**
     * 更新浮窗显示位置
     *
     * @param x 左上角的x坐标 单位：px
     * @param y 左上角的y坐标 单位：px
     */
    void updatePosition(int x, int y);

    /**
     * 获得浮窗的跟布局
     */
    View getView();
}
