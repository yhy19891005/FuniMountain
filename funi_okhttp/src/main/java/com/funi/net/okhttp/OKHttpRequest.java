package com.funi.net.okhttp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.funi.net.common.CheckNetUtil;
import com.funi.net.common.IRequestResult;
import com.funi.net.model.ResponseBaseDomain;
import com.funi.net.model.ResponseResult;
import com.funi.net.okhttp.upload.ExMultipartBody;
import com.funi.net.okhttp.upload.IUploadProgress;
import com.funi.net.okhttp.upload.UploadImageUtil;
import com.funi.utils.FuniLog;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OKHttpRequest
 * 网络请求
 *
 * @author pengqiang.zou
 * @data: 2019/02/24 14:08
 * @version: v1.0
 */
public class OKHttpRequest {
    //volatile的作用
    //1、防止重排序
    //2、线程可见性
    private final static String NET_ERROR = "抱歉，无网络连接。";
    private final static String REQUEST_ERROR = "抱歉，请求失败。";
    private final static String UPLOAD_ERROR = "抱歉，上传失败。";
    private final static String UPLOAD_FILE_NOT_EXITS = "上传的文件不存在，请重新选择。";
    private final static String MEDIA_TYPE = "application/json; charset=utf-8";
    private final static int QUERY_SUCCESS = 1;
    private final static int QUERY_ERROR = 2;
    private final static String SUCCESS = "200";

    private OKHttpRequest() {

    }

    /**
     * 判断是否有网络
     *
     * @param context
     * @param resultI
     * @return
     */
    private static boolean isNetConnect(Context context, IRequestResult resultI) {
        if (CheckNetUtil.isNetworkAvailable(context)) {
            return true;
        } else {
            resultI.failure(NET_ERROR);
            return false;
        }
    }

    /**
     * @param context
     * @param url            服务器上传地址
     * @param fileUrl        本地文件地址
     * @param uploadProgress 上传进度监听
     * @param resultI        上传结果监听
     */
    public static void uploadFile(Context context, String url, String fileUrl, final IUploadProgress uploadProgress, final IRequestResult resultI) {
        if (isNetConnect(context, resultI)) {
            File file = new File(fileUrl);
            if (!file.exists()) {
                resultI.failure(UPLOAD_FILE_NOT_EXITS);
                return;
            }

            if (UploadImageUtil.isImageFile(fileUrl)) {
                if (UploadImageUtil.isCompress(file)) {
                    //压缩后的图片
                    File compressFile = UploadImageUtil.compressBySize(fileUrl);
                    if (null != compressFile) {
                        uploadImage(url, compressFile, resultI, uploadProgress);
                    } else {
                        uploadImage(url, file, resultI, uploadProgress);
                    }
                } else {
                    uploadImage(url, file, resultI, uploadProgress);
                }
            } else {
                uploadImage(url, file, resultI, uploadProgress);
            }
        }
    }

