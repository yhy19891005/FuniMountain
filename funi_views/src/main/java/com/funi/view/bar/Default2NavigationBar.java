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
public class Default2NavigationBar extends BaseNavigationBar<Default2NavigationBar.Builder> {

    protected Default2NavigationBar(Builder builder) {
        super(builder);
    }

    public static class Builder extends BaseNavigationBar.Builder<Default2NavigationBar.Builder> {

        public Builder(Context context, ViewGroup parent) {
            super(context, R.layout.view_defualt_navigation_bar_2, parent);
        }

        @Override
        public Default2NavigationBar create() {
            return new Default2NavigationBar(this);
        }


        public Builder setTitleText(String text) {
            setText(R.id.title_tv, text);
            return this;
        }

        public Builder setBackClickListener(View.OnClickListener clickListener) {
            setOnClickListener(R.id.layout_back, clickListener);
            return this;
        }

        public Builder setRightImageId(int id) {
            setImageResource(R.id.right_iv, id);
            return this;
        }

        public Builder setRightImageClickListener(View.OnClickListener clickListener) {
            setOnClickListener(R.id.right_iv, clickListener);
            return this;
        }
    }
}
