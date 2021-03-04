package com.raise.weapon_base_kt

import com.raise.weapon_base.UIThreadUtil

/**
 * 在主线程中执行
 */
fun runOnUIThread(delayTime: Long = 0, block: () -> Unit) {
    UIThreadUtil.post(delayTime, block)
}