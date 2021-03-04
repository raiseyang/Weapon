package com.raise.weapon_ui.dialog;

import android.app.Dialog;
import android.view.ViewGroup;

/**
 * VCDialog接口
 * Created by raise.yang on 17/10/12.
 */

public interface IVCDialog {

    boolean isShowing();

    void dismiss();

    IVCDialog show();

    /**
     * 获取原来的dialog实例，方便调用未公开的接口，比如cancel()
     */
    Dialog getRawDialog();

    /**
     * 获取dialog的跟布局，方便通过该布局找到所有子view并修改其属性，比如倒计时功能；
     */
    ViewGroup getRootView();
}
