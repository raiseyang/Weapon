package com.raise.weapon

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.raise.weapon_base.LLog
import com.raise.weapon_base.UIThreadUtil
import com.raise.weapon_ui.ToastUtil
import com.raise.weapon_ui.dialog.VcDialog
import com.raise.weapon_ui.floatwindow.FloatWindow
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_2.text = "弹出VcDialog"
        btn_2.setOnClickListener {
            val dialog = VcDialog.Builder(this)
                .setTitle("提示")
                .setMessage("内有恶犬")
                .setOnCancelListener {
                    ToastUtil.show("我消失了")
                }
                .create()

            val title =
                dialog.rootView.findViewById<TextView>(com.raise.weapon_ui.R.id.vcdialog_textview_title)
            title.text = "修改后的提示"

            dialog.show()
            UIThreadUtil.post(3000) {
                dialog.rawDialog.cancel()
            }
        }

        btn_1.text = "弹出FloatWindow"
        btn_1.setOnClickListener {
            LLog.d(TAG, "onCreate() btn1 click")

            val button = Button(this)
            button.text = "悬浮按钮1"
            button.setOnClickListener {
                ToastUtil.show("我是悬浮窗按钮")
            }

            FloatWindow.Builder(this)
                .setView(button)
                .create()
                .show()

        }
    }
}