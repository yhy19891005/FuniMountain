package com.funi.view.pullview.internal;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.funi.view.R;
import com.funi.view.pullview.ProgressBarUtil;
import com.funi.view.pullview.PullToRefreshBase;

public class LoadingLayout extends FrameLayout {

    static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;

    private final ImageView headerImage;
    private final ProgressBar headerProgress;
    private final TextView headerText;
    private final TextView subText;

    private String pullLabel;
    private String refreshingLabel;
    private String releaseLabel;
    private String subString = "";

    private final Animation rotateAnimation, resetRotateAnimation;

    public LoadingLayout(Context context, final int mode, String releaseLabel, String pullLabel, String refreshingLabel) {
        super(context);
        ViewGroup header = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.view_pull_to_refresh_head, this);
        headerText = header.findViewById(R.id.pull_to_refresh_text);
        headerImage = header.findViewById(R.id.pull_to_refresh_image);
        headerProgress = header.findViewById(R.id.pull_to_refresh_progress);
        subText = header.findViewById(R.id.pull_to_refresh_sub_text);

        final Interpolator interpolator = new LinearInterpolator();
        rotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotateAnimation.setInterpolator(interpolator);
        rotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
        rotateAnimation.setFillAfter(true);

        resetRotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        resetRotateAnimation.setInterpolator(interpolator);
        resetRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
        resetRotateAnimation.setFillAfter(true);

        this.releaseLabel = releaseLabel;
        this.pullLabel = pullLabel;
        this.refreshingLabel = refreshingLabel;

        switch (mode) {
            case PullToRefreshBase.MODE_PULL_UP_TO_REFRESH:
                headerImage.setImageResource(R.mipmap.pulltorefresh_up_arrow);
                break;
            case PullToRefreshBase.MODE_PULL_DOWN_TO_REFRESH:
            default:
                headerImage.setImageResource(R.mipmap.pulltorefresh_down_arrow);
                break;
        }
    }

    public void reset() {
        headerText.setText(pullLabel);
        headerProgress.setVisibility(View.GONE);
        headerImage.setVisibility(View.VISIBLE);
    }

    public void releaseToRefresh() {
        headerText.setText(releaseLabel);
        headerImage.clearAnimation();
        headerImage.startAnimation(rotateAnimation);
    }

    public void showSubText() {
        subText.setVisibility(VISIBLE);
    }

    public void setSubText(String sub) {
        subString = sub;
        subText.setText(subString);
    }

    public void setPullLabel(String pullLabel) {
        this.pullLabel = pullLabel;
    }

    public void refreshing() {
        headerText.setText(refreshingLabel);
//        headerText.setTextColor(Color.BLACK);
        headerText.bringToFront();
        headerImage.clearAnimation();
        headerImage.setVisibility(View.GONE);
        headerProgress.setVisibility(View.VISIBLE);
    }

    public void setRefreshingLabel(String refreshingLabel) {
        this.refreshingLabel = refreshingLabel;
    }

    public void setReleaseLabel(String releaseLabel) {
        this.releaseLabel = releaseLabel;
    }

    public void pullToRefresh() {
        headerText.setText(pullLabel);
        headerImage.clearAnimation();
        headerImage.startAnimation(resetRotateAnimation);
    }

    public void setTextColor(int color) {
        headerText.setTextColor(color);
    }

}
