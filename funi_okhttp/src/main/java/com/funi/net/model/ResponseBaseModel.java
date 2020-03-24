package com.funi.net.model;

import com.google.gson.annotations.Expose;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * @Description:ResponseBaseDomain
 * @author: zoupengqiang
 * @CreateDate: 2016/11/22
 */

public class ResponseBaseModel implements Serializable {
    private final static String KEY_MESSAGE = "message";
    private final static String KEY_SUCCESS = "success";
    private final static String KEY_DATA = "data";
    private final static String KEY_CODE = "code";

    @Expose
    private String message;  //直接返回集合  则表述集合的size
    @Expose
    private boolean success;   //请求是否成功
    @Expose
    private String data;   //数据JSON(服务器是Object类型)  可能是： 1、单个对象  2、单个字符串 3、集合
    @Expose
    private int code;//状态码

    public ResponseBaseModel() {

    }

    /**
     * 根据service返回的JSON数据构建
     *
     * @param response
     * @throws Exception
     */
    public ResponseBaseModel(JSONObject response) throws Exception {
        if (response.has(KEY_MESSAGE)) {
            this.message = response.getString(KEY_MESSAGE);
        }
        if (response.has(KEY_SUCCESS)) {
            this.success = response.getBoolean(KEY_SUCCESS);
        }
        if (response.has(KEY_DATA)) {
            this.data = response.getString(KEY_DATA);
        }
        if (response.has(KEY_CODE)) {
            this.code = response.getInt(KEY_CODE);
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