    /**
     * @param url            服务器上传地址
     * @param uploadProgress 上传进度监听
     * @param resultI        上传结果监听
     */
    private static void uploadImage(final String url, File file, final IRequestResult resultI, final IUploadProgress uploadProgress) {
        //OkHttpClient httpClient = new OkHttpClient();
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        //            clientBuilder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
        clientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        clientBuilder.readTimeout(30, TimeUnit.SECONDS);
        OkHttpClient httpClient  = clientBuilder.build();
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        builder.addFormDataPart("file", file.getName(),
                RequestBody.create(MediaType.parse(UploadImageUtil.guessMimeType(file.getAbsolutePath())), file));

        ExMultipartBody exMultipartBody = new ExMultipartBody(builder.build()
                , new IUploadProgress() {

            @Override
            public void onProgress(long total, long current) {
                if (null != uploadProgress) {
                    uploadProgress.onProgress(total, current);
                }
            }
        });

        // 构建一个请求
        final Request request = new Request.Builder()
                .url(url)
                .post(exMultipartBody).build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                FuniLog.d(url + "  ERROR:  " + e.getMessage());
                queryResult(QUERY_ERROR, UPLOAD_ERROR, resultI);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //response.body().string() 这个方法只能调用一次
                String result = response.body().string();
                FuniLog.d(url + "  back:  " + result);
                queryResult(QUERY_SUCCESS, result, resultI);
            }
        });
    }

    /**
     * post提交json参数
     *
     * @param url     请求服务器地址
     * @param json    上传json参数
     * @param resultI 请求结果处理
     */
    public static void post(Context context, final String url, String json, final IRequestResult resultI) {
        FuniLog.d("url-->" + url + json);

        if (isNetConnect(context, resultI)) {
            OkHttpClient okHttpClient = MyOkHttpClient.getClient();
            RequestBody requestBody = FormBody.create(MediaType.parse(MEDIA_TYPE), json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    FuniLog.d(url + "  ERROR:  " + e.getMessage());
                    queryResult(QUERY_ERROR, REQUEST_ERROR, resultI);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //response.body().string() 这个方法只能调用一次
                    String result = response.body().string();
                    FuniLog.d(url + "  back:  " + result);
                    queryResult(QUERY_SUCCESS, result, resultI);
                }
            });
        }
    }

    /**
     * @param url     请求服务器地址
     * @param params  请求参数
     * @param resultI 请求结果处理
     */
    public static void post(Context context, final String url, HashMap<String, String> params, final IRequestResult resultI) {
        FuniLog.d("url-->" + url + params.toString());

        if (isNetConnect(context, resultI)) {
            OkHttpClient okHttpClient = MyOkHttpClient.getClient();
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            RequestBody formBody = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    FuniLog.d(url + "  ERROR:  " + e.getMessage());
                    queryResult(QUERY_ERROR, REQUEST_ERROR, resultI);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //response.body().string() 这个方法只能调用一次
                    String result = response.body().string();
                    FuniLog.d(url + "  back:  " + result);
                    queryResult(QUERY_SUCCESS, result, resultI);
                }
            });
        }
    }

    /**
     * get请求
     *
     * @param params  请求参数
     * @param url     请求服务器地址
     * @param resultI 请求结果处理
     */
    public static void get(Context context, final String url, HashMap<String, String> params, final IRequestResult resultI) {
        FuniLog.d("url-->" + url);

        if (isNetConnect(context, resultI)) {
            OkHttpClient okHttpClient = MyOkHttpClient.getClient();

            Request.Builder reqBuild = new Request.Builder().get();
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            if (null != params && params.size() > 0) {
                for (Map.Entry<String, String> p : params.entrySet()) {
                    urlBuilder.addQueryParameter(p.getKey(), p.getValue());
                }
            }
            reqBuild.url(urlBuilder.build());
            Request request = reqBuild.build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    FuniLog.d(url + "  ERROR:  " + e.getMessage());
                    queryResult(QUERY_ERROR, REQUEST_ERROR, resultI);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //response.body().string() 这个方法只能调用一次
                    String result = response.body().string();
                    FuniLog.d(url + "  back:  " + result);
                    queryResult(QUERY_SUCCESS, result, resultI);
                }
            });
        }
    }

    /**
     * 请求结果处理
     * 不能放到Okhttp线程中去处理数据
     * 否则更新ui等操作会报异常
     *
     * @param message
     * @param resultI
     */
    private static void queryResult(int type, String message, IRequestResult resultI) {
        Message msg = new Message();
        msg.what = type;
        ResponseResult result = new ResponseResult();
        result.setMessage(message);
        result.setResultI(resultI);
        msg.obj = result;
        handler.sendMessage(msg);
    }

    /**
     * 请求结果处理
     */
    @SuppressLint("HandlerLeak")
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ResponseResult result = (ResponseResult) msg.obj;
            if (msg.what == QUERY_SUCCESS) {
                JSONObject object = null;
                try {
                    object = new JSONObject(result.getMessage());
                    ResponseBaseDomain responseBaseDomain = new ResponseBaseDomain(object);
                    if (null != responseBaseDomain && responseBaseDomain.isOk()) {
                        result.getResultI().success(responseBaseDomain.getResult());
                    } else {
                        result.getResultI().failure(REQUEST_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result.getResultI().failure(REQUEST_ERROR);
                }
            } else if (msg.what == QUERY_ERROR) {
                result.getResultI().failure(result.getMessage());
            }
        }
    };
}
