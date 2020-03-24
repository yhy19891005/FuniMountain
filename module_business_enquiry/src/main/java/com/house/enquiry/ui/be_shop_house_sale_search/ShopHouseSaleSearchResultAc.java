package com.house.enquiry.ui.be_shop_house_sale_search;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.business.enquiry.R;

import org.json.JSONArray;
import org.json.JSONObject;

import yin.deng.dyutils.base.SuperBaseActivity;
import yin.deng.dyutils.http.BaseHttpInfo;

public class ShopHouseSaleSearchResultAc extends SuperBaseActivity {

    private TextView tvTitle;
    private TextView mTvWqh,mTvWqtime,mTvStatus;
    private TextView     mTvProjectName,mTvCountryLandSyz,mTvYushouXkz,mTvCompanyName,mTvSellerIdNumber;
    private LinearLayout mLlHouVos;
    //private TextView mTvHouseAddress,mTvArea,mTvInnerArea,mTvHouseFunction;

    @Override
    public int setLayout() {
        return R.layout.shop_house_sale_search_result_ac;
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
        mTvProjectName = findViewById(R.id.tv_project_name);
        mTvCountryLandSyz = findViewById(R.id.tv_country_land_syz);
        mTvYushouXkz = findViewById(R.id.tv_yushou_xkz);
        mTvCompanyName = findViewById(R.id.tv_company_name);
        mTvSellerIdNumber = findViewById(R.id.seller_id_number);
        //mTvHouseAddress = findViewById(R.id.tv_house_address);
        //mTvArea = findViewById(R.id.tv_area);
        //mTvInnerArea = findViewById(R.id.tv_inner_area);
        //mTvHouseFunction = findViewById(R.id.tv_house_function);
        mLlHouVos = findViewById(R.id.ll_hou_vos);
    }

    private void initData() {
        SpannableString m2 = new SpannableString("m2");
        m2.setSpan(new RelativeSizeSpan(0.6f), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//一半大小
        m2.setSpan(new SuperscriptSpan(), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);   //上标

        tvTitle.setText("商品房买卖合同摘要");
        String result = getIntent().getStringExtra("result");
        try {
            JSONObject jsonObject = new JSONObject(result);
            mTvWqh.setText(jsonObject.optString("recordNumber"));
            mTvWqtime.setText(jsonObject.optString("timeOfContract"));
            mTvStatus.setText(jsonObject.optString("contractStatus"));
            mTvProjectName.setText(jsonObject.optString("projectName"));
            mTvCountryLandSyz.setText(jsonObject.optString("landUsedCode"));
            mTvYushouXkz.setText(jsonObject.optString("sellNumber"));
            JSONObject seller = jsonObject.optJSONObject("seller");
            mTvCompanyName.setText(seller.optString("name"));
            mTvSellerIdNumber.setText(seller.optString("idType") + "/" + seller.optString("idNumber"));
            //mTvHouseAddress.setText(jsonObject.optString("houseAddress"));
            //mTvArea.setText(new SpannableStringBuilder(jsonObject.optString("buildArea")).append(m2));
            //mTvInnerArea.setText(new SpannableStringBuilder(jsonObject.optString("innerArea")).append(m2));
            //mTvHouseFunction.setText(jsonObject.optString("houseType"));
            JSONArray contHouVos = jsonObject.optJSONArray("contHouVos");
            if(contHouVos != null && contHouVos.length() > 0){
                for(int i = 0 ; i < contHouVos.length() ; i ++){
                    View view = View.inflate(this,R.layout.layout_cont_hou_vos_info,null);
                    ((TextView)view.findViewById(R.id.tv_house_address)).setText(contHouVos.optJSONObject(i).optString("houseAddress"));
                    ((TextView)view.findViewById(R.id.tv_area)).setText(new SpannableStringBuilder(contHouVos.optJSONObject(i).optString("area")).append(m2));
                    ((TextView)view.findViewById(R.id.tv_inner_area)).setText(new SpannableStringBuilder(contHouVos.optJSONObject(i).optString("inArea")).append(m2));
                    ((TextView)view.findViewById(R.id.tv_house_function)).setText(contHouVos.optJSONObject(i).optString("houUsageName"));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                                     ViewGroup.LayoutParams.WRAP_CONTENT);
                    if(i > 0){
                        params.setMargins(0,8,0,0);
                    }
                    mLlHouVos.addView(view,params);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
