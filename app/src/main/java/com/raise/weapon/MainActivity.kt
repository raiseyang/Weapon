package com.raise.weapon

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.raise.weapon_base.LLog
import com.raise.weapon_ui.ToastUtil
import com.raise.weapon_ui.floatwindow.FloatWindow
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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