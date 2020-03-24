package com.funi.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cunoraz.gifview.library.GifView;
import com.funi.view.R;
import com.funi.view.bar.Default1NavigationBar;
import com.funi.view.bar.Default2NavigationBar;
import com.funi.view.bar.DefaultNavigationBar;
import com.funi.view.pullview.ProgressBarUtil;

import androidx.annotation.Nullable;

/**
 * BaseNoramlActivity
 *
 * @Description:带导航条的Activity基类
 * @Author: pengqiang.zou
 * @CreateDate: 2018-12-03 10:46
 */
public abstract class BaseNormalActivity extends BaseActivity {
    private ViewGroup viewGroup;//用来添加导航栏
    private GifView gifView;
    private FrameLayout layoutContent;

    //菊花信息提示
    protected TextView loadText = null;
    //菊花重新加载
    protected ImageView ivReload = null;
    //点击重新加载
    private RelativeLayout layoutReload = null;
    //默认导航栏
    public DefaultNavigationBar defaultNavigationBar;
    public Default1NavigationBar navigationBarRightText;
    public Default2NavigationBar navigationBarRightImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_normal);

        viewGroup = findViewById(R.id.view_group);
        layoutContent = findViewById(R.id.layout_content);

        needLoading();
        setLayoutId();

        if (isNeedLoading) {
            showLoadingLayout();
            queryData();
        } else {
            setLayoutView();//不显示加载进度条
            onCreate();
        }
    }

    /**
     * 显示加载数据界面
     */
    private void showLoadingLayout() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_loading_layout, null);
        gifView = view.findViewById(R.id.load_gif);
        gifView.play();
        ivReload = view.findViewById(R.id.iv_reload);
        loadText = view.findViewById(R.id.page_loading_load);
        layoutReload = view.findViewById(R.id.layout_reload);
        layoutContent.addView(view);
    }

    /**
     * 直接显示界面
     */
    protected void setLayoutView() {
        View view = LayoutInflater.from(this).inflate(layoutId, null);
        layoutContent.addView(view);
    }


    /**
     * 加载数据成功
     */
    protected void loadDataSuccess() {
        gifView.pause();
        isReload = false;
        View view = LayoutInflater.from(this).inflate(layoutId, null);
        layoutContent.removeAllViews();
        layoutContent.addView(view);
        onCreate();
    }

    /**
     * 加载数据为空
     */
    protected void loadDataEmpty() {
        gifView.setVisibility(View.GONE);
        gifView.pause();
        ivReload.setVisibility(View.GONE);
        loadText.setText(getString(R.string.loading_empty));
        loadText.setGravity(Gravity.CENTER);
    }

    /**
     * 加载数据失败
     */
    private boolean isReload = false;

    protected void loadDataError() {
        isReload = false;
        gifView.setVisibility(View.GONE);
        gifView.pause();
        ivReload.setVisibility(View.VISIBLE);
        loadText.setText(getString(R.string.loading_failed));
        layoutReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isReload) {
                    isReload = true;
                    reload();
                }
            }
        });
    }

    /**
     * 重新请求数据
     */
    protected void reload() {
        ivReload.setVisibility(View.GONE);
        loadText.setText(getResources().getString(R.string.loading_content));
        gifView.setVisibility(View.VISIBLE);
        gifView.play();
        queryData();
    }

    /**
     * 默认导航栏样式
     *
     * @param title
     */
    protected void setNavigationBarStyle(String title) {
        setNavigationBarStyle(title, "");
    }


    /**
     * 设置导航栏显示样式
     *
     * @param title
     * @param right 只能传文本或者图片的Resource Id
     */
    public void setNavigationBarStyle(String title, Object right) {
        if ("".equals(right) || null == right) {
            initDefaultNavigationBar(title);
        } else {
            if (right instanceof String) {
                initDefaultNavigationBar1(title, (String) right);
            } else if (right instanceof Integer) {
                initDefaultNavigationBar2(title, (Integer) right);
            } else {
                Toast.makeText(this, "导航栏右边按钮只能设置文字和图片", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 默认导航栏 右边不显示
     *
     * @param title
     */
    private void initDefaultNavigationBar(String title) {
        defaultNavigationBar = new DefaultNavigationBar.Builder(this, viewGroup)
                .setTitleText(title)
                .setBackClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        titleBackButtonListener(v);
                    }
                })
                .create();
    }

    /**
     * 导航栏右边显示文本
     *
     * @param title
     * @param rightText
     */
    private void initDefaultNavigationBar1(String title, String rightText) {
        navigationBarRightText = new Default1NavigationBar.Builder(this, viewGroup)
                .setTitleText(title)
                .setBackClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        titleBackButtonListener(v);
                    }
                })
                .setRightText(rightText)
                .setRightTextClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        titleRightButtonListener();
                    }
                })
                .create();
    }

    /**
     * 导航栏右边显示图片
     *
     * @param title
     * @param rightImageId
     */
    private void initDefaultNavigationBar2(String title, int rightImageId) {
        navigationBarRightImage = new Default2NavigationBar.Builder(this, viewGroup)
                .setTitleText(title)
                .setBackClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        titleBackButtonListener(v);
                    }
                })
                .setRightImageId(rightImageId)
                .setRightImageClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        titleRightButtonListener();
                    }
                })
                .create();
    }


    /**
     * 标题栏左边关闭控件监听响应
     */
    protected void titleBackButtonListener(View view) {
        inputMethod(this, false, view);
        finish();
    }

    /**
     * 标题栏右边按钮监听响应
     */
    protected void titleRightButtonListener() {

    }

    /**
     * 设置是否需要加载loading界面
     */
    protected abstract void needLoading();

    /**
     * 设置布局
     * <p/>
     * 固定格式： layoutId = R.layout.***;
     */
    protected abstract void setLayoutId();

    /**
     * 加载loading界面 发送数据请求
     */
    protected abstract void queryData();

    /**
     * 替代Activity的 onCreate 方法
     */
    protected abstract void onCreate();


    /**
     * 跳转activity
     *
     * @param clazz
     */
    public void startActivity(Class<? extends BaseNormalActivity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }


    /**
     * 隐藏输入法
     *
     * @param context
     * @param isShow
     * @param view
     */
    public static void inputMethod(Context context, boolean isShow, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
        } else {
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
