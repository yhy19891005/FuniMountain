package com.funi.net.okhttp;

import android.content.Context;

import com.funi.net.common.IRequestResult;
import com.funi.net.common.RequestBaseUrl;
import com.funi.net.okhttp.upload.IUploadProgress;
import com.funi.utils.TextUtil;
import com.funi.utils.ToastUtil;

/**
 * UploadFileRequest
 *
 * @Description:文件上传、下载
 * @Author: pengqiang.zou
 * @CreateDate: 2019-02-27 14:37
 */
public class UploadRequest {

    /**
     * 文件上传
     *
     * @param context
     * @param filePath       文件地址
     * @param uploadProgress 上传进度监听
     * @param resultI        上传结果监听
     */
    public static void uploadFile(Context context, String filePath, IUploadProgress uploadProgress, IRequestResult resultI) {
        if (TextUtil.stringIsNull(RequestBaseUrl.UPLOAD_URL)) {
            ToastUtil.show(context, "请先配置上传地址");
        } else {
            OKHttpRequest.uploadFile(context, RequestBaseUrl.UPLOAD_URL, filePath, uploadProgress, resultI);
        }
    }
}
