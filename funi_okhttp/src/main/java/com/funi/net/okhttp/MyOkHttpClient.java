package com.funi.net.okhttp;

import android.os.Environment;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * MyOkHttpClient
 *
 * @Description:
 * @Author: pengqiang.zou
 * @CreateDate: 2019-02-25 15:54
 */
public class MyOkHttpClient {
    //volatile的作用
    //1、防止重排序
    //2、线程可见性
    private static volatile OkHttpClient mOkHttpClient = null;
    //超时时间
    private static final int TIME_OUT = 30;
    //缓存大小
    private static final int CACHE_SIZE = 30;

    private MyOkHttpClient() {

    }

    /**
     * 单例模式这样写保证线程安全及效率
     *
     * @return
     */
    public static OkHttpClient getClient() {
        if (mOkHttpClient == null) {
            synchronized (OKHttpRequest.class) {
                if (null == mOkHttpClient) {
                    File sdCache = new File(Environment.getExternalStorageDirectory(), "cache");
                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(new Cache(sdCache.getAbsoluteFile(), CACHE_SIZE))
                            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return mOkHttpClient;
    }

}
