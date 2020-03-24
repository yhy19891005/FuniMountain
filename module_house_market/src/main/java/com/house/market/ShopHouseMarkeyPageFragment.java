package com.house.market;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import yin.deng.dyutils.base.fragment.SuperViewPagerBaseFragment;
import yin.deng.dyutils.http.BaseHttpInfo;
import yin.deng.dyutils.utils.LogUtils;

/**
 * Created by Administrator on 2018/5/22.
 * deng yin
 */
public class ShopHouseMarkeyPageFragment extends SuperViewPagerBaseFragment {
    private RelativeLayout rlEmptyRoot;
    private boolean isShopHouse=true;
    private int position=0;
    private HouseMarketListInfo houseMarketListInfo;
    private LinearLayout llFormRoot;
    private TextView tvTypeName;
    private TextView tvShopHouseSaleNumOfDay;
    private TextView tvShopHouseSaleAreaOfDay;
    private TextView tvShopHouseSaleNumOfMonth;
    private TextView tvShopHouseSaleAreaOfMonth;
    private TextView tvShopHouseSaleNumOfYear;
    private TextView tvShopHouseSaleAreaOfYear;
    private TextView tvFamilyHouseSaleNumOfDay;
    private TextView tvFamilyHouseSaleAreaOfDay;
    private TextView tvFamilyHouseSaleNumOfMonth;
    private TextView tvFamilyHouseSaleAreaOfMonth;
    private TextView tvFamilyHouseSaleNumOfYear;
    private TextView tvFamilyHouseSaleAreaOfYear;
    private TextView tvBusinessSaleNumOfDay;
    private TextView tvBusinessSaleAreaOfDay;
    private TextView tvBusinessSaleNumOfMonth;
    private TextView tvBusinessSaleAreaOfMonth;
    private TextView tvBusinessSaleNumOfYear;
    private TextView tvBusinessSaleAreaOfYear;
    private TextView tvTypeOther;
    private TextView tvOtherSaleNumOfDay;
    private TextView tvOtherSaleAreaOfDay;
    private TextView tvOtherSaleNumOfMonth;
    private TextView tvOtherSaleAreaOfMonth;
    private TextView tvOtherSaleNumOfYear;
    private TextView tvOtherSaleAreaOfYear;



    @Override
    protected int setContentView() {
        return R.layout.hm_page_fragment;
    }

    @Override
    public void onActivityMsgToHere(BaseHttpInfo info) {

    }

    @Override
    protected void init() {
        initView();
        houseMarketListInfo= (HouseMarketListInfo) getArguments().getSerializable("info");

    }

    public void needShowEmptyLayout(boolean isNeedShowEmpty){
        if (isNeedShowEmpty) {
            llFormRoot.setVisibility(View.GONE);
            rlEmptyRoot.setVisibility(View.VISIBLE);
        } else {
            llFormRoot.setVisibility(View.VISIBLE);
            rlEmptyRoot.setVisibility(View.GONE);
        }
    }


