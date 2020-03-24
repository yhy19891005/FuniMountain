package com.house.enquiry.ui.be_most_new_house_building_search;

import android.content.Context;
import android.widget.ImageView;

import com.business.enquiry.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.house.enquiry.modle.MostNewSearchInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import yin.deng.dyutils.pictureUtils.PicassoUtils;

public class NewHouseAdapter extends BaseQuickAdapter<MostNewSearchInfo.ResultBean.ListBean, BaseViewHolder> {
    Context context;
    List<MostNewSearchInfo.ResultBean.ListBean> houseInfos=new ArrayList<>();
    public NewHouseAdapter(int layoutResId, @Nullable List data,Context context) {
        super(layoutResId, data);
        this.context=context;
        this.houseInfos=data;
    }

    @Override
    protected void convert(BaseViewHolder helper, MostNewSearchInfo.ResultBean.ListBean item) {
        ImageView ivImg = helper.getView(R.id.new_house_iv_pic);
        PicassoUtils.getinstance().LoadImage(context,item.getCoverUrl(),ivImg);
        helper.setText(R.id.new_house_tv_name,item.getProjectName());
        helper.setText(R.id.new_house_tv_code,"预售证号："+item.getCurrentSellNumber());
        helper.setText(R.id.new_house_tv_time,"开盘时间："+ item.getOpenTime());
        helper.setText(R.id.new_house_tv_place,"项目地址："+item.getAddress());
    }
}
