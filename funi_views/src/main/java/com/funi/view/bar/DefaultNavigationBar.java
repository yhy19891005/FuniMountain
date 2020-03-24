package com.funi.view.bar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.funi.view.R;


/**
 * DefaultNavigationBar
 *
 * @Description:默认的导航栏只包含返回键和中间的标题
 * @Author: pengqiang.zou
 * @CreateDate: 2018-11-30 10:46
 */
public class DefaultNavigationBar extends BaseNavigationBar<DefaultNavigationBar.Builder> {

    protected DefaultNavigationBar(Builder builder) {
        super(builder);
    }

    public static class Builder extends BaseNavigationBar.Builder<DefaultNavigationBar.Builder> {

        public Builder(Context context, ViewGroup parent) {
            super(context, R.layout.view_defualt_navigation_bar, parent);
        }

        @Override
        public DefaultNavigationBar create() {
            return new DefaultNavigationBar(this);
        }


        public Builder setTitleText(String text) {
            setText(R.id.title_tv, text);
            return this;
        }

        public Builder setBackClickListener(View.OnClickListener clickListener) {
            setOnClickListener(R.id.layout_back, clickListener);
            return this;
        }
    }
}
