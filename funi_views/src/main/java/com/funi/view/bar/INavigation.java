package com.funi.view.bar;

import android.view.View;
import android.view.ViewGroup;

/**
 * INavigation
 *
 * @Description:导航栏规范
 * @Author: pengqiang.zou
 * @CreateDate: 2018-11-30 10:14
 */
public interface INavigation {
    /**
     * 创建导航栏
     */
    void createNavigationBar();

    /**
     * 绑定控件
     */
    void attachNavigationParams();

    /**
     * 将 NavigationView添加到父布局
     *
     * @param navigationBar 导航栏布局
     * @param parent
     */
    void attachParent(View navigationBar, ViewGroup parent);
}
