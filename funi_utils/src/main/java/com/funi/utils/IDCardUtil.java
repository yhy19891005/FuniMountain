package com.funi.utils;

import java.util.HashMap;

/**
 * IDCardUtil
 *
 * @Description:身份证util
 * @Author: pengqiang.zou
 * @CreateDate: 2018-11-30 14:30
 */
public class IDCardUtil {
    private String _codeError;
    final int[] wi = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
    final int[] vi = new int[]{1, 0, 88, 9, 8, 7, 6, 5, 4, 3, 2};
    private int[] ai = new int[18];
    private static String[] _areaCode = new String[]{"11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81", "82", "91"};
    private static HashMap<String, Integer> dateMap = new HashMap();
    private static HashMap<String, String> areaCodeMap;

    static {
        dateMap.put("01", Integer.valueOf(31));
        dateMap.put("02", null);
        dateMap.put("03", Integer.valueOf(31));
        dateMap.put("04", Integer.valueOf(30));
        dateMap.put("05", Integer.valueOf(31));
        dateMap.put("06", Integer.valueOf(30));
        dateMap.put("07", Integer.valueOf(31));
        dateMap.put("08", Integer.valueOf(31));
        dateMap.put("09", Integer.valueOf(30));
        dateMap.put("10", Integer.valueOf(31));
        dateMap.put("11", Integer.valueOf(30));
        dateMap.put("12", Integer.valueOf(31));
        areaCodeMap = new HashMap();
        String[] var3 = _areaCode;
        int var2 = _areaCode.length;

        for (int var1 = 0; var1 < var2; ++var1) {
            String code = var3[var1];
            areaCodeMap.put(code,null);
        }

    }

    public IDCardUtil() {
    }

    public boolean verifyLength(String code) {
        int length = code.length();
        if (length != 15 && length != 18) {
            this._codeError = "错误：输入的身份证号不是15位和18位的";
            return false;
        } else {
            return true;
        }
    }

    public boolean verifyAreaCode(String code) {
        String areaCode = code.substring(0, 2);
        if (areaCodeMap.containsKey(areaCode)) {
            return true;
        } else {
            this._codeError = "错误：输入的身份证号的地区码(1-2位)[" + areaCode + "]不符合中国行政区划分代码规定(GB/T2260-1999)";
            return false;
        }
    }

    public boolean verifyBirthdayCode(String code) {
        String month = code.substring(10, 12);
        boolean isEighteenCode = 18 == code.length();
        if (!dateMap.containsKey(month)) {
            this._codeError = "错误：输入的身份证号" + (isEighteenCode ? "(11-12位)" : "(9-10位)") + "不存在[" + month + "]月份,不符合要求(GB/T7408)";
            return false;
        } else {
            String dayCode = code.substring(12, 14);
            Integer day = (Integer) dateMap.get(month);
            String yearCode = code.substring(6, 10);
            Integer year = Integer.valueOf(yearCode);
            if (day != null) {
                if (Integer.valueOf(dayCode).intValue() > day.intValue() || Integer.valueOf(dayCode).intValue() < 1) {
                    this._codeError = "错误：输入的身份证号" + (isEighteenCode ? "(13-14位)" : "(11-13位)") + "[" + dayCode + "]号不符合小月1-30天大月1-31天的规定(GB/T7408)";
                    return false;
                }
            } else if ((year.intValue() % 4 != 0 || year.intValue() % 100 == 0) && year.intValue() % 400 != 0) {
                if (Integer.valueOf(dayCode).intValue() > 28 || Integer.valueOf(dayCode).intValue() < 1) {
                    this._codeError = "错误：输入的身份证号" + (isEighteenCode ? "(13-14位)" : "(11-13位)") + "[" + dayCode + "]号在" + year + "平年的情况下未符合1-28号的规定(GB/T7408)";
                    return false;
                }
            } else if (Integer.valueOf(dayCode).intValue() > 29 || Integer.valueOf(dayCode).intValue() < 1) {
                this._codeError = "错误：输入的身份证号" + (isEighteenCode ? "(13-14位)" : "(11-13位)") + "[" + dayCode + "]号在" + year + "闰年的情况下未符合1-29号的规定(GB/T7408)";
                return false;
            }

            return true;
        }
    }

    public boolean containsAllNumber(String code) {
        String str = "";
        if (code.length() == 15) {
            str = code.substring(0, 15);
        } else if (code.length() == 18) {
            str = code.substring(0, 17);
        }

        char[] ch = str.toCharArray();

        for (int i = 0; i < ch.length; ++i) {
            if (ch[i] < 48 || ch[i] > 57) {
                this._codeError = "错误：输入的身份证号第" + (i + 1) + "位包含字母";
                return false;
            }
        }

        return true;
    }

    public String getCodeError() {
        return this._codeError;
    }

    public boolean verify(String idcard) {
        this._codeError = "";
        if (!this.verifyLength(idcard)) {
            return false;
        } else if (!this.containsAllNumber(idcard)) {
            return false;
        } else {
            String eifhteencard = "";
            if (idcard.length() == 15) {
                eifhteencard = this.uptoeighteen(idcard);
            } else {
                eifhteencard = idcard;
            }

            return !this.verifyAreaCode(eifhteencard) ? false : (!this.verifyBirthdayCode(eifhteencard) ? false : this.verifyMOD(eifhteencard));
        }
    }

    public boolean verifyMOD(String code) {
        String verify = code.substring(17, 18);
        if ("x".equals(verify)) {
            code = code.replaceAll("x", "X");
            verify = "X";
        }

        String verifyIndex = this.getVerify(code);
        if (verify.equals(verifyIndex)) {
            return true;
        } else {
            this._codeError = "错误：输入的身份证号最末尾的数字验证码错误";
            return false;
        }
    }

    public String getVerify(String eightcardid) {
        int remaining = 0;
        if (eightcardid.length() == 18) {
            eightcardid = eightcardid.substring(0, 17);
        }

        if (eightcardid.length() == 17) {
            int sum = 0;

            int i;
            for (i = 0; i < 17; ++i) {
                String k = eightcardid.substring(i, i + 1);
                this.ai[i] = Integer.parseInt(k);
            }

            for (i = 0; i < 17; ++i) {
                sum += this.wi[i] * this.ai[i];
            }

            remaining = sum % 11;
        }

        return remaining == 2 ? "X" : String.valueOf(this.vi[remaining]);
    }

    public String uptoeighteen(String fifteencardid) {
        String eightcardid = fifteencardid.substring(0, 6);
        eightcardid = eightcardid + "19";
        eightcardid = eightcardid + fifteencardid.substring(6, 15);
        eightcardid = eightcardid + this.getVerify(eightcardid);
        return eightcardid;
    }
}
