package com.example.demo.ThreadPools;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Thread {

    TimeUnit timeUnit = TimeUnit.DAYS;

    BlockingDeque<Runnable> blockingDeque;

    Runnable runnable;

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            10,10,100L,timeUnit,blockingDeque);

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    volatile String v = "";

    private AtomicInteger atomicInteger;
}
