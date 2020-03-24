package com.funi.mas.main;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cunoraz.gifview.library.GifView;
import com.funi.mas.R;
import com.funi.net.common.IRequestResult;
import com.funi.net.okhttp.OKHttpRequest;
import com.google.gson.Gson;
import com.house.market.HouseMarketListInfo;
import com.house.market.ShopHouseMarkeyPageFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import yin.deng.dyutils.base.fragment.SuperViewPagerBaseFragment;
import yin.deng.dyutils.config.NetConfig;
import yin.deng.dyutils.http.BaseHttpInfo;
import yin.deng.dyutils.utils.LogUtils;
import yin.deng.dyutils.utils.NoDoubleClickListener;

/**
 * FragmentHouseMarket
 *
 * @Description: 楼市行情
 * @Author: pengqiang.zou
 * @CreateDate: 2019-04-08 09:46
 */
public class FragmentHouseMarket extends SuperViewPagerBaseFragment {
    private SmartTabLayout tabLayout;
    private ViewPager viewpager;
    private Context context;
    private TextView tvShopHouse;
    private TextView tvSaveHouse;
    private ShopHouseMarkeyPageFragment currentFragment;
    private boolean isFirst = true;
    private FrameLayout fmLoading;
    private GifView gifView;
    private RelativeLayout rlEmptyRoot;
    private ImageView ivEmptyImg;
    private HouseMarketListInfo houseMarketListInfo;
    private int currentPage = 0;

    @Override
    protected int setContentView() {
        return R.layout.view_menu_house_market;
    }

    @Override
    public void onActivityMsgToHere(BaseHttpInfo info) {

    }

    private void initView(View view) {
        tabLayout = view.findViewById(R.id.hm_tab_layout);
        viewpager = view.findViewById(R.id.viewpager);
        tvShopHouse = view.findViewById(R.id.tv_shop_house);
        tvSaveHouse = view.findViewById(R.id.tv_save_house);
        rlEmptyRoot = getRootView().findViewById(R.id.rl_empty_root);
        ivEmptyImg = getRootView().findViewById(R.id.iv_empty_img);
        fmLoading = getRootView().findViewById(R.id.fm_loading);
        gifView = getRootView().findViewById(R.id.gif_view);
        fmLoading.setVisibility(View.VISIBLE);
        gifView.play();
    }

    @Override
    public void init() {
        initView(getRootView());
        context = getActivity();
        initAll();
    }

    @Override
    protected void onFragmentFirstVisible() {
        initAll();
    }

