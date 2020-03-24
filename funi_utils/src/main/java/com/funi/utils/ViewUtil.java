package com.funi.utils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

/**
 * ViewUtil
 *
 * @Description:动画之类的
 * @Author: pengqiang.zou
 * @CreateDate: 2018-11-30 14:04
 */
public class ViewUtil {

    /**
     * view上到下结束动画
     *
     * @param view
     */
    public static void hideView(View view) {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f);
        mHiddenAction.setDuration(300);
        view.startAnimation(mHiddenAction);
    }


    /**
     * view下到上结束动画
     *
     * @param view
     */
    public static void showView(View view) {
        TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);
        view.startAnimation(mShowAction);
    }


    /**
     * view切换动画左进
     * @param context
     * @param layout
     * @param id
     * @return
     */
    public static Animation showLayout(Context context, final LinearLayout layout, int id) {
        Animation animation = AnimationUtils.loadAnimation(context, id);
        animation.setFillAfter(true);
        LinearInterpolator lin = new LinearInterpolator();
        animation.setInterpolator(lin);
        layout.startAnimation(animation);
        layout.setLayoutAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return animation;
    }

    /**
     * 上下平移动画
     *
     * @param moveLayout
     * @param fromH      h
     * @param toH        h
     */
    public static void showLayout(final View moveLayout, float fromH, float toH, int duration) {
        @SuppressLint("ObjectAnimatorBinding")
        ValueAnimator animY = ObjectAnimator.ofFloat(moveLayout, "height", fromH, toH);
        animY.setDuration(duration);
        animY.setInterpolator(new DecelerateInterpolator());
        animY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = ((Float) valueAnimator.getAnimatedValue());
                ViewGroup.LayoutParams layoutParams = moveLayout.getLayoutParams();
                layoutParams.height = (int) val;
                moveLayout.setLayoutParams(layoutParams);
            }
        });
        animY.start();
    }
}
