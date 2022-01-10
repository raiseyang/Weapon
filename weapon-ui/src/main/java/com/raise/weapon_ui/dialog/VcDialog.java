package com.raise.weapon_ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.raise.weapon_ui.R;

/**
 * http://www.jianshu.com/p/526fcf3e8db3
 * <p>
 * https://github.com/tianzhijiexian/EasyDialog/blob/HEAD/lib/src/main/java/kale/ui/view/dialog/BaseEasyDialog.java
 * 注意：
 * 1. 必须依附AppCompatActivity使用；service或其他场景，请使用系统dialog.getwindow.settype()解决
 *
 * <p>
 * val dialog = VcDialog.Builder(this)
 * .setTitle("提示")
 * .setMessage("内有恶犬")
 * .setOnCancelListener {
 * ToastUtil.show("我消失了")
 * }
 * .create()
 * val title =
 * dialog.rootView.findViewById<TextView>(com.raise.weapon_ui.R.id.vcdialog_textview_title)
 * title.text = "修改后的提示"
 * dialog.show()
 * Created by raise.yang on 17/10/10.
 */
public class VcDialog extends AppCompatDialogFragment implements IVCDialog {

    // DialogFragment中dialog本体
    private Dialog mDialog;
    // 调用者,fragment依附的Activity，建议是AppCompatActivity
    private FragmentActivity fragmentActivity;
    // 根据建造者模式，产品不应该持有建造者
//    private Builder mBuilder;
    private DialogInterface.OnCancelListener onCancelListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mBuilder = (Builder) getArguments().getParcelable(KEY_ARG_BUILD);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // VcDialog实例一旦创建，mDialog不可能为null
        if (mDialog == null) {
            throw new NullPointerException("Exception,VcDialog mDialog == null");
//            mDialog = mBuilder.create().getRawDialog();
        }
        return mDialog;
    }

    /**
     * 替换默认的原生dialog
     *
     * 自定义Fragment持有的Dialog对象
     * 注意：使用该接口，请先了解其功能
     * @param newDialog
     */
    public void replaceRawDialog(Dialog newDialog) {
        mDialog = newDialog;
    }

    public boolean isShowing() {
        return mDialog != null && mDialog.isShowing();
    }

    @Override
    public IVCDialog show() {
//        FragmentActivity fa = (FragmentActivity) mBuilder.context;
        FragmentActivity fa = fragmentActivity;
        FragmentTransaction ft = fa.getSupportFragmentManager().beginTransaction();
        ft.add(this, "VcDialog" + hashCode());
        ft.commitAllowingStateLoss();
        return this;
    }

    @Override
    public Dialog getRawDialog() {
        return mDialog;
    }

    @Override
    public ViewGroup getRootView() {
//        return mBuilder.rootView;
        return mDialog.getWindow().getDecorView().findViewById(R.id.vcdialog_layout_root);
    }

    @Override
    public void dismiss() {
        dismissAllowingStateLoss();
    }

    @Override
    public IVCDialog cancelableByBackKey(boolean cancelable) {
        setCancelable(cancelable);
        return this;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (onCancelListener != null) {
            onCancelListener.onCancel(dialog);
        }
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    /**
     * VcDialogBuilder
     * 构建一个VcDialog
     */
    public static class Builder implements IBuilder {

        FragmentActivity context;
        Dialog dialog;

        String title;
        String message;
        String[] list_view_items = new String[]{};

        int width = 0;
        int height = 0;

        String leftButtonText;
        boolean leftButtonCancel;
        DialogInterface.OnClickListener leftListener; // positive

        String rightButtonText;
        boolean rightButtonCancel;
        DialogInterface.OnClickListener rightListener; // negative

        String centerButtonText;
        boolean centerButtonCancel;
        DialogInterface.OnClickListener centerListener; // NEUTRAL

        boolean cancelable = false;//dialog是否可以取消
        DialogInterface.OnCancelListener onCancelListener;

        int layoutResId = -1;//message区域部分
        View customView;//message区域部分
        //        ViewGroup rootView;//根布局区域部分
        AdapterView.OnItemClickListener onItemClickListener;

        public Builder(FragmentActivity context) {
            this.context = context;
        }

        @Override
        public IBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        @Override
        public IBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        @Override
        public IBuilder setItems(String[] items, AdapterView.OnItemClickListener listener) {
            this.list_view_items = items;
            this.onItemClickListener = listener;
            return this;
        }

        public IBuilder setWidth(int width) {
            this.width = width;
            return this;
        }

        public IBuilder setHeight(int height) {
            this.height = height;
            return this;
        }

        @Override
        public IBuilder setLeftButton(String text, DialogInterface.OnClickListener listener) {
            return setLeftButton(text, listener, true);
        }

        @Override
        public IBuilder setLeftButton(String text, DialogInterface.OnClickListener listener, boolean cancel) {
            this.leftButtonText = text;
            this.leftListener = listener;
            this.leftButtonCancel = cancel;
            return this;
        }

        @Override
        public IBuilder setCenterButton(String text, DialogInterface.OnClickListener listener) {
            return setCenterButton(text, listener, true);
        }

        @Override
        public IBuilder setCenterButton(String text, DialogInterface.OnClickListener listener, boolean cancel) {
            this.centerButtonText = text;
            this.centerListener = listener;
            this.centerButtonCancel = cancel;
            return this;
        }

        @Override
        public IBuilder setRightButton(String text, DialogInterface.OnClickListener listener) {
            return setRightButton(text, listener, true);
        }

        @Override
        public IBuilder setRightButton(String text, DialogInterface.OnClickListener listener, boolean cancel) {
            this.rightButtonText = text;
            this.rightListener = listener;
            this.rightButtonCancel = cancel;
            return this;
        }

        @Override
        public IBuilder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        @Override
        public IBuilder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.onCancelListener = onCancelListener;
            return this;
        }

        @Override
        public IBuilder setView(View view) {
            this.customView = view;
            return this;
        }

        @Override
        public IBuilder setLayoutId(int resId) {
            layoutResId = resId;
            return this;
        }

        @Override
        public IVCDialog create() {
            dialog = new Dialog(context);
            //去掉头部
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (layoutResId != -1) {
                dialog.setContentView(layoutResId);
            } else {
                dialog.setContentView(R.layout.vcdialog_layout);
            }
            setupView(dialog.getWindow());

            dialog.setCanceledOnTouchOutside(cancelable);
            
            VcDialog dialog = new VcDialog();
//            Bundle bundle = new Bundle();
//            bundle.putParcelable(KEY_ARG_BUILD, this);
//            dialog.setArguments(bundle);
            dialog.mDialog = this.dialog;
            dialog.fragmentActivity = context;
//            dialog.mBuilder = this;
            dialog.setOnCancelListener(onCancelListener);
            return dialog;
        }

        private void setupView(Window window) {
            //背景透明
            window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
            ViewGroup root = (ViewGroup) window.findViewById(R.id.vcdialog_layout_root);
//            rootView = root;
            //设置宽高
            ViewGroup.LayoutParams layoutParams = root.getLayoutParams();
            if (customView == null) {
                layoutParams.width = this.width != 0 ? width : context.getResources().getDisplayMetrics().widthPixels * 5 / 7;
                layoutParams.height = this.height != 0 ? height : context.getResources().getDisplayMetrics().heightPixels * 1 / 5;
            } else {
                // 请在外部设置view的宽高：customView.layoutParams = ViewGroup.LayoutParams(600, 450)
                layoutParams.width = customView.getLayoutParams().width;
                layoutParams.height = customView.getLayoutParams().height;
            }

//            root.setBackgroundResource(R.drawable.vcdialog_bg_gray);
            //title
            TextView title_tv = (TextView) window.findViewById(R.id.vcdialog_textview_title);
            if (TextUtils.isEmpty(title)) {
                title_tv.setVisibility(View.GONE);
            } else {
                title_tv.setText(title);
                title_tv.setVisibility(View.VISIBLE);
            }
            //message
            ViewGroup msg_vg = (ViewGroup) window.findViewById(R.id.vcdialog_layout_message);
            TextView message_tv = (TextView) msg_vg.findViewById(R.id.vcdialog_textview_message);
            if (customView != null) {
                //自定义view
                msg_vg.removeAllViews();
                msg_vg.addView(customView);
            } else {
                if (TextUtils.isEmpty(message)) {
                    message_tv.setVisibility(View.GONE);
                } else {
                    message_tv.setVisibility(View.VISIBLE);
                    message_tv.setText(message);
                }
            }

            if (list_view_items.length > 0) {
                ListView listView = (ListView) msg_vg.findViewById(R.id.vcdialog_listview_message);
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_single_choice, list_view_items));
                listView.setOnItemClickListener(onItemClickListener);
            }

            //buttons
            ViewGroup buttons_vg = (ViewGroup) window.findViewById(R.id.vcdialog_layout_buttons);
            if (TextUtils.isEmpty(leftButtonText)
                    && TextUtils.isEmpty(rightButtonText)
                    && TextUtils.isEmpty(centerButtonText)) {
                //没有按钮
                buttons_vg.setVisibility(View.GONE);
            } else {
                buttons_vg.setVisibility(View.VISIBLE);
                Button leftButton = (Button) window.findViewById(R.id.vcdialog_button_left);
                if (TextUtils.isEmpty(leftButtonText)) {
                    leftButton.setVisibility(View.GONE);
                } else {
                    leftButton.setText(leftButtonText);
                    leftButton.setVisibility(View.VISIBLE);
                    leftButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (leftListener != null)
                                leftListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                            if (leftButtonCancel) {
                                dialog.cancel();
                            }
                        }
                    });
                }
                final Button rightButton = (Button) window.findViewById(R.id.vcdialog_button_right);
                if (TextUtils.isEmpty(rightButtonText)) {
                    rightButton.setVisibility(View.GONE);
                } else {
                    rightButton.setText(rightButtonText);
                    rightButton.setVisibility(View.VISIBLE);
                    rightButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (rightListener != null)
                                rightListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                            if (rightButtonCancel) {
                                dialog.cancel();
                            }
                        }
                    });
                }
                final Button centerButton = (Button) window.findViewById(R.id.vcdialog_button_center);
                if (TextUtils.isEmpty(centerButtonText)) {
                    centerButton.setVisibility(View.GONE);
                } else {
                    centerButton.setText(centerButtonText);
                    centerButton.setVisibility(View.VISIBLE);
                    centerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (centerListener != null)
                                centerListener.onClick(dialog, DialogInterface.BUTTON_NEUTRAL);
                            if (centerButtonCancel) {
                                dialog.cancel();
                            }
                        }
                    });
                }
            }
        }
    }
}
