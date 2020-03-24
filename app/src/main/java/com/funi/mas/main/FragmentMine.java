package com.funi.mas.main;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.funi.mas.R;
import com.house.mine.activity.InfoDeclareStepOneAc;
import com.house.mine.util.UrlConfig;

import yin.deng.dyutils.base.fragment.SuperViewPagerBaseFragment;
import yin.deng.dyutils.http.BaseHttpInfo;

/**
 * FragmentMine
 *
 * @Description: 我的
 * @Author: haiyun.ye
 * @CreateDate: 2019-11-21 16:46
 */
public class FragmentMine
        extends SuperViewPagerBaseFragment
        implements View.OnClickListener
{

    private TextView mTvDeveloperInfoDeclare,mTvPrimeBrokerInfoDeclare;
    private Intent   mIntent;

    @Override
    protected int setContentView() {
        return R.layout.view_menu_mine;
    }

    @Override
    public void onActivityMsgToHere(BaseHttpInfo info) {

    }

    @Override
    public void bindViewWithId(View view) {
        mTvDeveloperInfoDeclare = view.findViewById(R.id.developer_info_declare);
        mTvPrimeBrokerInfoDeclare = view.findViewById(R.id.primebroker_info_declare);
    }

    @Override
    protected void init() {
        mTvDeveloperInfoDeclare.setOnClickListener(this);
        mTvPrimeBrokerInfoDeclare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.developer_info_declare:
                //startActivity(new Intent(getActivity(), InfoDeclareStepThreeWithCreditCodeAc.class));
                if(mIntent == null){
                    mIntent = new Intent(getActivity(), InfoDeclareStepOneAc.class);
                }
                mIntent.putExtra(UrlConfig.ORGTYPE, UrlConfig.ORGTYPE_DEVELOPER);
                startActivity(mIntent);
                 break;
            case R.id.primebroker_info_declare:
                //startActivity(new Intent(getActivity(), InfoDeclareStepOneAc.class));
                if(mIntent == null){
                    mIntent = new Intent(getActivity(), InfoDeclareStepOneAc.class);
                }
                mIntent.putExtra(UrlConfig.ORGTYPE, UrlConfig.ORGTYPE_BROKER);
                startActivity(mIntent);
                break;
        }
    }
}
