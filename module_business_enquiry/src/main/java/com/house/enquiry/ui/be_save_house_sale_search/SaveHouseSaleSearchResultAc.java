package com.house.enquiry.ui.be_save_house_sale_search;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.widget.TextView;

import com.business.enquiry.R;

import org.json.JSONObject;

import yin.deng.dyutils.base.SuperBaseActivity;
import yin.deng.dyutils.http.BaseHttpInfo;

public class SaveHouseSaleSearchResultAc
        extends SuperBaseActivity {

    private TextView tvTitle;
    private TextView mTvWqh,mTvWqtime,mTvStatus;
    private TextView mTvHouseAddress,mTvArea,mTvInnerArea,mTvHouseFunction;
    @Override
    public int setLayout() {
        return R.layout.save_house_sale_search_result_ac;
    }

    @Override
    public void onMsgHere(BaseHttpInfo info) {

    }

    @Override
    protected void initFirst() {
       initView();
       initData();
    }

    private void initView() {
        tvTitle=findViewById(R.id.tvTitle);
        mTvWqh = findViewById(R.id.tv_wqh);
        mTvWqtime = findViewById(R.id.tv_wqtime);
        mTvStatus = findViewById(R.id.tv_status);
        mTvHouseAddress = findViewById(R.id.tv_house_address);
        mTvArea = findViewById(R.id.tv_area);
        mTvInnerArea = findViewById(R.id.tv_inner_area);
        mTvHouseFunction = findViewById(R.id.tv_house_function);
    }

    private void initData() {
        SpannableString m2 = new SpannableString("m2");
        m2.setSpan(new RelativeSizeSpan(0.6f), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//一半大小
        m2.setSpan(new SuperscriptSpan(), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);   //上标

        tvTitle.setText("存量房买卖合同摘要");
        String result = getIntent().getStringExtra("result");
        try {
            JSONObject jsonObject = new JSONObject(result);
            mTvWqh.setText(jsonObject.optString("contractCode"));
            mTvWqtime.setText(jsonObject.optString("timeOfContract"));
            mTvStatus.setText(jsonObject.optString("contractStatus"));
            mTvHouseAddress.setText(jsonObject.optString("houseAddress"));
            mTvArea.setText(new SpannableStringBuilder(jsonObject.optString("buildArea")).append(m2));
            mTvInnerArea.setText(new SpannableStringBuilder(jsonObject.optString("innerArea")).append(m2));
            mTvHouseFunction.setText(jsonObject.optString("houseType"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
