package com.funi.net.okhttp.download;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public final class DownloadDispatcher {

    private static final DownloadDispatcher sDispatcher = new DownloadDispatcher();

    private DownloadDispatcher() {

    }

    public static DownloadDispatcher getDispatcher() {
        return sDispatcher;
    }

    /**
     * Ready async calls in the order they'll be run.
     */
    private final Deque<DownloadTask> readyTasks = new ArrayDeque<>();

    /**
     * Running asynchronous calls. Includes canceled calls that haven't finished yet.
     */
    private final Deque<DownloadTask> runningTasks = new ArrayDeque<>();

    /**
     * Running synchronous calls. Includes canceled calls that haven't finished yet.
     */
    private final Deque<DownloadTask> stopTasks = new ArrayDeque<>();

    // 最大只能下载多少个 3 5
    public void startDownload(final String url, final DownloadCallback callback) {
        // 获取文件的大小
        Call call = OkHttpManager.getManager().asyncCall(url);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 获取文件的大小
                long contentLength = response.body().contentLength();

                if (contentLength <= -1) {
                    callback.onFailure(new IOException());
                    return;
                }

                // 计算每个线程负责哪一块
                DownloadTask downloadTask = new DownloadTask(url, contentLength, callback);
                downloadTask.init();

                runningTasks.add(downloadTask);
            }
        });
    }

    public void recyclerTask(DownloadTask downloadTask) {
        runningTasks.remove(downloadTask);
        // 参考 OkHttp 的 Dispatcher 的源码,如果还有需要下载的开始下一个的下载
    }
}
