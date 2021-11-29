package com.raise.weapon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
            val showVcDialog = showVcDialog(this, false) {
                setMessage("登录中")
                setCancelable(false)
            }
                .cancelableByBackKey(false)
                .show()
        }

        btn_1.text = "btn_1"
        btn_1.setOnClickListener {
            val showVcDialog = showVcDialog(this, false) {
                setMessage("登录中")
                setCancelable(true)
            }

            showVcDialog.show()
            LLog.d("MainActivity","hello")
        }
    }
}