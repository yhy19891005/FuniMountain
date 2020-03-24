package com.house.mine.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class DensityUtil {

    public static int dp2px(Context context,int dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }

    public static int getWindowWidth(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }
}
