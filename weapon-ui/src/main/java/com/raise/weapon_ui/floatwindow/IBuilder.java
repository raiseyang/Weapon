package com.raise.weapon_ui.floatwindow;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public interface IBuilder {
    IBuilder setView(@NonNull View view);

    IBuilder setView(@LayoutRes int view);

    IBuilder setX(int x);

    IBuilder setY(int y);

    IBuilder setGravity(int gravity);

    IFloatWindow create();
}
