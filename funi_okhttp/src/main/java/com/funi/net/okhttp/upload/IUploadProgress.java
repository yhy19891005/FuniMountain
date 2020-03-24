package com.funi.net.okhttp.upload;

/**
 * IUploadProgress
 *
 * @Description:上传进度进度监听
 * @Author: pengqiang.zou
 * @CreateDate: 2019-02-26 13:59
 */
public interface IUploadProgress {
    void onProgress(long total, long current);
}
