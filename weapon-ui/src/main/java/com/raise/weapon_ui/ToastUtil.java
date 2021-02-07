package com.raise.weapon_ui;


import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.raise.weapon_base.ContextVal;
import com.raise.weapon_base.UIThreadUtil;

/**
 * 短时间内多次show()会存在覆盖逻辑
 * How to use:
 * <code>
 * ToastUtil.show();
 * </code>
 */
public class ToastUtil {
    private static Toast mToast;

    private ToastUtil() {
        throw new UnsupportedOperationException("do not create this class.");
    }

    public static void show(@NonNull final String content) {
        if (mToast != null) {
            mToast.cancel();
        }

        if (UIThreadUtil.isMainThread()) {
            mToast = Toast.makeText(ContextVal.getContext(), content, Toast.LENGTH_SHORT);
            mToast.show();
        } else {
            UIThreadUtil.post(new Runnable() {
                public void run() {
                    mToast = Toast.makeText(ContextVal.getContext(), content, Toast.LENGTH_SHORT);
                    mToast.show();
                }
            });
        }

    }

    public static void show(@StringRes int resId) {
        show(ContextVal.getContext().getString(resId));
    }

    public static void show(@NonNull final View view) {
        if (UIThreadUtil.isMainThread()) {
            mToast = new Toast(ContextVal.getContext());
            mToast.setView(view);
            mToast.show();
        } else {
            UIThreadUtil.post(new Runnable() {
                public void run() {
                    mToast = new Toast(ContextVal.getContext());
                    mToast.setView(view);
                    mToast.show();
                }
            });
        }

    }
}
