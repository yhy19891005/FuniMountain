package com.funi.net.model;

import com.google.gson.annotations.Expose;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * 消息提取类
 *
 * @author TangXiaoJun
 */
public class ResponseBaseDomain implements Serializable {
    public final static String KEY_STATUS = "success";
    public final static String KEY_RESULT = "result";

    @Expose
    private String success;
    @Expose
    private String result;

    /**
     * 是否请求成功
     *
     * @return
     */
    public boolean isOk() {
        return success.equals("true");
    }

    public ResponseBaseDomain() {

    }

    public ResponseBaseDomain(JSONObject response) throws Exception {
        if (response.has(KEY_RESULT)) {
            this.result = response.getString(KEY_RESULT);
        }
        if (response.has(KEY_STATUS)) {
            this.success = response.getString(KEY_STATUS);
        }
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
