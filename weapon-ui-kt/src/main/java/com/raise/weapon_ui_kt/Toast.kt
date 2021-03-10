package com.raise.weapon_ui_kt

import androidx.annotation.StringRes
import com.raise.weapon_ui.ToastUtil

fun toast(msg: String) {
    ToastUtil.show(msg)
}

fun toast(@StringRes resId: Int) {
    ToastUtil.show(resId)
}

/**
 * toast {
 *     val name = "raise"
 *     "${name}消失了"
 * }
 */
fun toast(block: () -> String) {
    ToastUtil.show(block())
}