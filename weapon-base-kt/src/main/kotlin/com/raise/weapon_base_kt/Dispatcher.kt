package com.raise.weapon_base_kt

import android.os.SystemClock
import com.raise.weapon_base.Dispatcher
import com.raise.weapon_base.UIThreadUtil

/**
 * 交给线程池执行异步任务
 * 场景：明确知道需要创建一个线程执行
 */
fun async(delayTime: Long = 0, block: () -> Unit) {

    Dispatcher.with().enqueue {
        if (delayTime > 0) {
            SystemClock.sleep(delayTime)
        }
        block()
    }
}

/**
 * block在非UI线程调用
 * 1.若当前线程为UI线程，会创建子线程运行
 * 2.若当前线程为非UI线程，会直接在当前线程执行
 */
fun runOnNotUIThread(block: () -> Unit) {
    if (UIThreadUtil.isMainThread()) {
        async(block = block)
    } else {
        block()
    }
}