    /**
     * 根据不同的参数显示不同数据
     */
    public void showDataOnPage(boolean isShopHouse,int position) {
        if(houseMarketListInfo==null){
            return;
        }
        if(isShopHouse) {
            LogUtils.d("改变：商品房");
            changeTypeName("总计");
            showXfHouseUi(houseMarketListInfo.getResult().getXfCountRegion(),houseMarketListInfo.getResult().getXfCountMonth(),houseMarketListInfo.getResult().getXfCountYear(),position);
        }else{
            LogUtils.d("改变：存量房");
            changeTypeName("总计");
            showEsfHouseUi(houseMarketListInfo.getResult().getEsfCountRegion(),houseMarketListInfo.getResult().getEsfCountMonth(),houseMarketListInfo.getResult().getEsfCountYear(),position);
        }
    }

//    {"area":0,"businessArea":0,
//            "businessUnit":0,"regionCode":"340521","regionName":"当涂县"
//            ,"residenceArea":0,"residenceUnit":0,"unit":0}
    //显示商品房数据
    private void showXfHouseUi(List<HouseMarketListInfo.ResultBean.XfCountRegionBean> xfCountRegion,List<HouseMarketListInfo.ResultBean.XfCountMonthBean> xfCountMonth, List<HouseMarketListInfo.ResultBean.XfCountYearBean> xfCountYear, int position) {
        //存量房总计日销售套数
        tvShopHouseSaleNumOfDay.setText(String.valueOf(xfCountRegion.get(position).getUnit()));
        //存量房总计日销售面积
        tvShopHouseSaleAreaOfDay.setText(String.valueOf(xfCountRegion.get(position).getArea()));
        //存量房商业日销售套数
        tvBusinessSaleNumOfDay.setText(String.valueOf(xfCountRegion.get(position).getBusinessUnit()));
        //存量房商业日销售面积
        tvBusinessSaleAreaOfDay.setText(String.valueOf(xfCountRegion.get(position).getBusinessArea()));
        //存量房住宅日销售套数
        tvFamilyHouseSaleNumOfDay.setText(String.valueOf(xfCountRegion.get(position).getResidenceUnit()));
        //存量房住宅日销售面积
        tvFamilyHouseSaleAreaOfDay.setText(String.valueOf(xfCountRegion.get(position).getResidenceArea()));
        //存量房其他日销售套数
        tvOtherSaleNumOfDay.setText(String.valueOf(xfCountRegion.get(position).getOtherUnit()));
        //存量房其他日销售面积
        tvOtherSaleAreaOfDay.setText(String.valueOf(xfCountRegion.get(position).getOtherArea()));

        //存量房总计月销售套数
        tvShopHouseSaleNumOfMonth.setText(String.valueOf(xfCountMonth.get(position).getUnit()));
        //存量房总计月销售面积
        tvShopHouseSaleAreaOfMonth.setText(String.valueOf(xfCountMonth.get(position).getArea()));
        //存量房商业月销售套数
        tvBusinessSaleNumOfMonth.setText(String.valueOf(xfCountMonth.get(position).getBusinessUnit()));
        //存量房商业月销售面积
        tvBusinessSaleAreaOfMonth.setText(String.valueOf(xfCountMonth.get(position).getBusinessArea()));
        //存量房住宅月销售套数
        tvFamilyHouseSaleNumOfMonth.setText(String.valueOf(xfCountMonth.get(position).getResidenceUnit()));
        //存量房住宅月销售面积
        tvFamilyHouseSaleAreaOfMonth.setText(String.valueOf(xfCountMonth.get(position).getResidenceArea()));
        //存量房其他月销售套数
        tvOtherSaleNumOfMonth.setText(String.valueOf(xfCountMonth.get(position).getOtherUnit()));
        //存量房其他月销售面积
        tvOtherSaleAreaOfMonth.setText(String.valueOf(xfCountMonth.get(position).getOtherArea()));


        //存量房总计年销售套数
        tvShopHouseSaleNumOfYear.setText(String.valueOf(xfCountYear.get(position).getUnit()));
        //存量房总计年销售面积
        tvShopHouseSaleAreaOfYear.setText(String.valueOf(xfCountYear.get(position).getArea()));
        //存量房商业年销售套数
        tvBusinessSaleNumOfYear.setText(String.valueOf(xfCountYear.get(position).getBusinessUnit()));
        //存量房商业年销售面积
        tvBusinessSaleAreaOfYear.setText(String.valueOf(xfCountYear.get(position).getBusinessArea()));
        //存量房住宅年销售套数
        tvFamilyHouseSaleNumOfYear.setText(String.valueOf(xfCountYear.get(position).getResidenceUnit()));
        //存量房住宅年销售面积
        tvFamilyHouseSaleAreaOfYear.setText(String.valueOf(xfCountYear.get(position).getResidenceArea()));
        //存量房其他年销售套数
        tvOtherSaleNumOfYear.setText(String.valueOf(xfCountYear.get(position).getOtherUnit()));
        //存量房其他年销售面积
        tvOtherSaleAreaOfYear.setText(String.valueOf(xfCountYear.get(position).getOtherArea()));
    }


