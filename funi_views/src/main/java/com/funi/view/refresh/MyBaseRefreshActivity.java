package com.funi.view.refresh;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.funi.view.R;
import com.funi.view.activity.BaseActivity;
import com.funi.view.bar.Default1NavigationBar;
import com.funi.view.bar.Default2NavigationBar;
import com.funi.view.bar.DefaultNavigationBar;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import yin.deng.dyutils.refresh.SmartRefreshLayout;
import yin.deng.dyutils.refresh.listener.OnLoadmoreListener;
import yin.deng.dyutils.refresh.listener.OnRefreshListener;

/**
 * BaseRefreshActivity
 *
 * @Description:下拉刷新activity
 * @Author: pengqiang.zou
 * @CreateDate: 2018-12-03 10:46
 */
public abstract class MyBaseRefreshActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {
    private ViewGroup viewGroup;//用来添加导航栏
    private FrameLayout layoutContent;
    //默认导航栏
    public DefaultNavigationBar defaultNavigationBar;
    public Default1NavigationBar navigationBarRightText;
    public Default2NavigationBar navigationBarRightImage;

    public RecyclerView mRecyclerView;
    public int page = 0;
    public int pageSize = 10;
    public SmartRefreshLayout mRefreshLayout = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_normal);

        viewGroup = findViewById(R.id.view_group);
        layoutContent = findViewById(R.id.layout_content);

        setLayoutId();
        setLayoutView();//不显示加载进度条
        initView();
        onCreate();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mRefreshLayout = findViewById(R.id.smRf);
        mRecyclerView = findViewById(R.id.rcView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (mRefreshLayout != null) {
            mRefreshLayout.setOnRefreshListener(this);
            mRefreshLayout.setOnLoadmoreListener(this);
            mRefreshLayout.setEnableAutoLoadmore(true);
            mRefreshLayout.autoRefresh();
            mRefreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
        }
    }

    /**
     * 直接显示界面
     */
    protected void setLayoutView() {
        View view = LayoutInflater.from(this).inflate(layoutId, null);
        layoutContent.addView(view);
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
     * 设置布局
     * <p/>
     * 固定格式： layoutId = R.layout.***;
     */
    protected abstract void setLayoutId();

    /**
     * 替代Activity的 onCreate 方法
     */
    protected abstract void onCreate();

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
