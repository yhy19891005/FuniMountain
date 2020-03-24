package com.funi.view.bar;

import android.content.Context;
import android.view.ViewGroup;

/**
 * 自定义导航栏
 */
public class NavigationBar extends BaseNavigationBar {

    protected NavigationBar(Builder builder) {
        super(builder);
    }

    /**
     * 导航栏的Builder
     */
    public static class Builder extends BaseNavigationBar.Builder<NavigationBar.Builder>{

        public Builder(Context context, int layoutId, ViewGroup parent) {
            super(context, layoutId, parent);
        }

        @Override
        public NavigationBar create() {
            return new NavigationBar(this);
        }
    }
}
