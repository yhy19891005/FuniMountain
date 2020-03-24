package com.funi.net.okhttp;

import android.content.Context;

import com.funi.net.common.RequestBaseUrl;
import com.funi.net.okhttp.download.DownloadCallback;
import com.funi.net.okhttp.download.DownloadDispatcher;
import com.funi.net.okhttp.download.FileManager;
import com.funi.utils.TextUtil;
import com.funi.utils.ToastUtil;

/**
 * 多线程文件下载
 */
public class DownloadRequest {
    private static final DownloadRequest downloadRequest = new DownloadRequest();

    private DownloadRequest() {

    }

    public static DownloadRequest getInstance() {
        return downloadRequest;
    }

    private void init(Context context) {
        FileManager.manager().init(context);
    }

    /**
     * 下载文件
     *
     * @param context
     * @param callback
     */
    public void downloadFile(Context context, DownloadCallback callback) {
        if (TextUtil.stringIsNull(RequestBaseUrl.DOWNLOAD_URL)) {
            ToastUtil.show(context, "请先配置下载地址");
        } else {
            init(context.getApplicationContext());
            DownloadDispatcher.getDispatcher().startDownload(RequestBaseUrl.DOWNLOAD_URL, callback);
        }
    }
}
