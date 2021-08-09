package com.raise.weapon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raise.weapon_base.LLog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LLog.setWriteEncrypt(true)

        btn_2.text = "弹出VcDialog"
        btn_2.setOnClickListener {

        }

        btn_1.text = "弹出FloatWindow"
        btn_1.setOnClickListener {
            LLog.d(TAG, "onCreate()111 ")
        }
    }
}