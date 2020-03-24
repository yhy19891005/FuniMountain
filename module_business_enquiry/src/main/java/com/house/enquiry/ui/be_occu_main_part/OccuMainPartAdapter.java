package com.house.enquiry.ui.be_occu_main_part;

import android.content.Context;

import com.business.enquiry.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.house.enquiry.modle.OccMainPartHouseInfoList;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class OccuMainPartAdapter extends BaseQuickAdapter<OccMainPartHouseInfoList.ResultBean.ListBean, BaseViewHolder> {
    Context context;
    List<OccMainPartHouseInfoList.ResultBean.ListBean> houseInfos=new ArrayList<>();
    public OccuMainPartAdapter(int layoutResId, @Nullable List data, Context context) {
        super(layoutResId, data);
        this.context=context;
        this.houseInfos=data;
    }

    @Override
    protected void convert(BaseViewHolder helper, OccMainPartHouseInfoList.ResultBean.ListBean item) {
        helper.setText(R.id.occ_tv_house_name,item.getOrganizationName());
        helper.setText(R.id.occ_tv_house_count,item.getContractInformation());
        helper.setText(R.id.occ_tv_house_place,item.getAddress());
        helper.setText(R.id.occ_tv_house_type,item.getOrganizationType());
        helper.setText(R.id.occ_tv_sign_status,item.getNetSignStatus());
    }
}
