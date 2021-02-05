package com.raise.weapon_base;


import androidx.annotation.NonNull;

/**
 * 启动一个条件检查的Timer
 * 可设置间隔时间,超时时间
 * Timer.newInstance()
 * .setPeriod(2000)
 * .setTimeout(10000)
 * .setCondition {
 * return@setCondition false
 * }
 * .start(object : Timer.IntervalListener {
 * override fun onNext(times: Int) {
 * }
 * <p>
 * override fun onCompleted() {
 * }
 * <p>
 * override fun onTimeout() {
 * }
 * })
 */
public class Timer {
    private final int DEFAULT_PERIOD = 1000;
    private final int DEFAULT_TIMEOUT = 5000;
    private final ListenerHandlerThread mHandlerThread = ListenerHandlerThread.create("Timer", false);
    // 用于绑定对象
    public Object tag;
    private Timer.ICondition mCondition;
    private int tickTimes = 0;
    private Timer.IntervalListener intervalListener;
    private long mPeriod = DEFAULT_PERIOD;
    private long mTimeout = DEFAULT_TIMEOUT;
    // 首次判断条件的延迟时间
    private long delayTime = 0L;
    // 最短完成时间(从调用start()方法开始计算)
    private long minTime = 0L;
    private final Runnable runnable = () -> {
        long runningTime = tickTimes * mPeriod + delayTime;
        if (runningTime > mTimeout) {
            mHandlerThread.end();
            if (intervalListener != null) {
                intervalListener.onTimeout();
            }
        } else if (runningTime > minTime && mCondition.isEnough()) {
            mHandlerThread.end();
            if (intervalListener != null) {
                intervalListener.onCompleted();
            }
        } else {
            if (intervalListener != null) {
                intervalListener.onNext(tickTimes);
            }
            tickTimes++;
            startDelayed(mPeriod);
        }
    };

    private Timer() {
        mHandlerThread.start();
    }

    /**
     * 创建一个Timer实例
     * 如需调用cancel()等接口，需要使用者持有该实例
     */
    public static Timer newInstance() {
        return new Timer();
    }

    /**
     * 取消timer
     */
    public void cancel() {
        mHandlerThread.end();
    }

    /**
     * 设置最小条件完成时间
     */
    public Timer setMinTime(long minTime) {
        this.minTime = minTime;
        return this;
    }

    public Timer setPeriod(long period) {
        mPeriod = period;
        return this;
    }

    /**
     * 设置超时时间
     */
    public Timer setTimeout(long timeInMills) {
        mTimeout = timeInMills;
        return this;
    }

    /**
     * 设置退出条件
     */
    public Timer setCondition(Timer.ICondition condition) {
        mCondition = condition;
        return this;
    }

    /**
     * 设置首次判断退出条件的延迟时间
     */
    public Timer setDelayTime(long timeInMills) {
        this.delayTime = timeInMills;
        return this;
    }

    /**
     * 启动Timer，在子线程回调结果
     *
     * @param intervalListener timer运行时回调
     */
    public void start(@NonNull Timer.IntervalListener intervalListener) {
        this.intervalListener = intervalListener;
        startDelayed(delayTime);
    }

    private void startDelayed(long delay) {
        mHandlerThread.postDelayed(runnable, delay);
    }

    public interface ICondition {
        /**
         * 条件是否满足
         *
         * @return true :跳出Timer
         */
        boolean isEnough();
    }

    public interface IntervalListener {
        /**
         * 每次条件判断后，调用该方法
         *
         * @param times 次数：0,1,2...
         */
        void onNext(int times);

        /**
         * 条件满足且运行时间大于minTime
         */
        void onCompleted();

        /**
         * 运行时间大于超时时间
         */
        void onTimeout();
    }
}

