package com.raise.weapon_base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.UserManager;

import androidx.annotation.NonNull;

/**
 * Created by raise.yang on 19/07/17.
 */
public class SPFUtil {

    private static final String s_sp_file_name; // sp文件名

    private static boolean isUserUnlocked = false;

    static {
        s_sp_file_name = AppInfoUtil.getPackageName();
    }

    /**
     * 兼容刚开机时，用户锁问题
     */
    private static SharedPreferences getSharedPreferences() {
        if (ContextVal.getContext() == null) {
            throw new NullPointerException("Should invoke ContextVal.setContext() on Application.OnCreate() in multi-process");
        }
        if (Build.VERSION.SDK_INT < 24 || isUserUnlocked) {
            return ContextVal.getContext().getSharedPreferences(
                    s_sp_file_name, Context.MODE_PRIVATE);
        } else {
            UserManager userManager = (UserManager) ContextVal.getContext().getSystemService(Context.USER_SERVICE);
            if (userManager.isUserUnlocked()) {
                isUserUnlocked = true;
            }
            ContextVal.getContext().createDeviceProtectedStorageContext();
            return ContextVal.getContext().createDeviceProtectedStorageContext().getSharedPreferences(
                    s_sp_file_name, Context.MODE_PRIVATE
            );
        }
    }

    public static void putInt(@NonNull String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(@NonNull String key) {
        return getInt(key, -1);
    }

    public static int getInt(@NonNull String key, int defaultValue) {
        return getSharedPreferences().getInt(key, defaultValue);
    }

    public static void putBoolean(@NonNull String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(@NonNull String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(@NonNull String key, boolean defaultValue) {
        return getSharedPreferences().getBoolean(key, defaultValue);
    }

    public static void putLong(@NonNull String key, long value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getLong(@NonNull String key) {
        return getLong(key, -1);
    }

    public static long getLong(@NonNull String key, long defaultValue) {
        SharedPreferences sharedPreferences = ContextVal.getContext().getSharedPreferences(
                s_sp_file_name, Context.MODE_PRIVATE);
        return getSharedPreferences().getLong(key, defaultValue);
    }


    public static void putFloat(@NonNull String key, float value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float getFloat(@NonNull String key) {
        return getFloat(key, -1f);
    }

    public static float getFloat(@NonNull String key, float defaultValue) {
        SharedPreferences sharedPreferences = ContextVal.getContext().getSharedPreferences(
                s_sp_file_name, Context.MODE_PRIVATE);
        return getSharedPreferences().getFloat(key, defaultValue);
    }

    public static void putString(@NonNull String key, @NonNull String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(@NonNull String key) {
        return getString(key, "");
    }

    @NonNull
    public static String getString(@NonNull String key, @NonNull String defValue) {
        return getSharedPreferences().getString(key, defValue);
    }
}
