package com.house.infomation.request;

import android.content.Context;

import com.funi.net.common.IRequestResult;
import com.funi.net.common.RequestBaseUrl;
import com.funi.net.okhttp.OKHttpRequest;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * HouseInfoRequest
 *
 * @Description:
 * @Author: pengqiang.zou
 * @CreateDate: 2019-04-12 14:36
 */
public class HouseInfoRequest {
    private final static String INFORMATION_LIST = RequestBaseUrl.BASE_URl + "/information/list";


    /**
     * @param context
     * @param type     informationChannelId
     *                 这个参数对应如下
     *                 房产资讯 1
     *                 诚信榜 2
     *                 政策法规 3
     *                 办事指南 4
     *                 通知公告 5
     * @param page
     * @param pageSize
     * @param resultI
     */
    public static void queryChannelList(Context context, int type, int page, int pageSize, IRequestResult resultI) {
        JSONObject object = new JSONObject();
        try {
            object.put("page", page);
            object.put("pageSize", pageSize);
            if(type > 0) {
                object.put("informationChannelId", type);
            }else{
                object.put("informationChannelId", "");
            }
            OKHttpRequest.post(context, INFORMATION_LIST, object.toString(), resultI);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
