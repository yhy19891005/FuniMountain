package yin.deng.dyutils.http;

import android.app.Application;
import android.os.Environment;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.annotation.CacheType;
import com.okhttplib.annotation.Encoding;
import com.okhttplib.callback.Callback;
import com.okhttplib.cookie.PersistentCookieJar;
import com.okhttplib.cookie.cache.SetCookieCache;
import com.okhttplib.cookie.persistence.SharedPrefsCookiePersistor;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import yin.deng.dyutils.utils.LogUtils;

public class MyHttpUtils {

    private final Gson mGson;

    public MyHttpUtils(Application context) {
        mGson = new Gson();
        String downloadFileDir = Environment.getExternalStorageDirectory().getPath() + "/my_okHttp_download/";
        String cacheDir = Environment.getExternalStorageDirectory().getPath() + "/my_okHttp_cache";
        OkHttpUtil.init(context)
                .setConnectTimeout(60)//连接超时时间
                .setWriteTimeout(30)//写超时时间
                .setReadTimeout(30)//读超时时间
                .setMaxCacheSize(10 * 1024 * 1024)//缓存空间大小
                .setCacheType(CacheType.FORCE_NETWORK)//缓存类型
                .setHttpLogTAG("MyHttpLog")//设置请求日志标识
                .setIsGzip(false)//Gzip压缩，需要服务端支持
                .setShowHttpLog(true)//显示请求日志
                .setShowLifecycleLog(false)//显示Activity销毁日志
                .setRetryOnConnectionFailure(true)//失败后不自动重连
                .setCachedDir(new File(cacheDir))//设置缓存目录
                .setDownloadFileDir(downloadFileDir)//文件下载保存目录
                .setResponseEncoding(Encoding.UTF_8)//设置全局的服务器响应编码
                .setRequestEncoding(Encoding.UTF_8)//设置全局的请求参数编码
//                .setHttpsCertificate("12306.cer")//设置全局Https证书
//                .addResultInterceptor(HttpInterceptor.ResultInterceptor)//请求结果拦截器
//                .addExceptionInterceptor(HttpInterceptor.ExceptionInterceptor)//请求链路异常拦截器
                .setCookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context)))//持久化cookie
                .build();
    }

    public void sendMsgGet(String requestUrl, JsonObject object, final Class x) {
        if (object == null) {
            object = new JsonObject();
        }
        HttpInfo requestInfo = HttpInfo.Builder()
                .setUrl(requestUrl)
                .addParamJson(mGson.toJson(object))
                .setNeedResponse(false)//设置返回结果为Response
                .build();
        OkHttpUtil.getDefault(this)//绑定生命周期
                .doGetAsync(requestInfo, new Callback() {
                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        String data = info.getRetDetail();
                        initSucessLog(info, true);
                        Object obj = mGson.fromJson(data, x);
                        EventBus.getDefault().post(obj);
                    }

                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        initSucessLog(info, false);
                    }
                });
    }

    public void sendMsgGet(String requestUrl, JsonObject object, Callback callback) {
        if (object == null) {
            object = new JsonObject();
        }
        HttpInfo requestInfo = HttpInfo.Builder()
                .setUrl(requestUrl)
                .addParamJson(mGson.toJson(object))
                .setNeedResponse(false)//设置返回结果为Response
                .build();
        OkHttpUtil.getDefault(this)//绑定生命周期
                .doGetAsync(requestInfo, callback);
    }


    public void sendMsgPost(String requestUrl, JsonObject object, final Class x) {
        if (object == null) {
            object = new JsonObject();
        }
        HttpInfo requestInfo = HttpInfo.Builder()
                .setUrl(requestUrl)
                .addHead("Content-Type", "application/json")
                .addParamJson(mGson.toJson(object))
                .setNeedResponse(false)//设置返回结果为Response
                .build();
        OkHttpUtil.getDefault(this)//绑定生命周期
                .doPostAsync(requestInfo, new Callback() {
                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        String data = info.getRetDetail();
                        initSucessLog(info, true);
                        Object obj = mGson.fromJson(data, x);
                        EventBus.getDefault().post(obj);
                    }

                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        initSucessLog(info, false);
                    }
                });
    }

    public void sendMsgPost(String requestUrl, JsonObject object, Callback callback) {
        if (object == null) {
            object = new JsonObject();
        }
        HttpInfo requestInfo = HttpInfo.Builder()
                .setUrl(requestUrl)
                .addHead("Content-Type", "application/json")
                .addParamJson(mGson.toJson(object))
                .setNeedResponse(false)//设置返回结果为Response
                .build();
        OkHttpUtil.getDefault(this)//绑定生命周期
                .doPostAsync(requestInfo, callback);
    }

    public void sendMsgPost(String requestUrl, HashMap<String,String> dataParams, Callback callback) {
        if (dataParams == null) {
            dataParams = new HashMap<>();
        }
        HttpInfo requestInfo = HttpInfo.Builder()
                                       .setUrl(requestUrl)
                                       .addHead("Content-Type", "application/json")
                                       .addParams(dataParams)
                                       .setNeedResponse(false)//设置返回结果为Response
                                       .build();
        OkHttpUtil.getDefault(this)//绑定生命周期
                  .doPostAsync(requestInfo, callback);
    }

    public void initSucessLog(HttpInfo info, boolean isSucess) {
        if (isSucess) {
            if(TextUtils.isEmpty(info.getParamJson())){
                LogUtils.i("请求结果（成功）：\n请求地址：" + info.getUrl() + "\n请求参数：" + info.getParams() + "\n响应结果:" + info.getRetDetail());
            }else {
                LogUtils.i("请求结果（成功）：\n请求地址：" + info.getUrl() + "\n请求参数：" + info.getParamJson() + "\n响应结果:" + info.getRetDetail());
            }
        } else {
            if(TextUtils.isEmpty(info.getParamJson())){
                LogUtils.i("请求结果（成功）：\n请求地址：" + info.getUrl() + "\n请求参数：" + info.getParams() + "\n响应结果:" + info.getRetDetail());
            }else {
                LogUtils.e("请求结果（失败）：\n请求地址：" + info.getUrl() + "\n请求参数：" + info.getParamJson() + "\n错误原因：" + info.getRetDetail());
            }
        }
    }


}
