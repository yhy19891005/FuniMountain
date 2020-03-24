package com.funi.view.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * BaseActivity
 *
 * @Description:Activity基类
 * @Author: pengqiang.zou
 * @CreateDate: 2018-11-30 13:03
 */
public class BaseActivity extends Activity {
    protected boolean isNeedLoading = false;//是否需要显示加载进度条
    protected int layoutId;//布局文件id

    private String umName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusStyle();
    }

    /**
     * 设置状态栏背景及字体颜色
     */
    public void setStatusStyle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 设置状态栏底色白色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.WHITE);
            // 设置状态栏字体黑色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        umName = this.getClass().getSimpleName();
        MobclickAgent.onPageStart(umName); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写)
        HashMap<String, String> hashMap = new HashMap<>();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(umName); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息
        MobclickAgent.onPause(this);
    }


    //防止设置中设置字体大小，影响界面显示
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
