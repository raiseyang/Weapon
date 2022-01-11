package com.raise.weapon

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raise.weapon.aboutme.AboutMeActivity
import com.raise.weapon_base.LLog
import com.raise.weapon_ui_kt.showVcDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_2.text = "btn_2"
        btn_2.setOnClickListener {

            val showVcDialog = showVcDialog {
                setMessage("登录中")
                setCancelable(true)
            }
                .cancelableByBackKey(true)
            LLog.d(TAG, "onCreate() vcDialog=$showVcDialog")
        }

        btn_1.text = "btn_1"
        btn_1.setOnClickListener {
            startActivity(Intent(this, AboutMeActivity::class.java))
        }
    }
}