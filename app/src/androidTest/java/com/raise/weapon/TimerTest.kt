package com.raise.weapon

import android.os.SystemClock
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.raise.weapon_base.LLog
import com.raise.weapon_base.Timer
import com.raise.weapon_base.wtimer
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TimerTest {
    companion object {
        private const val TAG = "TimerTest"
    }

    @Test
    fun test2() {
        LLog.d(TAG, "test2() start.")
        wtimer(
            delayTime = 1000,
            minCompletedTime = 2000,
            period = 500,
            timeout = 10000,
            condition = {
                false
            },
            onNext = {
                LLog.d(TAG, "onNext() times=$it")
            },
            onCompleted = {
                LLog.d(TAG, "onComplete() ")
            },
            onTimeout = {
                LLog.d(TAG, "onTimeOut() ")
            }
        )
        LLog.d(TAG, "test2() end")
        SystemClock.sleep(1000000)
    }

    @Test
    fun test1() {
        LLog.d(TAG, "test1() start.")
        Timer.newInstance()
            .setDelayTime(1000)
            .setPeriod(2000)
            .setTimeout(10000)
            .setCondition {
                return@setCondition false
            }
            .start(object : Timer.IntervalListener {
                override fun onNext(times: Int) {
                    LLog.d(TAG, "onNext() times=$times")
                }

                override fun onCompleted() {
                    LLog.d(TAG, "onComplete() ")
                }

                override fun onTimeout() {
                    LLog.d(TAG, "onTimeOut() ")
                }
            })
        LLog.d(TAG, "test1() end")
        SystemClock.sleep(1000000)
    }

}