    //显示存量房数据
    private void showEsfHouseUi(List<HouseMarketListInfo.ResultBean.EsfCountRegionBean> xfCountRegion,List<HouseMarketListInfo.ResultBean.EsfCountMonthBean> xfCountMonth, List<HouseMarketListInfo.ResultBean.EsfCountYearBean> xfCountYear, int position) {
        //存量房总计日销售套数
        tvShopHouseSaleNumOfDay.setText(String.valueOf(xfCountRegion.get(position).getUnit()));
        //存量房总计日销售面积
        tvShopHouseSaleAreaOfDay.setText(String.valueOf(xfCountRegion.get(position).getArea()));
        //存量房商业日销售套数
        tvBusinessSaleNumOfDay.setText(String.valueOf(xfCountRegion.get(position).getBusinessUnit()));
        //存量房商业日销售面积
        tvBusinessSaleAreaOfDay.setText(String.valueOf(xfCountRegion.get(position).getBusinessArea()));
        //存量房住宅日销售套数
        tvFamilyHouseSaleNumOfDay.setText(String.valueOf(xfCountRegion.get(position).getResidenceUnit()));
        //存量房住宅日销售面积
        tvFamilyHouseSaleAreaOfDay.setText(String.valueOf(xfCountRegion.get(position).getResidenceArea()));
        //存量房其他日销售套数
        tvOtherSaleNumOfDay.setText(String.valueOf(xfCountRegion.get(position).getOtherUnit()));
        //存量房其他日销售面积
        tvOtherSaleAreaOfDay.setText(String.valueOf(xfCountRegion.get(position).getOtherArea()));

        //存量房总计月销售套数
        tvShopHouseSaleNumOfMonth.setText(String.valueOf(xfCountMonth.get(position).getUnit()));
        //存量房总计月销售面积
        tvShopHouseSaleAreaOfMonth.setText(String.valueOf(xfCountMonth.get(position).getArea()));
        //存量房商业月销售套数
        tvBusinessSaleNumOfMonth.setText(String.valueOf(xfCountMonth.get(position).getBusinessUnit()));
        //存量房商业月销售面积
        tvBusinessSaleAreaOfMonth.setText(String.valueOf(xfCountMonth.get(position).getBusinessArea()));
        //存量房住宅月销售套数
        tvFamilyHouseSaleNumOfMonth.setText(String.valueOf(xfCountMonth.get(position).getResidenceUnit()));
        //存量房住宅月销售面积
        tvFamilyHouseSaleAreaOfMonth.setText(String.valueOf(xfCountMonth.get(position).getResidenceArea()));
        //存量房其他月销售套数
        tvOtherSaleNumOfMonth.setText(String.valueOf(xfCountMonth.get(position).getOtherUnit()));
        //存量房其他月销售面积
        tvOtherSaleAreaOfMonth.setText(String.valueOf(xfCountMonth.get(position).getOtherArea()));


        //存量房总计年销售套数
        tvShopHouseSaleNumOfYear.setText(String.valueOf(xfCountYear.get(position).getUnit()));
        //存量房总计年销售面积
        tvShopHouseSaleAreaOfYear.setText(String.valueOf(xfCountYear.get(position).getArea()));
        //存量房商业年销售套数
        tvBusinessSaleNumOfYear.setText(String.valueOf(xfCountYear.get(position).getBusinessUnit()));
        //存量房商业年销售面积
        tvBusinessSaleAreaOfYear.setText(String.valueOf(xfCountYear.get(position).getBusinessArea()));
        //存量房住宅年销售套数
        tvFamilyHouseSaleNumOfYear.setText(String.valueOf(xfCountYear.get(position).getResidenceUnit()));
        //存量房住宅年销售面积
        tvFamilyHouseSaleAreaOfYear.setText(String.valueOf(xfCountYear.get(position).getResidenceArea()));
        //存量房其他年销售套数
        tvOtherSaleNumOfYear.setText(String.valueOf(xfCountYear.get(position).getOtherUnit()));
        //存量房其他年销售面积
        tvOtherSaleAreaOfYear.setText(String.valueOf(xfCountYear.get(position).getOtherArea()));
    }

