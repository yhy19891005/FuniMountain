package com.funi.view;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.funi.utils.FuniLog;
import com.funi.utils.storage.PreferencesUtils;
import androidx.multidex.MultiDex;

/**
 * FuniApplication
 *
 * @Description:xxx
 * @Author: pengqiang.zou
 * @CreateDate: 2018-11-30 14:33
 */
public abstract class FuniApplication extends Application {

    public static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        PreferencesUtils.getInstance().init(this);

        //initUmeng();
        initBugly();
        initArouter();
    }

    /**
     * 655
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    //注册Arouter
    private void initArouter() {
        if (FuniLog.isDebug) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(FuniApplication.this);
    }


    public static Context getAppContext() {
        return context;
    }


    /**
     * 初始化友盟相关信息
     */
    // protected abstract void initUmeng();

    /**
     * 初始化腾讯bugly
     *
     * @return
     */
    protected abstract void initBugly();


//    static {
//        //启用矢量图兼容
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        //设置全局默认配置（优先级最低，会被其他设置覆盖）
//        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
//            @Override
//            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
//                //全局设置（优先级最低）
//                layout.setEnableAutoLoadMore(true);
//                layout.setEnableOverScrollDrag(false);
//                layout.setEnableOverScrollBounce(true);
//                layout.setEnableLoadMoreWhenContentNotFull(true);
//                layout.setEnableScrollContentWhenRefreshed(true);
//            }
//        });
//
//        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
//            @NonNull
//            @Override
//            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
//                return new ClassicsFooter(context);
//            }
//        });
//
//        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
//            @NonNull
//            @Override
//            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
//                //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
//                layout.setPrimaryColorsId(R.color.color_bar, android.R.color.white);
//                return new ClassicsHeader(context);
//            }
//        });
//    }
}
