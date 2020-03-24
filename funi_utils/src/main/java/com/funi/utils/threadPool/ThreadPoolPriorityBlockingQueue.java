package com.funi.utils.threadPool;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

/**
 * ThreadPoolBlockingQueue
 * 线程池
 *
 * @Description: 无序的可以根据优先级进行排序 ，指定的对象要实现 Comparable 作比较
 * @Author: pengqiang.zou
 * @CreateDate: 2018-12-25 14:39
 */
public class ThreadPoolPriorityBlockingQueue {

    public static ThreadPoolExecutor threadPoolExecutor;

    //线程队列个数
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new PriorityBlockingQueue<Runnable>(126);

    static {
        threadPoolExecutor = new ThreadPoolExecutor(
                4,// 核心线程数，就是线程池里面的核心线程数量
                10, // 最大线程数，线程池中的最大线程数
                60,// 线程存活的时间，没事干的时候的空闲存活时间，超过这个时间线程就会被销毁
                TimeUnit.SECONDS,// 线程存活时间的单位
                sPoolWorkQueue,// 线程队列
                new ThreadFactory() {// 线程创建工厂，如果线程池需要创建线程就会调用 newThread 来创建
                    @Override
                    public Thread newThread(@NonNull Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setDaemon(false); // 不是守护线程
                        return thread;
                    }
                });
    }

}
