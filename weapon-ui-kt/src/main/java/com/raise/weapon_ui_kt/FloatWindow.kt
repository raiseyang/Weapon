package com.raise.weapon_ui_kt

import android.content.Context
import com.raise.weapon_ui.floatwindow.FloatWindow
import com.raise.weapon_ui.floatwindow.IBuilder
import com.raise.weapon_ui.floatwindow.IFloatWindow

/**
 * val floatWindow = createFloatWindow(this) {
 *     setView(button)
 * }
 * floatWindow.show()
 * UIThreadUtil.post(3000) {
 *     floatWindow.hide()
 * }
 */
fun createFloatWindow(context: Context, block: IBuilder.() -> Unit): IFloatWindow {
    val builder = FloatWindow.Builder(context)
    block(builder)
    return builder.create()
}