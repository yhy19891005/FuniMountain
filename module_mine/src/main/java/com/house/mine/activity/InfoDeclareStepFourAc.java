package com.house.mine.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.house.mine.R;
import com.house.mine.bean.DataInfo;
import com.house.mine.util.DateUtil;
import com.house.mine.util.SingleSelectHelper;
import com.house.mine.util.UrlConfig;
import com.house.mine.view.SingleSelectPopupwindw;
import com.okhttplib.HttpInfo;
import com.okhttplib.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import yin.deng.dyutils.base.SuperBaseActivity;
import yin.deng.dyutils.http.BaseHttpInfo;

//第四步:资质信息
public class InfoDeclareStepFourAc extends SuperBaseActivity
        implements View.OnClickListener
{

    private TextView mTvTitle,mTvNext,mTvCertificateNo;
    private TextView mTvPeriodOfQualificationsDay,mTvOrgQualificationGrade,mTvDayOfQualifications;
    private EditText mEtCertificateNo;
    private LinearLayout mLlDayOfQualifications;
    private View mDividerDayOfQualifications;
    private List<DataInfo>          mOrgQualificationGrades = new ArrayList<>();
    private HashMap<String, String> mParams;
    private String mQualificationGradeValue = "";
    private String mOrgType;

    @Override
    public int setLayout() {
        return R.layout.ac_info_declare_step_four;
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
        mTvTitle.setText("资质信息");

        mTvNext = findViewById(R.id.tv_next);

        mTvCertificateNo = findViewById(R.id.tv_certificate_no);
        mTvPeriodOfQualificationsDay = findViewById(R.id.tv_period_of_qualifications_day);
        mTvOrgQualificationGrade = findViewById(R.id.tv_org_qualification_grade);
        mTvDayOfQualifications = findViewById(R.id.tv_day_of_qualifications);

        mEtCertificateNo = findViewById(R.id.et_certificate_no);

        mLlDayOfQualifications = findViewById(R.id.ll_day_of_qualifications);
        mDividerDayOfQualifications = findViewById(R.id.divider_day_of_qualifications);
    }

    private void initData() {
        mOrgType = getIntent().getStringExtra(UrlConfig.ORGTYPE);
        if(mOrgType.equals(UrlConfig.ORGTYPE_BROKER)){
            mLlDayOfQualifications.setVisibility(View.GONE);
            mDividerDayOfQualifications.setVisibility(View.GONE);
            mTvCertificateNo.setText("备案证书编号");
        }
        mParams = (HashMap<String, String>) getIntent().getSerializableExtra("data");
        if(mParams == null){
            mParams = new HashMap<>();
        }
        //获取资质等级
        getOrgQualificationGrade();
    }

    private void getOrgQualificationGrade() {
        String url = "";
        if(mOrgType.equals(UrlConfig.ORGTYPE_BROKER)){
            url = UrlConfig.GET_ORG_QUALIFICATION_GRADE_BY_TYPE + "?type=4&_dc=" + System.currentTimeMillis();
        }else if(mOrgType.equals(UrlConfig.ORGTYPE_DEVELOPER)){
            url = UrlConfig.GET_ORG_QUALIFICATION_GRADE_BY_TYPE + "?type=1&_dc=" + System.currentTimeMillis();
        }
        getHttpUtils().sendMsgGet(url, null, new Callback(){

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
                        mOrgQualificationGrades.add(certificateInfo);
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
        mTvNext.setOnClickListener(this);
        mTvPeriodOfQualificationsDay.setOnClickListener(this);
        mTvOrgQualificationGrade.setOnClickListener(this);
        mTvDayOfQualifications.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       if(v == mTvNext){
           String qualificationEnd = mTvPeriodOfQualificationsDay.getText().toString();
           if(TextUtils.isEmpty(qualificationEnd)){
               Toast.makeText(this, "请输入资质有效期", Toast.LENGTH_SHORT).show();
               return;
           }
           mParams.put("certificateNo",mEtCertificateNo.getText().toString().trim());
           mParams.put("qualificationEnd",qualificationEnd);
           mParams.put("qualificationGrade",mQualificationGradeValue);
           mParams.put("authorizedTime",mTvDayOfQualifications.getText().toString());
           getHttpUtils().sendMsgPost(UrlConfig.FORORGBASE_SAVE + "?_dc=" + System.currentTimeMillis(),
                                      mParams, new Callback(){

                       @Override
                       public void onSuccess(HttpInfo info)
                               throws IOException
                       {
                           getHttpUtils().initSucessLog(info,true);
                           HashMap<String,String> orgBaseSaveResultData = new HashMap<>();
                           try {
                               JSONObject jsonObject = new JSONObject(info.getRetDetail());
                               orgBaseSaveResultData.put("flowNode",TextUtils.isEmpty(jsonObject.optString("flowNode")) ? "" : jsonObject.optString("flowNode"));
                               orgBaseSaveResultData.put("orgId",TextUtils.isEmpty(jsonObject.optString("orgId")) ? "" : jsonObject.optString("orgId"));
                               orgBaseSaveResultData.put("orgQualificationId",TextUtils.isEmpty(jsonObject.optString("orgQualificationId")) ? "" : jsonObject.optString("orgQualificationId"));
                               orgBaseSaveResultData.put("orgQualifyId",TextUtils.isEmpty(jsonObject.optString("orgQualifyId")) ? "" : jsonObject.optString("orgQualifyId"));
                               orgBaseSaveResultData.put("orgStatus",TextUtils.isEmpty(jsonObject.optString("orgStatus")) ? "" : jsonObject.optString("orgStatus"));
                               orgBaseSaveResultData.put("orgTargetId",TextUtils.isEmpty(jsonObject.optString("orgTargetId")) ? "" : jsonObject.optString("orgTargetId"));
                               orgBaseSaveResultData.put("organizationCode",TextUtils.isEmpty(jsonObject.optString("organizationCode")) ? "" : jsonObject.optString("organizationCode"));
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                           Intent intent = new Intent(InfoDeclareStepFourAc.this, InfoDeclareStepFiveAc.class);
                           intent.putExtra("data",mParams);
                           intent.putExtra("org_data",orgBaseSaveResultData);
                           intent.putExtra("mandator_data",getIntent().getSerializableExtra("mandator_data"));
                           intent.putExtra(UrlConfig.ORGTYPE,mOrgType);
                           startActivity(intent);
                       }

                       @Override
                       public void onFailure(HttpInfo info)
                               throws IOException
                       {
                           getHttpUtils().initSucessLog(info,false);
                       }
                   });

       }else if(v == mTvPeriodOfQualificationsDay){
           DateUtil.setTime(this, mTvPeriodOfQualificationsDay);
       }else if(v == mTvDayOfQualifications){
           DateUtil.setTime(this,mTvDayOfQualifications);
       }else if(v == mTvOrgQualificationGrade){
           SingleSelectHelper.singleSelect(this,
                                           mOrgQualificationGrades,
                                           mTvOrgQualificationGrade,
                                           new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
                                               @Override
                                               public void afterChooseItem(DataInfo info) {
                                                   mTvOrgQualificationGrade.setText(info.getName());
                                                   mQualificationGradeValue = info.getValue();
                                               }
                                           });
       }
    }
}