    private void initAll() {
        LogUtils.e("执行initAll");
        rlEmptyRoot.setVisibility(View.GONE);
        if (houseMarketListInfo != null && !isAddViewOver) {
            LogUtils.e("执行加载");
            String[] names = getTabNames(houseMarketListInfo);
            //初始化tab，绑定单个fragment
            initPageFg(names);
            //设置上方两个小tab的切换监听
            initSwitchMainTab();
            fmLoading.setVisibility(View.GONE);
        } else if (houseMarketListInfo == null) {
            LogUtils.e("执行请求");
            OKHttpRequest.get(getActivity(), NetConfig.BUILDING_MARKET_LIST_URL, null, new IRequestResult() {
                @Override
                public void success(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("message","");
                        jsonObject.put("result",new JSONObject(result));
                        jsonObject.put("success",true);

                        houseMarketListInfo = new Gson().fromJson(jsonObject.toString().replaceAll("\\\\",""), HouseMarketListInfo.class);
                        if (houseMarketListInfo != null && houseMarketListInfo.getResult() != null) {
                            HouseMarketListInfo.ResultBean.XfCountRegionBean xfCountRegionBean = initD();
                            HouseMarketListInfo.ResultBean.XfCountMonthBean xfCountMonthBean = initM();
                            HouseMarketListInfo.ResultBean.XfCountYearBean xfCountYearBean = initY();
                            HouseMarketListInfo.ResultBean.EsfCountRegionBean esfCountRegionBean = initEsD();
                            HouseMarketListInfo.ResultBean.EsfCountMonthBean esfCountMonthBean = initEsM();
                            HouseMarketListInfo.ResultBean.EsfCountYearBean esfCountYearBean = initEsY();
                            houseMarketListInfo.getResult().getXfCountRegion().add(0, xfCountRegionBean);
                            houseMarketListInfo.getResult().getXfCountMonth().add(0, xfCountMonthBean);
                            houseMarketListInfo.getResult().getXfCountYear().add(0, xfCountYearBean);
                            houseMarketListInfo.getResult().getEsfCountRegion().add(0, esfCountRegionBean);
                            houseMarketListInfo.getResult().getEsfCountMonth().add(0, esfCountMonthBean);
                            houseMarketListInfo.getResult().getEsfCountYear().add(0, esfCountYearBean);
                        }

                        String[] names = getTabNames(houseMarketListInfo);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initPageFg(names);
                                //设置上方两个小tab的切换监听
                                initSwitchMainTab();
                                fmLoading.setVisibility(View.GONE);
                                LogUtils.e("执行加载界面");
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void failure(String message) {
                    getActivity().runOnUiThread(() -> {
                        fmLoading.setVisibility(View.GONE);
                        rlEmptyRoot.setVisibility(View.VISIBLE);
                        rlEmptyRoot.setOnClickListener(new NoDoubleClickListener() {
                            @Override
                            protected void onNoDoubleClick(View v) {
                                initAll();
                            }
                        });
                        LogUtils.e("执行请求-失败了 " + message);
                    });
                }
            });

            //getHttpUtils().sendMsgGet(NetConfig.BUILDING_MARKET_LIST_URL, null, new Callback() {
            //    @Override
            //    public void onSuccess(HttpInfo info) throws IOException {
            //        String data = info.getRetDetail();
            //        getHttpUtils().initSucessLog(info, true);
            //        try {
            //            houseMarketListInfo = new Gson().fromJson(data, HouseMarketListInfo.class);
            //            if (houseMarketListInfo != null && houseMarketListInfo.getResult() != null) {
            //                HouseMarketListInfo.ResultBean.XfCountRegionBean xfCountRegionBean = initD();
            //                HouseMarketListInfo.ResultBean.XfCountMonthBean xfCountMonthBean = initM();
            //                HouseMarketListInfo.ResultBean.XfCountYearBean xfCountYearBean = initY();
            //                HouseMarketListInfo.ResultBean.EsfCountRegionBean esfCountRegionBean = initEsD();
            //                HouseMarketListInfo.ResultBean.EsfCountMonthBean esfCountMonthBean = initEsM();
            //                HouseMarketListInfo.ResultBean.EsfCountYearBean esfCountYearBean = initEsY();
            //                houseMarketListInfo.getResult().getXfCountRegion().add(0, xfCountRegionBean);
            //                houseMarketListInfo.getResult().getXfCountMonth().add(0, xfCountMonthBean);
            //                houseMarketListInfo.getResult().getXfCountYear().add(0, xfCountYearBean);
            //                houseMarketListInfo.getResult().getEsfCountRegion().add(0, esfCountRegionBean);
            //                houseMarketListInfo.getResult().getEsfCountMonth().add(0, esfCountMonthBean);
            //                houseMarketListInfo.getResult().getEsfCountYear().add(0, esfCountYearBean);
            //            }
            //        } catch (Exception e) {
            //            e.printStackTrace();
            //        }
//          //          houseMarketListInfo= (HouseMarketListInfo) JsonUtil.getModel(data,HouseMarketListInfo.class);
            //        //初始化tab，绑定单个fragment
            //        String[] names = getTabNames(houseMarketListInfo);
            //        getActivity().runOnUiThread(new Runnable() {
            //            @Override
            //            public void run() {
            //                initPageFg(names);
            //                //设置上方两个小tab的切换监听
            //                initSwitchMainTab();
            //                fmLoading.setVisibility(View.GONE);
            //                LogUtils.e("执行加载界面");
            //            }
            //        });
            //    }
//
            //    @Override
            //    public void onFailure(HttpInfo info) throws IOException {
            //        getHttpUtils().initSucessLog(info, false);
            //        getActivity().runOnUiThread(() -> {
            //            fmLoading.setVisibility(View.GONE);
            //            rlEmptyRoot.setVisibility(View.VISIBLE);
            //            rlEmptyRoot.setOnClickListener(new NoDoubleClickListener() {
            //                @Override
            //                protected void onNoDoubleClick(View v) {
            //                    initAll();
            //                }
            //            });
            //            LogUtils.e("执行请求-失败了");
            //        });
            //    }
            //});
        }
    }

    private HouseMarketListInfo.ResultBean.XfCountMonthBean initM() {
        HouseMarketListInfo.ResultBean.XfCountMonthBean xfCountRegionBean = new HouseMarketListInfo.ResultBean.XfCountMonthBean();
        int allUnit = 0;
        double areaAll = 0;
        int residenceUnitAll = 0;
        double residenceAreaAll = 0;
        int businessUnitAll = 0;
        double businessAreaAll = 0;
        int otherUnitAll = 0;
        double otherAreaAll = 0;
        List<HouseMarketListInfo.ResultBean.XfCountMonthBean> list = houseMarketListInfo.getResult().getXfCountMonth();
        for (int i = 0; i < list.size(); i++) {
            allUnit += list.get(i).getUnit();
            areaAll = getAddDouble(areaAll, list.get(i).getArea());
            residenceUnitAll += list.get(i).getResidenceUnit();
            residenceAreaAll = getAddDouble(residenceAreaAll, list.get(i).getResidenceArea());
            businessUnitAll += list.get(i).getBusinessUnit();
            businessAreaAll = getAddDouble(businessAreaAll, list.get(i).getBusinessArea());
            otherUnitAll += list.get(i).getOtherUnit();
            otherAreaAll = getAddDouble(otherAreaAll, list.get(i).getOtherArea());
        }
        xfCountRegionBean.setOtherUnit(otherUnitAll);
        xfCountRegionBean.setOtherArea(otherAreaAll);
        xfCountRegionBean.setUnit(allUnit);
        xfCountRegionBean.setArea(areaAll);
        xfCountRegionBean.setResidenceUnit(residenceUnitAll);
        xfCountRegionBean.setResidenceArea(residenceAreaAll);
        xfCountRegionBean.setBusinessUnit(businessUnitAll);
        xfCountRegionBean.setBusinessArea(businessAreaAll);
        xfCountRegionBean.setRegionName("全市");
        return xfCountRegionBean;
    }

    private HouseMarketListInfo.ResultBean.XfCountRegionBean initD() {
        HouseMarketListInfo.ResultBean.XfCountRegionBean xfCountRegionBean = new HouseMarketListInfo.ResultBean.XfCountRegionBean();
        int allUnit = 0;
        double areaAll = 0;
        int residenceUnitAll = 0;
        double residenceAreaAll = 0;
        int businessUnitAll = 0;
        double businessAreaAll = 0;
        int otherUnitAll = 0;
        double otherAreaAll = 0;
        List<HouseMarketListInfo.ResultBean.XfCountRegionBean> list = houseMarketListInfo.getResult().getXfCountRegion();
        for (int i = 0; i < list.size(); i++) {
            allUnit += list.get(i).getUnit();
            areaAll = getAddDouble(areaAll, list.get(i).getArea());
            residenceUnitAll += list.get(i).getResidenceUnit();
            residenceAreaAll = getAddDouble(residenceAreaAll, list.get(i).getResidenceArea());
            businessUnitAll += list.get(i).getBusinessUnit();
            businessAreaAll = getAddDouble(businessAreaAll, list.get(i).getBusinessArea());
            otherUnitAll += list.get(i).getOtherUnit();
            otherAreaAll = getAddDouble(otherAreaAll, list.get(i).getOtherArea());
        }
        xfCountRegionBean.setOtherUnit(otherUnitAll);
        xfCountRegionBean.setOtherArea(otherAreaAll);
        xfCountRegionBean.setUnit(allUnit);
        xfCountRegionBean.setArea(areaAll);
        xfCountRegionBean.setResidenceUnit(residenceUnitAll);
        xfCountRegionBean.setResidenceArea(residenceAreaAll);
        xfCountRegionBean.setBusinessUnit(businessUnitAll);
        xfCountRegionBean.setBusinessArea(businessAreaAll);
        xfCountRegionBean.setRegionName("全市");
        return xfCountRegionBean;
    }

    private HouseMarketListInfo.ResultBean.EsfCountRegionBean initEsD() {
        HouseMarketListInfo.ResultBean.EsfCountRegionBean xfCountRegionBean = new HouseMarketListInfo.ResultBean.EsfCountRegionBean();
        int allUnit = 0;
        double areaAll = 0;
        int residenceUnitAll = 0;
        double residenceAreaAll = 0;
        int businessUnitAll = 0;
        double businessAreaAll = 0;
        int otherUnitAll = 0;
        double otherAreaAll = 0;
        List<HouseMarketListInfo.ResultBean.EsfCountRegionBean> list = houseMarketListInfo.getResult().getEsfCountRegion();
        for (int i = 0; i < list.size(); i++) {
            allUnit += list.get(i).getUnit();
            areaAll = getAddDouble(areaAll, list.get(i).getArea());
            residenceUnitAll += list.get(i).getResidenceUnit();
            residenceAreaAll = getAddDouble(residenceAreaAll, list.get(i).getResidenceArea());
            businessUnitAll += list.get(i).getBusinessUnit();
            businessAreaAll = getAddDouble(businessAreaAll, list.get(i).getBusinessArea());
            otherUnitAll += list.get(i).getOtherUnit();
            otherAreaAll = getAddDouble(otherAreaAll, list.get(i).getOtherArea());
        }
        xfCountRegionBean.setOtherUnit(otherUnitAll);
        xfCountRegionBean.setOtherArea(otherAreaAll);
        xfCountRegionBean.setUnit(allUnit);
        xfCountRegionBean.setArea(areaAll);
        xfCountRegionBean.setResidenceUnit(residenceUnitAll);
        xfCountRegionBean.setResidenceArea(residenceAreaAll);
        xfCountRegionBean.setBusinessUnit(businessUnitAll);
        xfCountRegionBean.setBusinessArea(businessAreaAll);
        xfCountRegionBean.setRegionName("全市");
        return xfCountRegionBean;
    }

    private HouseMarketListInfo.ResultBean.EsfCountMonthBean initEsM() {
        HouseMarketListInfo.ResultBean.EsfCountMonthBean xfCountRegionBean = new HouseMarketListInfo.ResultBean.EsfCountMonthBean();
        int allUnit = 0;
        double areaAll = 0;
        int residenceUnitAll = 0;
        double residenceAreaAll = 0;
        int businessUnitAll = 0;
        double businessAreaAll = 0;
        int otherUnitAll = 0;
        double otherAreaAll = 0;
        List<HouseMarketListInfo.ResultBean.EsfCountMonthBean> list = houseMarketListInfo.getResult().getEsfCountMonth();
        for (int i = 0; i < list.size(); i++) {
            allUnit += list.get(i).getUnit();
            areaAll = getAddDouble(areaAll, list.get(i).getArea());
            residenceUnitAll += list.get(i).getResidenceUnit();
            residenceAreaAll = getAddDouble(residenceAreaAll, list.get(i).getResidenceArea());
            businessUnitAll += list.get(i).getBusinessUnit();
            businessAreaAll = getAddDouble(businessAreaAll, list.get(i).getBusinessArea());
            otherUnitAll += list.get(i).getOtherUnit();
            otherAreaAll = getAddDouble(otherAreaAll, list.get(i).getOtherArea());
        }
        xfCountRegionBean.setOtherUnit(otherUnitAll);
        xfCountRegionBean.setOtherArea(otherAreaAll);
        xfCountRegionBean.setUnit(allUnit);
        xfCountRegionBean.setArea(areaAll);
        xfCountRegionBean.setResidenceUnit(residenceUnitAll);
        xfCountRegionBean.setResidenceArea(residenceAreaAll);
        xfCountRegionBean.setBusinessUnit(businessUnitAll);
        xfCountRegionBean.setBusinessArea(businessAreaAll);
        xfCountRegionBean.setRegionName("全市");
        return xfCountRegionBean;
    }

    private HouseMarketListInfo.ResultBean.XfCountYearBean initY() {
        HouseMarketListInfo.ResultBean.XfCountYearBean xfCountYearBean = new HouseMarketListInfo.ResultBean.XfCountYearBean();
        int allUnitY = 0;
        double areaAllY = 0;
        int residenceUnitAllY = 0;
        double residenceAreaAllY = 0;
        int businessUnitAllY = 0;
        double businessAreaAllY = 0;
        int otherUnitAll = 0;
        double otherAreaAll = 0;
        List<HouseMarketListInfo.ResultBean.XfCountYearBean> listY = houseMarketListInfo.getResult().getXfCountYear();
        for (int i = 0; i < listY.size(); i++) {
            allUnitY += listY.get(i).getUnit();
            areaAllY = getAddDouble(areaAllY, listY.get(i).getArea());
            residenceUnitAllY += listY.get(i).getResidenceUnit();
            residenceAreaAllY = getAddDouble(residenceAreaAllY, listY.get(i).getResidenceArea());
            businessUnitAllY += listY.get(i).getBusinessUnit();
            businessAreaAllY = getAddDouble(businessAreaAllY, listY.get(i).getBusinessArea());
            otherUnitAll += listY.get(i).getOtherUnit();
            otherAreaAll = getAddDouble(otherAreaAll, listY.get(i).getOtherArea());
        }
        xfCountYearBean.setOtherUnit(otherUnitAll);
        xfCountYearBean.setOtherArea(otherAreaAll);
        xfCountYearBean.setUnit(allUnitY);
        xfCountYearBean.setArea(areaAllY);
        xfCountYearBean.setResidenceUnit(residenceUnitAllY);
        xfCountYearBean.setResidenceArea(residenceAreaAllY);
        xfCountYearBean.setBusinessUnit(businessUnitAllY);
        xfCountYearBean.setBusinessArea(businessAreaAllY);
        xfCountYearBean.setRegionName("全市");
        return xfCountYearBean;
    }

    private HouseMarketListInfo.ResultBean.EsfCountYearBean initEsY() {
        HouseMarketListInfo.ResultBean.EsfCountYearBean xfCountYearBean = new HouseMarketListInfo.ResultBean.EsfCountYearBean();
        int allUnitY = 0;
        double areaAllY = 0;
        int residenceUnitAllY = 0;
        double residenceAreaAllY = 0;
        int businessUnitAllY = 0;
        double businessAreaAllY = 0;
        int otherUnitAll = 0;
        double otherAreaAll = 0;
        List<HouseMarketListInfo.ResultBean.EsfCountYearBean> listY = houseMarketListInfo.getResult().getEsfCountYear();
        for (int i = 0; i < listY.size(); i++) {
            allUnitY += listY.get(i).getUnit();
            areaAllY = getAddDouble(areaAllY, listY.get(i).getArea());
            residenceUnitAllY += listY.get(i).getResidenceUnit();
            residenceAreaAllY = getAddDouble(residenceAreaAllY, listY.get(i).getResidenceArea());
            businessUnitAllY += listY.get(i).getBusinessUnit();
            businessAreaAllY = getAddDouble(businessAreaAllY, listY.get(i).getBusinessArea());
            otherUnitAll += listY.get(i).getOtherUnit();
            otherAreaAll = getAddDouble(otherAreaAll, listY.get(i).getOtherArea());
        }
        xfCountYearBean.setOtherUnit(otherUnitAll);
        xfCountYearBean.setOtherArea(otherAreaAll);
        xfCountYearBean.setUnit(allUnitY);
        xfCountYearBean.setArea(areaAllY);
        xfCountYearBean.setResidenceUnit(residenceUnitAllY);
        xfCountYearBean.setResidenceArea(residenceAreaAllY);
        xfCountYearBean.setBusinessUnit(businessUnitAllY);
        xfCountYearBean.setBusinessArea(businessAreaAllY);
        xfCountYearBean.setRegionName("全市");
        return xfCountYearBean;
    }

    public double getAddDouble(double one, double two) {
        BigDecimal bigDecimal1 = new BigDecimal(one);
        BigDecimal bigDecimal2 = new BigDecimal(two);
        return bigDecimal1.add(bigDecimal2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private String[] getTabNames(HouseMarketListInfo houseMarketListInfo) {
        String[] names;
        if (!isShopChecked) {
            //存量房
            List<HouseMarketListInfo.ResultBean.EsfCountRegionBean> regions = houseMarketListInfo.getResult().getEsfCountRegion();
            names = new String[regions.size()];
            for (int i = 0; i < regions.size(); i++) {
                names[i] = regions.get(i).getRegionName();
            }
        } else {
            //商品房
            List<HouseMarketListInfo.ResultBean.XfCountRegionBean> regions = houseMarketListInfo.getResult().getXfCountRegion();
            names = new String[regions.size()];
            for (int i = 0; i < regions.size(); i++) {
                names[i] = regions.get(i).getRegionName();
            }
        }
        return names;
    }


    /**
     * 设置顶部tab改变的监听
     */
    private boolean isShopChecked = true;
    private boolean isAddViewOver = false;

    private void initSwitchMainTab() {
        tvShopHouse.setOnClickListener(v -> {
            if (isShopChecked) {
                return;
            }
            isShopChecked = true;
            changeState(isShopChecked);
        });
        tvSaveHouse.setOnClickListener(v -> {
            if (!isShopChecked) {
                return;
            }
            isShopChecked = false;
            changeState(isShopChecked);
        });
        isAddViewOver = true;
    }

    /**
     * 改变颜色状态
     *
     * @param isShopChecked
     */
    private void changeState(boolean isShopChecked) {
        if (isShopChecked) {
            tvShopHouse.setBackground(getResources().getDrawable(R.drawable.hm_left_select));
            tvSaveHouse.setBackground(getResources().getDrawable(R.drawable.hm_right_unselect));
            tvShopHouse.setTextColor(getResources().getColor(R.color.white));
            tvSaveHouse.setTextColor(getResources().getColor(R.color.dy_them_color_De0));
            String[] names = getTabNames(houseMarketListInfo);
            for (int i = 0; i < names.length; i++) {
                if (tvs.get(i) != null) {
                    tvs.get(i).setText(names[i]);
                }
            }
        } else {
            tvSaveHouse.setBackground(getResources().getDrawable(R.drawable.hm_right_select));
            tvShopHouse.setBackground(getResources().getDrawable(R.drawable.hm_left_unselect));
            tvSaveHouse.setTextColor(getResources().getColor(R.color.white));
            tvShopHouse.setTextColor(getResources().getColor(R.color.dy_them_color_De0));
            String[] names = getTabNames(houseMarketListInfo);
            for (int i = 0; i < names.length; i++) {
                if (tvs.get(i) != null) {
                    tvs.get(i).setText(names[i]);
                }
            }
        }
        currentPage = 0;
        viewpager.setCurrentItem(currentPage);
        if (currentFragment != null) {
            currentFragment.showDataOnPage(isShopChecked, currentPage);
        } else {
            LogUtils.d("改变名称失败");
        }
    }


    /**
     * 添加fragment到布局中
     */
    List<TextView> tvs = new ArrayList<>();//用于存储tab

    private void initPageFg(String[] names) {
        MainActivity mainActivity = (MainActivity) context;
        FragmentPagerItems.Creator items = FragmentPagerItems.with(mainActivity);
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            Bundle bundle = new Bundle();
            bundle.putSerializable("name", name);
            bundle.putSerializable("info", houseMarketListInfo);
            bundle.putInt("position", i);
            items.add(name, ShopHouseMarkeyPageFragment.class, bundle);
        }
        FragmentPagerItems c = items.create();
        FragmentPagerItemAdapter pageAdapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), c);
        viewpager.setOffscreenPageLimit(names.length);
        viewpager.setAdapter(pageAdapter);
        tabLayout.setCustomTabView(R.layout.hm_custom_tab_text, R.id.tv_tab);
        tabLayout.setViewPager(viewpager);
        tvs.clear();
        for (int i = 0; i < names.length; i++) {
            tvs.add((TextView) ((LinearLayout) tabLayout.getTabAt(i)).getChildAt(0));
        }
        tvs.get(0).setTextColor(getResources().getColor(R.color.dy_them_color_blue));
        tabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                clearTextColor();
                currentPage = position;
                TextView tv = tvs.get(position);
                tv.setTextColor(getResources().getColor(R.color.dy_them_color_blue));
                currentFragment = (ShopHouseMarkeyPageFragment) pageAdapter.getPage(position);
                currentFragment.showDataOnPage(isShopChecked, currentPage);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.setCurrentItem(currentPage);
        currentFragment = (ShopHouseMarkeyPageFragment) pageAdapter.getPage(0);
        currentFragment.showDataOnPage(isShopChecked, currentPage);
    }


    public void clearTextColor() {
        for (int i = 0; i < tvs.size(); i++) {
            tvs.get(i).setTextColor(getResources().getColor(R.color.dy_them_color_F66));
        }
    }
}
