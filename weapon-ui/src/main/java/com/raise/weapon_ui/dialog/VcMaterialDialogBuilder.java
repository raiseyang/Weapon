package com.raise.weapon_ui.dialog;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * 该Dialog仅作为 VcDialog的一个补充
 * 风格为Material
 */
public class VcMaterialDialogBuilder implements IBuilder {

    private FragmentActivity context;

    private String title;
    private String message;
    private String leftBtnText;
    private DialogInterface.OnClickListener leftBtnOnClickListener;

    private String centerBtnText;
    private DialogInterface.OnClickListener centerBtnOnClickListener;

    private String rightBtnText;
    private DialogInterface.OnClickListener rightBtnOnClickListener;

    private boolean cancelable;
    private DialogInterface.OnCancelListener cancelListener;

    public VcMaterialDialogBuilder(FragmentActivity context) {
        this.context = context;
    }

    @Override
    public IBuilder setTitle(@NonNull String title) {
        this.title = title;
        return this;
    }

    @Override
    public IBuilder setMessage(@NonNull String message) {
        this.message = message;
        return this;
    }

    @Override
    public IBuilder setItems(@NonNull String[] items, AdapterView.OnItemClickListener listener) {
        throw new IllegalStateException("VcMaterialDialog not support setItems.");
    }

    @Override
    public IBuilder setWidth(int width) {
        throw new IllegalStateException("VcMaterialDialog not support setWidth.");
    }

    @Override
    public IBuilder setHeight(int height) {
        throw new IllegalStateException("VcMaterialDialog not support setHeight.");
    }

    @Override
    public IBuilder setLeftButton(@NonNull String text, DialogInterface.OnClickListener listener) {
        leftBtnText = text;
        leftBtnOnClickListener = listener;
        return this;
    }

    @Override
    public IBuilder setLeftButton(@NonNull String text, DialogInterface.OnClickListener listener, boolean cancel) {
        throw new IllegalStateException("VcMaterialDialog not support setLeftButton cancel==false.");
    }

    @Override
    public IBuilder setCenterButton(@NonNull String text, DialogInterface.OnClickListener listener) {
        centerBtnText = text;
        centerBtnOnClickListener = listener;
        return this;
    }

    @Override
    public IBuilder setCenterButton(@NonNull String text, DialogInterface.OnClickListener listener, boolean cancel) {
        throw new IllegalStateException("VcMaterialDialog not support setCenterButton cancel==false.");
    }

    @Override
    public IBuilder setRightButton(@NonNull String text, DialogInterface.OnClickListener listener) {
        rightBtnText = text;
        rightBtnOnClickListener = listener;
        return this;
    }

    @Override
    public IBuilder setRightButton(@NonNull String text, DialogInterface.OnClickListener listener, boolean cancel) {
        throw new IllegalStateException("VcMaterialDialog not support setRightButton cancel==false.");
    }

    @Override
    public IBuilder setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    @Override
    public IBuilder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        cancelListener = onCancelListener;
        return this;
    }

    @Override
    public IBuilder setView(@NonNull View view) {
        throw new IllegalStateException("VcMaterialDialog not support setView.");
    }

    @Override
    public IBuilder setLayoutId(int resId) {
        throw new IllegalStateException("VcMaterialDialog not support setLayoutId.");
    }

    @Override
    public IVCDialog create() {
        VcDialog.Builder builder = new VcDialog.Builder(context);

        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context);

        materialAlertDialogBuilder.setTitle(title);
        materialAlertDialogBuilder.setMessage(message);
        if (!TextUtils.isEmpty(leftBtnText)) {
            materialAlertDialogBuilder.setNegativeButton(leftBtnText, leftBtnOnClickListener);
        }
        if (!TextUtils.isEmpty(centerBtnText)) {
            materialAlertDialogBuilder.setNegativeButton(centerBtnText, centerBtnOnClickListener);
        }
        if (!TextUtils.isEmpty(rightBtnText)) {
            materialAlertDialogBuilder.setPositiveButton(rightBtnText, rightBtnOnClickListener);
        }

        materialAlertDialogBuilder.setCancelable(cancelable);
        materialAlertDialogBuilder.setOnCancelListener(cancelListener);
        // 创建VcDialog
        VcDialog vcDialog = (VcDialog) builder.create();
        // 替换VcDialog中的Dialog实例
        vcDialog.replaceRawDialog(materialAlertDialogBuilder.create());
        return vcDialog;
    }

}
