package com.funi.net.common;


/**
 * RequestResultI 解析数据回调接口
 *
 * @author pengqiang.zou
 * @data: 2015/06/24 13:56
 * @version: v1.0
 */
public interface IRequestResult {
    void success(String result);

    void failure(String message);
}
