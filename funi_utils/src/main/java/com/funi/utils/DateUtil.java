package com.funi.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateUtil
 *
 * @Description:xxx
 * @Author: pengqiang.zou
 * @CreateDate: 2016-04-14 14:01
 */
public class DateUtil {
    private DateUtil() {

    }

    /**
     * 获取年-月-日
     *
     * @return
     */
    public static String getYearMonthDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    /**
     * 获取年月
     *
     * @return
     */
    public static String getYearMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(new Date());
    }

    /**
     * 得到月份
     *
     * @param date
     * @return
     */
    public static int getMonth(String date) {
        if (!TextUtil.stringIsNull(date)) {
            String[] strs = date.split("-");
            return Integer.parseInt(strs[0] + strs[1]);
        } else {
            return 0;
        }
    }

    /**
     * yyyy-MM
     *
     * @param date
     * @return
     */
    public static String getYearMonth(String date) {
        if (!TextUtil.stringIsNull(date)) {
            String[] strs = date.split("-");
            return strs[0] + "-" + strs[1];
        } else {
            return null;
        }
    }

    /**
     * 获得当前日期
     * @return
     */
    public static String getToday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date());
    }

    /**
     * 获得到达时间
     *
     * @return
     */
    public static String getLocationTime() {
        String date = getToday();
        String[] str = date.split(" ");
        return str[1];
    }

    /**
     * 是否是上午
     *
     * @param dateString
     * @return
     */
    public static boolean isAM(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
            String hour = String.valueOf(date.getHours());
            if (Integer.parseInt(hour) < 12) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }
}
