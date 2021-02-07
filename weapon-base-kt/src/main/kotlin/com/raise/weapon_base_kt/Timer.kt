package com.raise.weapon_base

/**
 *  wtimer(
 *      delayTime = 1000,
 *      minCompletedTime = 2000,
 *      period = 500,
 *      timeout = 10000,
 *      condition = {
 *          false
 *      },
 *      onNext = {
 *      },
 *      onCompleted = {
 *      },
 *      onTimeout = {
 *      }
 *  )
 */
fun wtimer(
    delayTime: Long = 0,
    minCompletedTime: Long = 0,
    period: Long = -1,
    timeout: Long = -1,
    condition: () -> Boolean,
    onNext: (Int) -> Unit,
    onCompleted: () -> Unit,
    onTimeout: () -> Unit
): Timer {
    val timer = Timer.newInstance()
    if (delayTime > 0)
        timer.setDelayTime(delayTime)
    if (minCompletedTime > 0)
        timer.setMinTime(minCompletedTime)
    if (period > 0)
        timer.setPeriod(period)
    if (timeout > 0)
        timer.setTimeout(timeout)
    timer.setCondition(condition)
    timer.start(object : Timer.IntervalListener {
        override fun onNext(times: Int) {
            onNext(times)
        }

        override fun onCompleted() {
            onCompleted()
        }

        override fun onTimeout() {
            onTimeout()
        }
    })
    return timer
}