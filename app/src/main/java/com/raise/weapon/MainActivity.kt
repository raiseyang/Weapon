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

        btn_1.setOnClickListener {
            LLog.d(TAG, "onCreate() btn1 click")
        }
    }
}