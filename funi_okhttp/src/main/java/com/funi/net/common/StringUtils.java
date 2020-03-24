package com.funi.net.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 字典排序工具类
 *
 * @author
 */
public class StringUtils {

    /**
     * 字典排序
     *
     * @param str 待排序数组
     * @return 排序结果拼接字符
     */
    public static String dicSort(String... str) {
        List<String> strList = new ArrayList<>();
        for (String s : str) {
            strList.add(s);
        }
        Collections.sort(strList);
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : strList) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }
}
