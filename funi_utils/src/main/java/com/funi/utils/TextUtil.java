package com.funi.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.widget.TextView;

/**
 * TextUtil
 *
 * @Description:TextUtil工具
 * @Author: pengqiang.zou
 * @CreateDate: 2018-11-30 13:43
 */
public class TextUtil {

    private TextUtil(){}

    /**
     * 获取字符串 避免返回null
     *
     * @param str
     * @return
     */
    public static String getText(String str) {
        if (stringIsNull(str)) {
            return "";
        } else {
            return str.trim();
        }
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean stringIsNull(String str) {
        if ("".equals(str) || null == str) {
            return true;
        }

        return false;
    }

    /**
     * 设置hint文字大小
     *
     * @param textView
     * @param hintText
     */
    public static <T extends TextView> void setTextHintText(T textView, String hintText) {
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString(hintText);
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(11, true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint
        textView.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }


    /**
     * 判断输入的数字是否合法
     *
     * @param s     输入的数字字符串
     * @param digit 小数保留的位数
     * @return
     */
    public static boolean inputNumberIsRight(String s, int digit) {
        if (".".equals(s) || s.contains("/")) {
            return false;
        }

        //输入包含2个或者2个以上小数点的数字不合法
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (".".equals(String.valueOf(s.charAt(i)))) {
                count++;
            }
        }
        if (count > 1) {
            return false;
        }

        //输入00开头的数字不合法
        if (s.startsWith("0")) {
            if (s.length() >= 2 && !s.startsWith("0.")) {
                return false;
            }
        }

        //判断输入的小数位数是否合法
        if (s.contains(".")) {
            String[] str = s.split("\\.");
            if (str != null) {
                if (str.length == 2) {
                    if (null != str[1]) {
                        if (str[1].length() > digit) {
                            return false;
                        }
                    }

                    if (s.endsWith(".")) {
                        return false;
                    }
                }
            }
        }

        //输入的数字不能超过int的最大值
        if (!TextUtil.stringIsNull(s)) {
            try {
                double price = Double.valueOf(s);
                int p = (int) (price * 10000);
                if (p >= Integer.MAX_VALUE) {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }


    /**
     * 汉字数字转为数字
     *
     * @param chinese
     * @return
     */
    public static int chineseToNumber(String chinese) {
        try {
            if (isNum(chinese)) {
                return Integer.parseInt(chinese);
            }

            if (chinese.equals("两")) {
                return 2;
            }

            String[] chin = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
            for (int i = 0; i < chin.length; i++) {
                if (chin[i].equals(chinese)) {
                    return i;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    /**
     * 字符串是不是纯数字
     *
     * @param str
     * @return
     */
    public static boolean isNum(String str) {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    /**
     * 判断字符串是否可以转double
     *
     * @param str
     * @return
     */
    public static boolean isStringToDouble(String str) {
        boolean ret = true;
        try {
            double d = Double.parseDouble(str);
            ret = true;
        } catch (Exception ex) {
            ret = false;
        }
        return ret;
    }

    /**
     * 如果double的位数超过2位则保留2位小数
     *
     * @param d
     * @return
     */
    public static String get2Double(Double d) {
        if (d > 1.0) {
            int len = (d + "").length() - (d + "").indexOf(".") - 1;
            if (len > 2) {
                java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                return df.format(d);
            } else {
                return String.valueOf(d);
            }
        } else {
            int length = "1.00".length();
            String str = String.valueOf(d);
            if (str.length() > length) {
                return str.substring(0, length);
            } else {
                return str;
            }
        }
    }
}
