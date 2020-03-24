package com.house.enquiry.ui;

import yin.deng.dyutils.http.BaseHttpInfo;

public class IsOkInfo extends BaseHttpInfo {

    /**
     * message : 合同不存在！
     * code : -1
     * success : false
     */

    private String message;
    private String result;
    private int code;
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
