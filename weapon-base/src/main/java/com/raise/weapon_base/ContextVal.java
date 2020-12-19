package com.raise.weapon_base;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

/**
 * context持有者
 * 使用：{@link ContextVal#getContext()}
 */
public class ContextVal {

    public static final String ACTION_APP_BOOT_COMPLETED = "com.abupdate.common.action.app.boot.completed";

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    public static void setContext(Context context) {
        sContext = context;
    }

    public static @NonNull
    Context getContext() {
        return sContext;
    }
}
