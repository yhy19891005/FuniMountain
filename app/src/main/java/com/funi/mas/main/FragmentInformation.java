package com.funi.mas.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.funi.mas.R;
import com.funi.net.common.IRequestResult;
import com.funi.net.common.JsonUtil;
import com.funi.utils.TextUtil;
import com.funi.utils.ToastUtil;
import com.funi.utils.storage.PreferencesUtils;
import com.house.infomation.activity.HouseInfoActivity;
import com.house.infomation.activity.HouseInfoAdapter;
import com.house.infomation.model.HouseInfo;
import com.house.infomation.model.HouseInformation;
import com.house.infomation.request.HouseInfoRequest;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import yin.deng.dyutils.refresh.SmartRefreshLayout;
import yin.deng.dyutils.refresh.api.RefreshLayout;
import yin.deng.dyutils.refresh.listener.OnLoadmoreListener;
import yin.deng.dyutils.utils.LogUtils;
import yin.deng.dyutils.web.NormalWebAc;
import yin.deng.dyutils.web.WebUrlConfig;

/**
 * FragmentInformation
 *
 * @Description: 房产资讯
 * @Author: pengqiang.zou
 * @CreateDate: 2019-04-08 09:39
 */
public class FragmentInformation extends Fragment implements View.OnClickListener {
    private Context context;
    private TextView tvHouseInformation;
    private TextView tvNotice;
    private TextView tvPolicy;
    private TextView tvGoodFaith;
    private TextView tvWorkGuide;

    private HouseInfoAdapter adapter;
    private ArrayList<HouseInfo> houseInfos = new ArrayList<>();
    private final static String HOUSE_INFO = "informations";

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private int page = 1;
    private int pageSize = 10;
    private String json = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_menu_inforation, container, false);
        context = getActivity();
        init(view);
        return view;
    }

    void init(View view) {
        json = PreferencesUtils.getInstance().getString(HOUSE_INFO, "");
        ArrayList<HouseInfo> list = (ArrayList) JsonUtil.getModelList(json, HouseInfo.class);

        tvHouseInformation = view.findViewById(R.id.tv_house_information);
        tvNotice = view.findViewById(R.id.tv_notice);
        tvPolicy = view.findViewById(R.id.tv_policy);
        tvGoodFaith = view.findViewById(R.id.tv_good_faith);
        tvWorkGuide = view.findViewById(R.id.tv_work_guide);

        tvHouseInformation.setOnClickListener(this);
        tvNotice.setOnClickListener(this);
        tvPolicy.setOnClickListener(this);
        tvGoodFaith.setOnClickListener(this);
        tvWorkGuide.setOnClickListener(this);

        mRefreshLayout = view.findViewById(com.funi.view.R.id.smRf);
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            page = 1;
            queryData();
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                queryData();
            }
        });

        mRefreshLayout.setEnableAutoLoadmore(true);
        if (null != list && list.size() > 0) {
            houseInfos.addAll(list);
        } else {
            mRefreshLayout.autoRefresh();
        }
        mRefreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
        mRecyclerView = view.findViewById(com.funi.view.R.id.rcView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new HouseInfoAdapter(context, houseInfos);
        mRecyclerView.setAdapter(adapter);
        adapter.setItemClickListener((view1, position) -> {
            HouseInfo info = houseInfos.get(position);
            if (null != info) {
                Intent intent = new Intent(context, NormalWebAc.class);
                intent.putExtra(NormalWebAc.TITLE, info.getChannelName() + "详情");
                intent.putExtra(NormalWebAc.URL, WebUrlConfig.HOUSE_INFOMATION_DETAILS_URL+info.getId());
                context.startActivity(intent);
            }
        });
    }

    private void queryData() {
        HouseInfoRequest.queryChannelList(context, 0, page, pageSize, new IRequestResult() {
            @Override
            public void success(String result) {
                HouseInformation information = (HouseInformation) JsonUtil.getModel(result, HouseInformation.class);
                if (null != information) {
                    ArrayList<HouseInfo> infos = information.getList();
                    if (null != infos && infos.size() > 0) {
                        if (page == 1) {
                            houseInfos.clear();
                            PreferencesUtils.getInstance().saveString(HOUSE_INFO, JsonUtil.toJson(infos)).commit();
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
                            LogUtils.d("显示暂无更多内容");
                            mRefreshLayout.setLoadmoreFinished(true);
                        }
                    }
                } else {
                    mRefreshLayout.finishRefresh();
                    mRefreshLayout.finishLoadmore();
                    if (page > 1) {
                        LogUtils.d("显示暂无更多内容");
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
                    ToastUtil.show(context, message);
                }

                page--;
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, HouseInfoActivity.class);
        switch (v.getId()) {
            //房产资讯
            case R.id.tv_house_information:
                intent.putExtra(HouseInfoActivity.TITLE, getString(R.string.house_information));
                intent.putExtra(HouseInfoActivity.CHANNEL_TYPE, 1);
                break;
            //通知公告
            case R.id.tv_notice:
                intent.putExtra(HouseInfoActivity.TITLE, getString(R.string.hi_notice));
                intent.putExtra(HouseInfoActivity.CHANNEL_TYPE, 5);
                break;
            //政策法规
            case R.id.tv_policy:
                intent.putExtra(HouseInfoActivity.TITLE, getString(R.string.hi_policy));
                intent.putExtra(HouseInfoActivity.CHANNEL_TYPE, 3);
                break;
            //诚信榜
            case R.id.tv_good_faith:
                intent.putExtra(HouseInfoActivity.TITLE, getString(R.string.hi_good_faith));
                intent.putExtra(HouseInfoActivity.CHANNEL_TYPE, 2);
                break;
            //办事指南
            case R.id.tv_work_guide:
                intent.putExtra(HouseInfoActivity.TITLE, getString(R.string.hi_work_guide));
                intent.putExtra(HouseInfoActivity.CHANNEL_TYPE, 4);
                break;
        }
        context.startActivity(intent);
    }
}
