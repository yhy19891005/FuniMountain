package com.funi.utils;

import android.util.Log;

/**
 * 日志输出工具类
 * 发布应用时请关闭日志输出 isDebug = false
 */
public class FuniLog {
    public static boolean isDebug = true;//true输出日志 false关闭日志输出
    private static final String TAG = "FuniLog";

    public static void v(String log) {
        if (isDebug) {
            Log.v(TAG, log);
        }
    }

    public static void i(String log) {
        if (isDebug) {
            Log.i(TAG, log);
        }
    }

    public static void d(String log) {
        if (isDebug) {
            int segmentSize = 3 * 1024;
            long length = log.length();
            if (length <= segmentSize ) {// 长度小于等于限制直接打印
                Log.d(TAG, log);
            }else {
                while (log.length() > segmentSize ) {// 循环分段打印日志
                    String logContent = log.substring(0, segmentSize );
                    log = log.replace(logContent, "");
                    Log.d(TAG, logContent);
                }
                Log.d(TAG, log);// 打印剩余日志
            }
        }

    }


    public static void w(String log) {
        if (isDebug) {
            Log.w(TAG, log);
        }
    }


    public static void e(String log) {
        if (isDebug) {
            Log.e(TAG, log);
        }
    }
}
