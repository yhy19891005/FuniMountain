package com.funi.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PhoneUtil
 *
 * @Description:电话号码util
 * @Author: pengqiang.zou
 * @CreateDate: 2018-11-30 13:45
 */
public class PhoneUtil {

    private PhoneUtil() {

    }

    /**
     * 校验手机号码是否合法
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneNumber(String phone) {
        Pattern p = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|6[6]|7[0-9]|8[0-9]|9[89])\\d{8}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 拨打电话
     * 打之前 请检查是否授权  Manifest.permission.CALL_PHONE
     *
     * @param context
     * @param tel     电话号码
     */
    public static void callPhone(Context context, String tel) {
        try {
            if (TextUtil.stringIsNull(tel)) {
                ToastUtil.show(context, "您拨打的电话号码不存在");
                return;
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.fromParts("tel", tel, null));//拼一个电话的Uri，拨打分机号 关键代码
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
