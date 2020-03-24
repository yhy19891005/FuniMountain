package com.funi.net.model;

import com.funi.net.common.IRequestResult;

/**
 * ResponseResult
 *
 * @Description:请求结果实体
 * @Author: pengqiang.zou
 * @CreateDate: 2019-03-22 13:06
 */
public class ResponseResult {
    private String message;
    private IRequestResult resultI;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IRequestResult getResultI() {
        return resultI;
    }

    public void setResultI(IRequestResult resultI) {
        this.resultI = resultI;
    }
}