    public void initView(){
        rlEmptyRoot = getRootView().findViewById(R.id.rl_empty_root);
        llFormRoot = (LinearLayout) getRootView().findViewById(R.id.ll_form_root);
        tvTypeName = (TextView) getRootView().findViewById(R.id.tv_type_name);
        tvShopHouseSaleNumOfDay = (TextView) getRootView().findViewById(R.id.tv_shop_house_sale_num_of_day);
        tvShopHouseSaleAreaOfDay = (TextView) getRootView().findViewById(R.id.tv_shop_house_sale_area_of_day);
        tvShopHouseSaleNumOfMonth = (TextView) getRootView().findViewById(R.id.tv_shop_house_sale_num_of_month);
        tvShopHouseSaleAreaOfMonth = (TextView) getRootView().findViewById(R.id.tv_shop_house_sale_area_of_month);
        tvShopHouseSaleNumOfYear = (TextView) getRootView().findViewById(R.id.tv_shop_house_sale_num_of_year);
        tvShopHouseSaleAreaOfYear = (TextView) getRootView().findViewById(R.id.tv_shop_house_sale_area_of_year);
        tvFamilyHouseSaleNumOfDay = (TextView) getRootView().findViewById(R.id.tv_family_house_sale_num_of_day);
        tvFamilyHouseSaleAreaOfDay = (TextView) getRootView().findViewById(R.id.tv_family_house_sale_area_of_day);
        tvFamilyHouseSaleNumOfMonth = (TextView) getRootView().findViewById(R.id.tv_family_house_sale_num_of_month);
        tvFamilyHouseSaleAreaOfMonth = (TextView) getRootView().findViewById(R.id.tv_family_house_sale_area_of_month);
        tvFamilyHouseSaleNumOfYear = (TextView) getRootView().findViewById(R.id.tv_family_house_sale_num_of_year);
        tvFamilyHouseSaleAreaOfYear = (TextView) getRootView().findViewById(R.id.tv_family_house_sale_area_of_year);
        tvBusinessSaleNumOfDay = (TextView) getRootView().findViewById(R.id.tv_business_sale_num_of_day);
        tvBusinessSaleAreaOfDay = (TextView) getRootView().findViewById(R.id.tv_business_sale_area_of_day);
        tvBusinessSaleNumOfMonth = (TextView) getRootView().findViewById(R.id.tv_business_sale_num_of_month);
        tvBusinessSaleAreaOfMonth = (TextView) getRootView().findViewById(R.id.tv_business_sale_area_of_month);
        tvBusinessSaleNumOfYear = (TextView) getRootView().findViewById(R.id.tv_business_sale_num_of_year);
        tvBusinessSaleAreaOfYear = (TextView) getRootView().findViewById(R.id.tv_business_sale_area_of_year);
        tvTypeOther = (TextView) getRootView().findViewById(R.id.tv_type_other);
        tvOtherSaleNumOfDay = (TextView) getRootView().findViewById(R.id.tv_other_sale_num_of_day);
        tvOtherSaleAreaOfDay = (TextView) getRootView().findViewById(R.id.tv_other_sale_area_of_day);
        tvOtherSaleNumOfMonth = (TextView) getRootView().findViewById(R.id.tv_other_sale_num_of_month);
        tvOtherSaleAreaOfMonth = (TextView) getRootView().findViewById(R.id.tv_other_sale_area_of_month);
        tvOtherSaleNumOfYear = (TextView) getRootView().findViewById(R.id.tv_other_sale_num_of_year);
        tvOtherSaleAreaOfYear = (TextView) getRootView().findViewById(R.id.tv_other_sale_area_of_year);
    }

    public void changeTypeName(String name){
        tvTypeName=getRootView().findViewById(R.id.tv_type_name);
        if(tvTypeName!=null){
            LogUtils.d("改变名字成功");
            tvTypeName.setText(name);
        }else{
            LogUtils.d("改变名字失败");
        }
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
    }

}
