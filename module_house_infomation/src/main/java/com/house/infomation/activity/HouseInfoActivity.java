package com.house.infomation.activity;

import android.content.Intent;
import android.view.View;

import com.funi.net.common.IRequestResult;
import com.funi.net.common.JsonUtil;
import com.funi.utils.FuniLog;
import com.funi.utils.TextUtil;
import com.funi.utils.ToastUtil;
import com.funi.view.OnItemClickListener;
import com.funi.view.refresh.MyBaseRefreshActivity;
import com.house.infomation.R;
import com.house.infomation.model.HouseInfo;
import com.house.infomation.model.HouseInformation;
import com.house.infomation.request.HouseInfoRequest;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import yin.deng.dyutils.refresh.api.RefreshLayout;
import yin.deng.dyutils.web.NormalWebAc;
import yin.deng.dyutils.web.WebUrlConfig;

/**
 * TestActivity
 *
 * @Description:
 * @Author: pengqiang.zou
 * @CreateDate: 2019-04-10 12:01
 */
public class HouseInfoActivity extends MyBaseRefreshActivity {
    @Override
    protected void setLayoutId() {
        layoutId = R.layout.smart_layout_rc;
    }

    private HouseInfoAdapter adapter;
    private ArrayList<HouseInfo> houseInfos = new ArrayList<>();

    public static final String CHANNEL_TYPE = "channelType";
    public static final String TITLE = "barTitle";

    //房产资讯 1
    //诚信榜 2
    //政策法规 3
    //办事指南 4
    //通知公告 5
    private int channelType = 1;
    private String title = "";

    @Override
    protected void onCreate() {
        title = getIntent().getStringExtra(TITLE);
        setNavigationBarStyle(title);
        channelType = getIntent().getIntExtra(CHANNEL_TYPE, 1);

        adapter = new HouseInfoAdapter(this, houseInfos);
        mRecyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                HouseInfo houseInfo = houseInfos.get(position);
                if (null != houseInfo) {
                    Intent intent = new Intent(HouseInfoActivity.this, NormalWebAc.class);
                    intent.putExtra(NormalWebAc.TITLE, title + "详情");
                    FuniLog.d("url:"+houseInfo.getUrl());
                    intent.putExtra(NormalWebAc.URL, WebUrlConfig.HOUSE_INFOMATION_DETAILS_URL+houseInfo.getId());
                    startActivity(intent);
                }
            }
        });
    }

    private void queryData() {
        HouseInfoRequest.queryChannelList(HouseInfoActivity.this, channelType, page, pageSize, new IRequestResult() {
            @Override
            public void success(String result) {
                HouseInformation information = (HouseInformation) JsonUtil.getModel(result, HouseInformation.class);
                if (null != information) {
                    ArrayList<HouseInfo> infos = information.getList();
                    if (null != infos && infos.size() > 0) {
                        if (page == 1) {
                            houseInfos.clear();
                        }

                        houseInfos.addAll(infos);
                        adapter.refreshData(houseInfos);
                        if (page == 1) {
                            mRefreshLayout.finishRefresh();
                        } else {
                            mRefreshLayout.finishLoadmore();
                        }
                    } else {
                        mRefreshLayout.finishLoadmore();
                        if (page > 1) {
                            mRefreshLayout.setLoadmoreFinished(true);
                        }
                    }
                } else {
                    mRefreshLayout.finishLoadmore();
                    if (page > 1) {
                        mRefreshLayout.setLoadmoreFinished(true);
                    }
                }
            }

            @Override
            public void failure(String message) {
                if (page == 1) {
                    mRefreshLayout.finishRefresh();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
                if (!TextUtil.stringIsNull(message)) {
                    ToastUtil.show(HouseInfoActivity.this, message);
                }

                page--;
            }
        });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        queryData();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        queryData();
    }
}
