package com.house.mine.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * 判别手机是否为正确手机号码
     */
    public static boolean isMobileNum(String mobile) {
        if (TextUtils.isEmpty(mobile) || mobile.length() != 11) return false;
        Pattern p = Pattern.compile("^1\\d{10}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 15位身份证号码的基本数字和位数验校
     */
    public static boolean is15Idcard(String idcard) {
        return !TextUtils.isEmpty(idcard) && Pattern.matches("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$", idcard);
    }

    /**
     * 18位身份证号码的基本数字和位数验校
     */
    public static boolean is18Idcard(String idcard) {
        return Pattern.matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$", idcard);
    }

    public static String getEntFileConfIdByImgType(String imgType,String orgType){
        if("营业执照".equals(imgType)){
            if(orgType.equals(UrlConfig.ORGTYPE_DEVELOPER)){
                return "4";
            }else if(orgType.equals(UrlConfig.ORGTYPE_BROKER)){
                return "8";
            }else {
                return "";
            }
        }else if("组织机构代码".equals(imgType)){
            if(orgType.equals(UrlConfig.ORGTYPE_DEVELOPER)){
                return "5";
            }else if(orgType.equals(UrlConfig.ORGTYPE_BROKER)){
                return "9";
            }else {
                return "";
            }
        }else if("资质证书".equals(imgType)){
            return "6";
        }else if("法人身份证".equals(imgType)){
            if(orgType.equals(UrlConfig.ORGTYPE_DEVELOPER)){
                return "7";
            }else if(orgType.equals(UrlConfig.ORGTYPE_BROKER)){
                return "10";
            }else {
                return "";
            }
        }else if("土地使用权证".equals(imgType)){
            return "22";
        }else if("建设工程规划许可证".equals(imgType)){
            return "23";
        }else if("土地使用权规划许可证".equals(imgType)){
            return "24";
        }else if("施工许可证".equals(imgType)){
            return "25";
        }else if("委托人".equals(imgType)){
            return "29";
        }else if("委托人身份证".equals(imgType)){
            return "30";
        }
        return "";
    }
}
