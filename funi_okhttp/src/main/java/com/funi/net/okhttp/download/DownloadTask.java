package com.funi.net.okhttp.download;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class DownloadTask {
    private String mUrl;
    private long mContentLength;
    private List<DownloadRunnable> mRunnables;
    private
    @Nullable
    ExecutorService executorService;
    private volatile int mSucceedNumber;

    private DownloadCallback mCallback;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int THREAD_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));

    public DownloadTask(String url, long contentLength, DownloadCallback callback) {
        this.mUrl = url;
        this.mContentLength = contentLength;
        mRunnables = new ArrayList<>();
        this.mCallback = callback;
    }

    /**
     * 线程池
     *
     * @return
     */
    public synchronized ExecutorService executorService() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(0, THREAD_SIZE, 30, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), new ThreadFactory() {
                @Override
                public Thread newThread(@NonNull Runnable r) {
                    Thread thread = new Thread(r, "DownloadTask");
                    thread.setDaemon(false);
                    return thread;
                }
            });
        }
        return executorService;
    }

    /**
     * 初始化
     */
    public void init() {
        for (int i = 0; i < THREAD_SIZE; i++) {
            // 计算出每个线程要下载的内容
            long threadSize = mContentLength / THREAD_SIZE;

            // 初始化的时候 这里要去读取数据库
            long start = i * threadSize;
            long end = (i + threadSize) - 1;

            if (i == THREAD_SIZE - 1) {
                end = mContentLength - 1;
            }

            DownloadRunnable downloadRunnable = new DownloadRunnable(mUrl, i, start, end, new DownloadCallback() {

                @Override
                public void onFailure(IOException e) {
                    // 一个apk 下载里面有一个线程异常了，处理异常,把其他线程停止掉
                    mCallback.onFailure(e);
                }

                @Override
                public void onSucceed(File file) {
                    // 线程同步一下，
                    synchronized (DownloadTask.this) {
                        mSucceedNumber += 1;
                        if (mSucceedNumber == THREAD_SIZE) {
                            mCallback.onSucceed(file);
                            DownloadDispatcher.getDispatcher().recyclerTask(DownloadTask.this);
                        }
                    }
                }
            });
            // 通过线程池去执行
            executorService().execute(downloadRunnable);
        }
    }

    public void stop() {
        for (DownloadRunnable runnable : mRunnables) {
            if (null != runnable) {
                runnable.stop();
            }
        }
    }
}
