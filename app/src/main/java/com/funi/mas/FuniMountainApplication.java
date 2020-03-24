package com.funi.mas;

import android.content.Context;

import com.funi.mas.main.MainActivity;
import com.funi.view.FuniApplication;
import com.house.mine.util.MediaLoader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import java.util.HashMap;
import java.util.Locale;

import yin.deng.dyutils.refresh.SmartRefreshLayout;
import yin.deng.dyutils.refresh.api.DefaultRefreshHeaderCreater;
import yin.deng.dyutils.refresh.api.RefreshHeader;
import yin.deng.dyutils.refresh.api.RefreshLayout;
import yin.deng.dyutils.utils.LogUtils;
import yin.deng.dyutils.utils.SharedPreferenceUtil;
import yin.deng.dyutils.view.MyFooterView;
import yin.deng.dyutils.view.MyHeaderView;

/**
 * FuniMountainApplication
 *
 * @Description:
 * @Author: pengqiang.zou
 * @CreateDate: 2019-04-02 13:43
 */
public class FuniMountainApplication extends FuniApplication {
    public static FuniMountainApplication application;
    private SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    protected void initBugly() {
        Beta.canShowUpgradeActs.add(MainActivity.class);
        Bugly.setIsDevelopmentDevice(this, true);
        Bugly.init(this, "ab2eb93a02", true);
        initX5();
        initAlbum();
        application=this;
    }

    private void initAlbum() {
        Album.initialize(AlbumConfig.newBuilder(this)
                                    .setAlbumLoader(new MediaLoader())
                                    .setLocale(Locale.getDefault())
                                    .build());
    }

    public SharedPreferenceUtil getSharedPreferenceUtil() {
        if (sharedPreferenceUtil == null) {
            sharedPreferenceUtil = new SharedPreferenceUtil(this,getApplicationContext().getPackageName());
        }
        return sharedPreferenceUtil;
    }

    public void initX5() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        QbSdk.initTbsSettings(map);
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                LogUtils.i("X5浏览器初始化：完成");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                LogUtils.i("X5浏览器初始化："+b);
            }
        });
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(yin.deng.dyutils.R.color.normal_bg, yin.deng.dyutils.R.color.normal_4a);//全局设置主题颜色
                MyHeaderView head = new MyHeaderView(context);
                return head;//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            layout.setPrimaryColorsId(yin.deng.dyutils.R.color.normal_bg, yin.deng.dyutils.R.color.normal_4a);//全局设置主题颜色
            MyFooterView footer = new MyFooterView(context);
            return footer;
        });
    }
}
