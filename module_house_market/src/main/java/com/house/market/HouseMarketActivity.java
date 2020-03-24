package com.house.market;

import com.funi.view.activity.BaseNormalActivity;

/**
 * HouseMarketActivity
 *
 * @Description: 楼市行情
 * @Author: pengqiang.zou
 * @CreateDate: 2019-04-03 17:20
 */
public class HouseMarketActivity extends BaseNormalActivity {
    @Override
    protected void needLoading() {

    }

    @Override
    protected void setLayoutId() {
        layoutId = R.layout.hm_house_market_activity;
    }

    @Override
    protected void queryData() {

    }

    @Override
    protected void onCreate() {
        setNavigationBarStyle(getString(R.string.hm_house_market));
    }
}
