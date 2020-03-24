package com.house.mine.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.house.mine.R;
import com.house.mine.bean.DataInfo;
import com.house.mine.util.SingleSelectHelper;
import com.house.mine.util.UrlConfig;
import com.house.mine.view.CustomDialog;
import com.house.mine.view.SingleSelectPopupwindw;
import com.okhttplib.HttpInfo;
import com.okhttplib.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import yin.deng.dyutils.base.SuperBaseActivity;
import yin.deng.dyutils.http.BaseHttpInfo;

//第二步:企业信息
public class InfoDeclareStepTwoAc
        extends SuperBaseActivity
        implements RadioGroup.OnCheckedChangeListener,View.OnClickListener
{

    private TextView    mTvTitle,mTvBusinessType,mTvCompanyType,mTvNext;
    private RadioGroup  mRadioGroup;
    private RadioButton mRadioHas,mRadioNo;
    private EditText mEtCompanyName,mEtMotherCompanyName,mEtCompanyContactTel,mEtCompanyComplaintTel;
    private String mBusinessTypeValue = "11",mCompanyTypeValue = "17";

    private List<DataInfo> mOrgTypes     = new ArrayList<>();
    private List<DataInfo> mCompanyTypes = new ArrayList<>();
    private HashMap<String, String> mParams;

    private String mOrgType;

    @Override
    public int setLayout() {
        return R.layout.ac_info_declare_step_two;
    }

    @Override
    public void onMsgHere(BaseHttpInfo info) {

    }

    @Override
    protected void initFirst() {
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mTvTitle = findViewById(R.id.tvTitle);
        mTvTitle.setText("企业信息");

        mRadioGroup = findViewById(R.id.radio_group);
        mRadioHas = findViewById(R.id.radio_has);
        mRadioNo = findViewById(R.id.radio_no);
        mEtCompanyName = findViewById(R.id.et_company_name);
        mEtMotherCompanyName = findViewById(R.id.et_mother_company_name);
        mEtCompanyContactTel = findViewById(R.id.et_company_contact_tel);
        mEtCompanyComplaintTel = findViewById(R.id.et_company_complaint_tel);
        mTvBusinessType = findViewById(R.id.tv_business_type);
        mTvCompanyType = findViewById(R.id.tv_company_type);
        mTvNext = findViewById(R.id.tv_next);
    }

    private void initData() {
        mOrgType = getIntent().getStringExtra(UrlConfig.ORGTYPE);
        if(mOrgType.equals(UrlConfig.ORGTYPE_BROKER)){
            mTvBusinessType.setText("经纪机构");
        }
        mParams = (HashMap<String, String>) getIntent().getSerializableExtra("data");
        getBusinessType();
        getCompanyType();
    }

    private void getBusinessType() {
        getHttpUtils().sendMsgGet(UrlConfig.GET_ORG_TYPE + "?orgType=" + mOrgType + "&_dc=" + System.currentTimeMillis(), null, new Callback(){

            @Override
            public void onSuccess(HttpInfo info)
                    throws IOException
            {
                getHttpUtils().initSucessLog(info,true);
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    JSONArray  array      = jsonObject.optJSONObject("result").optJSONArray("list");
                    for(int i = 0; i < array.length(); i++){
                        DataInfo certificateInfo = new DataInfo();
                        certificateInfo.setName(array.optJSONObject(i).optString("name"));
                        certificateInfo.setValue(array.optJSONObject(i).optString("value"));
                        mOrgTypes.add(certificateInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpInfo info)
                    throws IOException
            {
                getHttpUtils().initSucessLog(info,false);
            }
        });
    }

    private void getCompanyType() {
        getHttpUtils().sendMsgGet(UrlConfig.GET_ORG_NATURE + "?_dc=" + System.currentTimeMillis(), null, new Callback(){

            @Override
            public void onSuccess(HttpInfo info)
                    throws IOException
            {
                getHttpUtils().initSucessLog(info,true);
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    JSONArray  array      = jsonObject.optJSONObject("result").optJSONArray("list");
                    for(int i = 0; i < array.length(); i++){
                        DataInfo certificateInfo = new DataInfo();
                        certificateInfo.setName(array.optJSONObject(i).optString("name"));
                        certificateInfo.setValue(array.optJSONObject(i).optString("value"));
                        mCompanyTypes.add(certificateInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpInfo info)
                    throws IOException
            {
                getHttpUtils().initSucessLog(info,false);
            }
        });
    }

    private void initListener() {
        mRadioGroup.setOnCheckedChangeListener(this);
        mTvBusinessType.setOnClickListener(this);
        mTvCompanyType.setOnClickListener(this);
        mTvNext.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == R.id.radio_has){
            mRadioHas.setChecked(true);
            mRadioNo.setChecked(false);
            mEtMotherCompanyName.setEnabled(true);
            mEtMotherCompanyName.requestFocus();
        }else if(checkedId == R.id.radio_no){
            mRadioHas.setChecked(false);
            mRadioNo.setChecked(true);
            mEtMotherCompanyName.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mTvBusinessType){
            SingleSelectHelper.singleSelect(this, mOrgTypes, mTvBusinessType,
                                            new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
                @Override
                public void afterChooseItem(DataInfo info) {
                    mTvBusinessType.setText(info.getName());
                    mBusinessTypeValue = info.getValue();
                }
            });
            //SingleSelectAdapter<DataInfo> adapter = new SingleSelectAdapter<DataInfo>(this) {
            //    @Override
            //    public void bindData(DataInfo certificateInfo, TextView tv) {
            //        tv.setText(certificateInfo.getName());
            //    }
            //};
            //SingleSelectPopupwindw<DataInfo> popupwindw = new SingleSelectPopupwindw<>(this, adapter);
            //popupwindw.setData(mOrgTypes);
            //popupwindw.show(mTvBusinessType);
            //popupwindw.setChooseItemListener(new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
            //    @Override
            //    public void afterChooseItem(DataInfo info) {
            //        mTvBusinessType.setText(info.getName());
            //    }
            //});
        }else if(v == mTvCompanyType){
            SingleSelectHelper.singleSelect(this, mCompanyTypes, mTvCompanyType,
                                            new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
                @Override
                public void afterChooseItem(DataInfo info) {
                    mTvCompanyType.setText(info.getName());
                    mCompanyTypeValue = info.getValue();
                }
            });
            //SingleSelectAdapter<DataInfo> adapter = new SingleSelectAdapter<DataInfo>(this) {
            //    @Override
            //    public void bindData(DataInfo certificateInfo, TextView tv) {
            //        tv.setText(certificateInfo.getName());
            //    }
            //};
            //SingleSelectPopupwindw<DataInfo> popupwindw = new SingleSelectPopupwindw<>(this, adapter);
            //popupwindw.setData(mCompanyTypes);
            //popupwindw.show(mTvCompanyType);
            //popupwindw.setChooseItemListener(new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
            //    @Override
            //    public void afterChooseItem(DataInfo info) {
            //        mTvCompanyType.setText(info.getName());
            //    }
            //});
        }else if(v == mTvNext){
             String companyName = mEtCompanyName.getText().toString().trim();
             if(TextUtils.isEmpty(companyName)){
                 Toast.makeText(this,"请输入企业名称",Toast.LENGTH_SHORT).show();
                 return;
             }
            String motherCompanyName = mEtMotherCompanyName.getText().toString().trim();
            if(mRadioHas.isChecked()&&TextUtils.isEmpty(motherCompanyName)){
                Toast.makeText(this,"请输入母公司名称",Toast.LENGTH_SHORT).show();
                return;
            }
            String companyContactTel = mEtCompanyContactTel.getText().toString().trim();
            if(TextUtils.isEmpty(companyContactTel)){
                Toast.makeText(this,"请输入企业联系电话",Toast.LENGTH_SHORT).show();
                return;
            }
            String companyComplaintTel = mEtCompanyComplaintTel.getText().toString().trim();
            if(TextUtils.isEmpty(companyComplaintTel)){
                Toast.makeText(this,"请输入企业投诉电话",Toast.LENGTH_SHORT).show();
                return;
            }

             getUpComp(motherCompanyName);

            final HashMap<String,String> params = new HashMap<>();
            params.put("orgName",companyName);
            params.put("orgType",mBusinessTypeValue);
            params.put("tel",companyContactTel);
            params.put("isGroup",mRadioHas.isChecked() ? "1" : "0");
            params.put("parentOrgCode","");
            params.put("orgNature",mCompanyTypeValue);
            params.put("complaintTel",companyComplaintTel);
            params.put("dealId",mParams.get("id"));
            params.put("dealCode",mParams.get("dealCode"));
            params.put("logicRegionCode",mParams.get("logicRegionCode"));

             final CustomDialog dialog = new CustomDialog(this);
             dialog.setMessage("有无统一社会信用代码?", Gravity.CENTER_HORIZONTAL);
             dialog.setNegativeBtn("无", new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     dialog.dismiss();
                     Intent intent = new Intent(InfoDeclareStepTwoAc.this,InfoDeclareStepThreeWithoutCreditCodeAc.class);
                     intent.putExtra(UrlConfig.ORGTYPE,mOrgType);
                     intent.putExtra("data",params);
                     intent.putExtra("mandator_data",mParams);
                     startActivity(intent);
                 }
             });
             dialog.setPositiveBtn("有", new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     dialog.dismiss();
                     Intent intent = new Intent(InfoDeclareStepTwoAc.this,InfoDeclareStepThreeWithCreditCodeAc.class);
                     intent.putExtra(UrlConfig.ORGTYPE,mOrgType);
                     intent.putExtra("data",params);
                     intent.putExtra("mandator_data",mParams);
                     startActivity(intent);
                 }
             });
             dialog.show();
        }
    }

    private void getUpComp(String motherCompanyName) {
        final HashMap<String,String> params = new HashMap<>();
        params.put("keyword",motherCompanyName);
        params.put("page","1");
        params.put("start","0");
        params.put("limit","25");
        getHttpUtils().sendMsgPost(UrlConfig.GET_UP_COMP + "?_dc=" + System.currentTimeMillis(),
                                   params,
                                   new Callback() {

                                       @Override
                                       public void onSuccess(HttpInfo info)
                                               throws IOException
                                       {
                                           getHttpUtils().initSucessLog(info,true);
                                       }

                                       @Override
                                       public void onFailure(HttpInfo info)
                                               throws IOException
                                       {
                                           getHttpUtils().initSucessLog(info,false);
                                       }
                                   });
    }
}
