package com.funi.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context,float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 效验密码是否符合8到25位包含字母数字
     *
     * @param password
     * @return
     */
    public static boolean isPassWord(String password) {
        Pattern p = Pattern.compile("^(?![^a-zA-Z]+$)(?!\\D+$).{8,25}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 输入数字只能一位小数
     *
     * @param text
     * @return
     */
    public static boolean isNumberHeFa(String text) {
        Pattern p = Pattern.compile("^[0-9]+([.][0-9]{1}){0,1}$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }


    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getApplicationContext().getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }



    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 隐藏输入法
     *
     * @param context
     * @param isShow
     * @param view
     */
    public static void inputMethod(Context context, boolean isShow, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
        } else {
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    /**
     * 当前应用是否处于前台
     * @param context
     * @return
     */
    public static boolean isForeground(Context context) {
        if (context != null) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            String currentPackageName = cn.getPackageName();
            if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName())) {
                return true;
            }
            return false;
        }
        return false;
    }
}