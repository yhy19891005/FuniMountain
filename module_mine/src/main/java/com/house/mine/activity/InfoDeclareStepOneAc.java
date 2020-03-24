package com.house.mine.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.house.mine.R;
import com.house.mine.bean.DataInfo;
import com.house.mine.util.SingleSelectHelper;
import com.house.mine.util.StringUtil;
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

//第一步:委托人信息
public class InfoDeclareStepOneAc extends SuperBaseActivity
        implements RadioGroup.OnCheckedChangeListener, View.OnClickListener
{

    private TextView    mTvTitle,mTvCertificateType,mTvArea,mTvNext;
    private RadioGroup  mRadioGroup;
    private RadioButton mRadioMan,mRadioWoman;
    private EditText mEtName,mEtTel,mEtIdCode;
    private String mIdType = "1",mGender = "1",mLogicRegionCode = "";
    private List<DataInfo> mCertificateInfos = new ArrayList<>();
    private List<DataInfo> mAreaInfos        = new ArrayList<>();
    private String mId = "", mDealCode = "";//这两个值是保存委托人信息接口请求成功后返回的

    @Override
    public int setLayout() {
        return R.layout.ac_info_declare_step_one;
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
        mTvTitle.setText("委托人");

        mTvCertificateType = findViewById(R.id.tv_certificate_type);
        mTvArea = findViewById(R.id.tv_area);
        mTvNext = findViewById(R.id.tv_next);

        mRadioGroup = findViewById(R.id.radio_group);
        mRadioMan = findViewById(R.id.radio_man);
        mRadioWoman = findViewById(R.id.radio_woman);

        mEtName = findViewById(R.id.et_name);
        mEtTel = findViewById(R.id.et_tel);
        mEtIdCode = findViewById(R.id.et_id_code);
    }

    private void initData() {
        getCertificateType();
        getAreaInfo();
    }

    private void getCertificateType() {
        getHttpUtils().sendMsgGet(UrlConfig.GET_EMP_CERTIFICATE_TYPE + "?_dc=" + System.currentTimeMillis(), null, new Callback(){

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
                        mCertificateInfos.add(certificateInfo);
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

    private void getAreaInfo() {
        getHttpUtils().sendMsgGet(UrlConfig.GET_LOGIC_REGION + "?_dc=" + System.currentTimeMillis(), null, new Callback(){

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
                        mAreaInfos.add(certificateInfo);
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
        mTvCertificateType.setOnClickListener(this);
        mTvArea.setOnClickListener(this);
        mTvNext.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
       if(checkedId == R.id.radio_man){
           mRadioMan.setChecked(true);
           mRadioWoman.setChecked(false);
           mGender = "1";
       }else if(checkedId == R.id.radio_woman){
           mRadioMan.setChecked(false);
           mRadioWoman.setChecked(true);
           mGender = "2";
       }
    }

    @Override
    public void onClick(View v) {
       if(v == mTvCertificateType){
           SingleSelectHelper.singleSelect(this, mCertificateInfos, mTvCertificateType,
                                           new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
               @Override
               public void afterChooseItem(DataInfo info) {
                   mTvCertificateType.setText(info.getName());
                   mIdType = info.getValue();
               }
           });
           //SingleSelectAdapter<DataInfo> adapter = new SingleSelectAdapter<DataInfo>(this) {
           //    @Override
           //    public void bindData(DataInfo certificateInfo, TextView tv) {
           //        tv.setText(certificateInfo.getName());
           //    }
           //};
           //SingleSelectPopupwindw<DataInfo> popupwindw = new SingleSelectPopupwindw<>(this, adapter);
           //popupwindw.setData(mCertificateInfos);
           //popupwindw.show(mTvCertificateType);
           //popupwindw.setChooseItemListener(new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
           //    @Override
           //    public void afterChooseItem(DataInfo info) {
           //        mTvCertificateType.setText(info.getName());
           //    }
           //});
       }else if(v == mTvArea){
           SingleSelectHelper.singleSelect(this, mAreaInfos, mTvArea,
                                           new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
                                               @Override
                                               public void afterChooseItem(DataInfo info) {
                                                   mTvArea.setText(info.getName());
                                                   mLogicRegionCode = info.getValue();
                                               }
                                           });
           //SingleSelectAdapter<DataInfo> adapter = new SingleSelectAdapter<DataInfo>(this) {
           //    @Override
           //    public void bindData(DataInfo certificateInfo, TextView tv) {
           //        tv.setText(certificateInfo.getName());
           //    }
           //};
           //SingleSelectPopupwindw<DataInfo> popupwindw = new SingleSelectPopupwindw<>(this,adapter);
           //popupwindw.setData(mAreaInfos);
           //popupwindw.show(mTvArea);
           //popupwindw.setChooseItemListener(new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
           //    @Override
           //    public void afterChooseItem(DataInfo info) {
           //        mTvArea.setText(info.getName());
           //    }
           //});
       }else if(v == mTvNext){
           String name = mEtName.getText().toString().trim();
           if(TextUtils.isEmpty(name)){
               Toast.makeText(this,"请输入委托人姓名",Toast.LENGTH_SHORT).show();
               return;
           }
           String tel = mEtTel.getText().toString().trim();
           if(TextUtils.isEmpty(tel)){
               Toast.makeText(this,"请输入联系电话",Toast.LENGTH_SHORT).show();
               return;
           }
           String idCode = mEtIdCode.getText().toString().trim();
           if(TextUtils.isEmpty(idCode)){
               Toast.makeText(this,"请输入证件号码",Toast.LENGTH_SHORT).show();
               return;
           }
           if(!StringUtil.isMobileNum(tel)){
               Toast.makeText(this,"手机号码有误,请重新输入",Toast.LENGTH_SHORT).show();
               return;
           }
           if(mIdType.equals("1")){
               if(!StringUtil.is15Idcard(idCode) && !StringUtil.is18Idcard(idCode)){
                   Toast.makeText(this,"身份证号有误,请重新输入",Toast.LENGTH_SHORT).show();
                   return;
               }
           }
           final HashMap<String,String> params = new HashMap<>();
           params.put("name",name);
           params.put("tel",tel);
           params.put("gender",mGender);
           params.put("idType",mIdType);
           params.put("idCode",idCode);
           params.put("logicRegionCode",mLogicRegionCode);
           params.put("dealCode",mDealCode);
           params.put("id",mId);
           getHttpUtils().sendMsgPost(UrlConfig.FORMANDATOR_SAVE + "?_dc=" + System.currentTimeMillis(),
                                      params,
                                      new Callback() {

                                          @Override
                                          public void onSuccess(HttpInfo info)
                                                  throws IOException
                                          {
                                              getHttpUtils().initSucessLog(info,true);
                                              try {
                                                  JSONObject jsonObject = new JSONObject(info.getRetDetail());
                                                  mId = jsonObject.optString("id");
                                                  mDealCode = jsonObject.optString("dealCode");
                                                  params.put("dealCode",mDealCode);
                                                  params.put("id",mId);
                                                  Intent intent = new Intent(InfoDeclareStepOneAc.this, InfoDeclareStepTwoAc.class);
                                                  //intent.putExtra("id",mId);
                                                  //intent.putExtra("dealCode",mDealCode);
                                                  intent.putExtra("data",params);
                                                  intent.putExtra(UrlConfig.ORGTYPE,getIntent().getStringExtra(UrlConfig.ORGTYPE));
                                                  startActivity(intent);
                                              } catch (JSONException e) {
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
    }
}
