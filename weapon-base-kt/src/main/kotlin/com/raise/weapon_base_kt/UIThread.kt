package com.raise.weapon_base

/**
 * 在主线程中执行
 */
fun runOnUIThread(delayTime: Long = 0, block: () -> Unit) {
    UIThreadUtil.post(delayTime, block)
}