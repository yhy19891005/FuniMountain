package com.funi.view.pullview;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ProgressBar;

import java.lang.reflect.Method;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

/**
 * ProgressBarUtil
 *
 * @Description:xxx
 * @Author: pengqiang.zou
 * @CreateDate: 2017-07-26 20:52
 */
public class ProgressBarUtil {
    @SuppressLint("NewApi")
    public static void setProgressDrawable(@NonNull ProgressBar bar, @DrawableRes int resId) {
        Drawable layerDrawable = bar.getResources().getDrawable(resId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable d = getMethod("tileify", bar, new Object[] { layerDrawable, false });
            bar.setProgressDrawable(d);
        } else {
            bar.setProgressDrawableTiled(layerDrawable);
        }
    }

    private static Drawable getMethod(String methodName, Object o, Object[] paras) {
        Drawable newDrawable = null;
        try {
            Class<?> c[] = new Class[2];
            c[0] = Drawable.class;
            c[1] = boolean.class;
            Method method = ProgressBar.class.getDeclaredMethod(methodName, c);
            method.setAccessible(true);
            newDrawable = (Drawable) method.invoke(o, paras);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newDrawable;
    }
}
