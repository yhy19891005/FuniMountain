package com.funi.view.bar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.funi.view.R;


/**
 * DefaultNavigationBar
 *
 * @Description:默认的导航栏只包含返回键、中间的标题、右边文字
 * @Author: pengqiang.zou
 * @CreateDate: 2018-11-30 10:46
 */
public class Default1NavigationBar extends BaseNavigationBar<Default1NavigationBar.Builder> {

    protected Default1NavigationBar(Builder builder) {
        super(builder);
    }

    public static class Builder extends BaseNavigationBar.Builder<Default1NavigationBar.Builder> {

        public Builder(Context context, ViewGroup parent) {
            super(context, R.layout.view_defualt_navigation_bar_1, parent);
        }

        @Override
        public Default1NavigationBar create() {
            return new Default1NavigationBar(this);
        }


        public Builder setTitleText(String text) {
            setText(R.id.title_tv, text);
            return this;
        }

        public Builder setBackClickListener(View.OnClickListener clickListener) {
            setOnClickListener(R.id.layout_back, clickListener);
            return this;
        }

        public Builder setRightText(String text) {
            setText(R.id.right_tv, text);
            return this;
        }

        public Builder setRightTextClickListener(View.OnClickListener clickListener) {
            setOnClickListener(R.id.right_tv, clickListener);
            return this;
        }
    }
}
