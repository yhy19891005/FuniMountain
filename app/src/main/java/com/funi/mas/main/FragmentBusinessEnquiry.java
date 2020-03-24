package com.funi.mas.main;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.funi.mas.R;
import com.house.enquiry.ui.be_most_new_house_building_search.MostNewHouseBuildingSearchAc;
import com.house.enquiry.ui.be_occu_main_part.BeOccuMainPartOutAc;
import com.house.enquiry.ui.be_re_certify_search.BeReCertifySearchAc;
import com.house.enquiry.ui.be_save_house_sale_search.SaveHouseSaleSearchAc;
import com.house.enquiry.ui.be_shop_house_sale_search.ShopHouseSaleSearchAc;

import yin.deng.dyutils.base.fragment.SuperViewPagerBaseFragment;
import yin.deng.dyutils.http.BaseHttpInfo;
import yin.deng.dyutils.utils.NoDoubleClickListener;

/**
 * FragmentHouseMarket
 *
 * @Description: 业务查询
 * @Author: pengqiang.zou
 * @CreateDate: 2019-04-08 09:46
 */
public class FragmentBusinessEnquiry extends SuperViewPagerBaseFragment {

    private LinearLayout llBeNewHouse;
    private LinearLayout llBeShopHouseSearch;
    private LinearLayout llBeSaveHouseSearch;
    private LinearLayout llBeReSaleCertifySearch;
    private LinearLayout llBeOccuMainPartSearch;

    @Override
    protected int setContentView() {
        return R.layout.view_menu_business_enquiry;
    }

    @Override
    public void onActivityMsgToHere(BaseHttpInfo info) {

    }


    @Override
    public void bindViewWithId(View view) {
        llBeNewHouse=view.findViewById(R.id.ll_be_new_house);
        llBeShopHouseSearch=view.findViewById(R.id.ll_be_shop_house_search);
        llBeOccuMainPartSearch=view.findViewById(R.id.ll_be_occu_main_part_search);
        llBeSaveHouseSearch=view.findViewById(R.id.ll_be_save_house_search);
        llBeReSaleCertifySearch=view.findViewById(R.id.ll_be_re_sale_certify_search);
    }

    @Override
    protected void init() {
        llBeNewHouse.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                startActivity(new Intent(getActivity(), MostNewHouseBuildingSearchAc.class));
            }
        });
        llBeShopHouseSearch.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                startActivity(new Intent(getActivity(), ShopHouseSaleSearchAc.class));
            }
        });
        llBeSaveHouseSearch.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                startActivity(new Intent(getActivity(), SaveHouseSaleSearchAc.class));
            }
        });
        llBeReSaleCertifySearch.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
               startActivity(new Intent(getActivity(), BeReCertifySearchAc.class));
            }
        });
        llBeOccuMainPartSearch.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                startActivity(new Intent(getActivity(), BeOccuMainPartOutAc.class));
            }
        });
    }
}
