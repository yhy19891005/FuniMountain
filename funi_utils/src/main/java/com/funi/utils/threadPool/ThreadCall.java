package com.funi.utils.threadPool;


import com.funi.utils.FuniLog;

import androidx.annotation.NonNull;

/**
 * ThreadCall
 *
 * @Description:xxx
 * @Author: pengqiang.zou
 * @CreateDate: 2018-12-25 15:30
 */
public class ThreadCall implements Runnable,Comparable<ThreadCall> {

    public ThreadCall(){

    }

    @Override
    public int compareTo(@NonNull ThreadCall o) {
        return 0;
    }

    @Override
    public void run() {
        FuniLog.d("测试---------------->");
    }
}
