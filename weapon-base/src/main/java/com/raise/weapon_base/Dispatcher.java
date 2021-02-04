package com.raise.weapon_base;


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 子线程分发器
 */
public class Dispatcher {

    /*********************** copy form AsyncTask start ***********************/
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;
    /**
     * An {@link ExecutorService} that can be used to execute tasks in parallel.
     */
    private static final ExecutorService THREAD_POOL_EXECUTOR = newThreadPool("Dispatcher");
    private final Deque<Runnable> readyAsyncCalls = new ArrayDeque<>();

    /*********************** copy form AsyncTask end ***********************/
    private final Deque<Runnable> runningAsyncCalls = new ArrayDeque<>();
    private final Deque<Callable> runningSyncCalls = new ArrayDeque<>();
    private int maxRequests = 64;
    private Runnable idleCallback;
    private ExecutorService executorService; // 可以自定义，默认使用THREAD_POOL_EXECUTOR

    private Dispatcher() {
    }

    /**
     * 生产一个线程池
     *
     * @param threadNamePre 线程池name的前缀
     */
    public static ThreadPoolExecutor newThreadPool(String threadNamePre) {
        final BlockingQueue<Runnable> poolWorkQueue = new LinkedBlockingQueue<Runnable>();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                poolWorkQueue, NameThreadFactory.create(threadNamePre));
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }

    public static Dispatcher with() {
        return DispatcherHolder.sInstance;
    }

    /**
     * 设置后，将会在此线程池中调度线程
     *
     * @param executorService 线程池{@link ExecutorService}
     */
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    private synchronized ExecutorService executorService() {
        return executorService == null ? THREAD_POOL_EXECUTOR : executorService;
    }

    public synchronized int getMaxRequests() {
        return maxRequests;
    }

    /**
     * 设置并行运行的线程数量
     *
     * @param maxRequests >1
     */
    public synchronized void setMaxRequests(int maxRequests) {
        if (maxRequests < 1) {
            throw new IllegalArgumentException("max < 1: " + maxRequests);
        }
        this.maxRequests = maxRequests;
        promoteCalls();
    }

    public synchronized void setIdleCallback(Runnable idleCallback) {
        this.idleCallback = idleCallback;
    }

    /**
     * 入队并行执行runnable
     */
    public void enqueue(Runnable runnable) {
        RunnableWrapper runnableWrapper = new RunnableWrapper(runnable);
        if (runningCallsCount() < maxRequests) {
            runningAsyncCalls.add(runnableWrapper);
            executorService().submit(runnableWrapper);
        } else {
            readyAsyncCalls.add(runnableWrapper);
        }
    }

    /**
     * 入队串行执行callable
     * 注意：需要排队，可能不会立即执行
     */
    public <T> T blockExecuted(Callable<T> callable) {
        runningSyncCalls.add(callable);
        FutureTask<T> futureTask = new FutureTask<>(callable);
        executorService().submit(futureTask);
        try {
            return futureTask.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            runningSyncCalls.remove(callable);
        }
    }

    private void promoteCalls() {
        if (runningCallsCount() >= maxRequests) {
            // Already running max capacity.
            return;
        }
        if (readyAsyncCalls.isEmpty()) {
            // No ready calls to promote.
            return;
        }

        for (Iterator<Runnable> i = readyAsyncCalls.iterator(); i.hasNext(); ) {
            Runnable call = i.next();
            i.remove();
            runningAsyncCalls.add(call);
            executorService().execute(call);
            if (runningCallsCount() >= maxRequests) {
                // Reached max capacity.
                return;
            }
        }
    }

    /**
     * 停止未执行的异步线程
     */
    public void finished(Runnable call) {
        finished(runningAsyncCalls, call, true);
    }

    private <T> void finished(Deque<T> calls, T call, boolean promoteCalls) {
        int runningCallsCount;
        Runnable idleCallback;
        synchronized (this) {
            if (!calls.remove(call)) {
                throw new AssertionError("Call wasn't in-flight!");
            }
            if (promoteCalls) {
                promoteCalls();
            }
            runningCallsCount = runningCallsCount();
            idleCallback = this.idleCallback;
        }

        if (runningCallsCount == 0 && idleCallback != null) {
            idleCallback.run();
        }
    }

    /**
     * 入队未执行的线程总数
     */
    public synchronized int queuedCallsCount() {
        return readyAsyncCalls.size();
    }

    /**
     * 当前运行的线程总数
     */
    public synchronized int runningCallsCount() {
        return runningAsyncCalls.size() + runningSyncCalls.size();
    }

    private static class NameThreadFactory implements ThreadFactory {
        private final AtomicInteger mCount = new AtomicInteger(1);
        String name;

        private NameThreadFactory(String name) {
            this.name = name;
        }

        public static ThreadFactory create(String name) {
            return new NameThreadFactory(name);
        }

        public Thread newThread(Runnable r) {
            return new Thread(r, name + "#" + mCount.getAndIncrement());
        }
    }

    private static class DispatcherHolder {
        private static final Dispatcher sInstance = new Dispatcher();
    }

    /**
     * runnable的包装类
     * 增加在线程运行完之后，停止的功能
     */
    static class RunnableWrapper implements Runnable {

        private final Runnable runnable;

        public RunnableWrapper(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 移除当前runnable，如果不溢出会占用线程池大小
                Dispatcher.with().finished(this);
            }
        }
    }
}
