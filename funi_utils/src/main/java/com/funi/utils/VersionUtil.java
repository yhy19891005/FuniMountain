package com.funi.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * VersionUtil
 *
 * @Description:版本信息util
 * @Author: pengqiang.zou
 * @CreateDate: 2018-11-30 13:42
 */
public class VersionUtil {
    /**
     * 获取版本名字
     *
     * @return
     */
    public static String getVersionName(Context context) {
        String version = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取版本Code
     *
     * @return
     */
    public static int getVersionCode(Context context) {
        int code = 0;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            code = packInfo.versionCode;
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }
}
