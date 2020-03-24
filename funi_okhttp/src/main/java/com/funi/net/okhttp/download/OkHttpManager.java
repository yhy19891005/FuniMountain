package com.funi.net.okhttp.download;

import com.funi.net.okhttp.MyOkHttpClient;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpManager {
    private static final OkHttpManager sManager = new OkHttpManager();
    private OkHttpClient okHttpClient = null;

    private OkHttpManager() {
        okHttpClient = MyOkHttpClient.getClient();
    }

    public static OkHttpManager getManager() {
        return sManager;
    }

    public Call asyncCall(String url) {
        Request request = new Request.Builder().url(url).build();
        return okHttpClient.newCall(request);
    }

    public Response syncResponse(String url, long start, long end) throws IOException {
        Request request = new Request.Builder().url(url)
                .addHeader("Range", "bytes=" + start + "-" + end).build();
        return okHttpClient.newCall(request).execute();
    }
}
