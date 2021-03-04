package com.raise.weapon_ui.dialog;

import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * Builder设计模式，build接口类，用于抽象需要构建的零部件；
 * 相当于构建房子所需要的厨房，客厅，卫生间，厂库；
 * <p>
 * http://www.cnblogs.com/yejg1212/archive/2013/02/25/2932526.html
 * Created by raise.yang on 17/10/11.
 */

public interface IBuilder {

    IBuilder setTitle(@NonNull String title);

    IBuilder setMessage(@NonNull String message);

    IBuilder setItems(@NonNull String[] items, AdapterView.OnItemClickListener listener);

    IBuilder setWidth(int width);

    IBuilder setHeight(int height);

    IBuilder setLeftButton(@NonNull String text, DialogInterface.OnClickListener listener);

    IBuilder setLeftButton(@NonNull String text, DialogInterface.OnClickListener listener, boolean cancel);

    IBuilder setCenterButton(@NonNull String text, DialogInterface.OnClickListener listener);

    IBuilder setCenterButton(@NonNull String text, DialogInterface.OnClickListener listener, boolean cancel);

    IBuilder setRightButton(@NonNull String text, DialogInterface.OnClickListener listener);

    IBuilder setRightButton(@NonNull String text, DialogInterface.OnClickListener listener, boolean cancel);

    /**
     * 点击弹窗外，弹窗是否取消
     *
     * @param cancelable true 弹窗取消
     */
    IBuilder setCancelable(boolean cancelable);

    IBuilder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener);

    IBuilder setView(@NonNull View view);

    /**
     * layout文件布局内部元素的id，应该和库中保持一致；
     */
    IBuilder setLayoutId(@LayoutRes int resId);

    IVCDialog create();
}
