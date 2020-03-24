package com.funi.mas.main;


import com.funi.mas.R;
import com.house.market.HouseMarketListInfo;
import com.next.easynavigation.view.EasyNavigationBar;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import yin.deng.dyutils.UiLayoutUtil.BottomNavigationUtils;
import yin.deng.dyutils.base.SuperBaseActivity;
import yin.deng.dyutils.config.NetConfig;
import yin.deng.dyutils.http.BaseHttpInfo;

public class MainActivity extends SuperBaseActivity {
    EasyNavigationBar easyNavigationBar;
    private List<Fragment> tabFragmentLists = new ArrayList<>();
    //Tab选项卡未选择状态图片
    private static int[] mImageViewArrayUnselected = {R.mipmap.house_infomation_un_select, R.mipmap.house_market_un_selected,
                                                      R.mipmap.house_business_un_selected};//,R.mipmap.mine_unselected};
    //Tab选项卡选中状态图片
    private static int[] mImageViewArraySelected = {R.mipmap.house_infomation_selected, R.mipmap.house_market_selected,
                                                    R.mipmap.house_business_selected};//,R.mipmap.mine_selected};
    //Tab选项卡的文字
    //private static String[] mTextViewArray = new String[4];
    private static String[] mTextViewArray = new String[3];

    @Override
    public int setLayout() {
        return R.layout.test_ac;
    }

    @Override
    public void onMsgHere(BaseHttpInfo info) {
//        if(info instanceof HouseMarketListInfo){
//            HouseMarketListInfo listInfo= (HouseMarketListInfo) info;
//            MyStaticCacheDataUtils.saveData(listInfo);
//        }
    }

    @Override
    protected void initFirst() {
        initView();
        //初始化下方tab文字
        initBottomText();
        tabFragmentLists.add(new FragmentInformation());
        tabFragmentLists.add(new FragmentHouseMarket());
        tabFragmentLists.add(new FragmentBusinessEnquiry());
       // tabFragmentLists.add(new FragmentMine());
        BottomNavigationUtils.initBottomUi(this, easyNavigationBar, mTextViewArray, mImageViewArrayUnselected, mImageViewArraySelected, tabFragmentLists);
        getHttpUtils().sendMsgPost(NetConfig.BUILDING_MARKET_LIST_URL,null, HouseMarketListInfo.class);
    }

    private void initView() {
        easyNavigationBar = findViewById(R.id.easy_navigationBar);
    }

    private void initBottomText() {
        mTextViewArray[0] = getResources().getString(R.string.house_information);
        mTextViewArray[1] = getResources().getString(R.string.house_market);
        mTextViewArray[2] = getResources().getString(R.string.business_enquiry);
        //mTextViewArray[3] = getResources().getString(R.string.mine);
    }

    /**
     * 设置为主界面，退出时会有退出提示
     *
     * @return
     */
    @Override
    protected boolean setIsExitActivity() {
        return true;
    }


}
