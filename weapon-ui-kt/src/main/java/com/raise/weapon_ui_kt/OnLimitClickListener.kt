package com.raise.weapon_ui_kt

import android.view.View
import com.raise.weapon_base.LLog

/**
 *
 */
class OnLimitClickListener(private val limitTime: Long = 0, private val onClick: View?.() -> Unit) :
    View.OnClickListener {

    var preClickTime = 0L

    override fun onClick(view: View?) {
        val curTime = System.currentTimeMillis()
        if (curTime - preClickTime > limitTime) {
            preClickTime = curTime
            onClick.invoke(view)
        } else {
            LLog.d("OnLimitClickListener", "onClick() ")
        }
    }
}