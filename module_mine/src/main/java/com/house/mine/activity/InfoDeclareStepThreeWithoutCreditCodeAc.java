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
import com.house.mine.view.LinesEditView;
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

//第三步:工商信息(无统一社会信用代码)
public class InfoDeclareStepThreeWithoutCreditCodeAc extends SuperBaseActivity
        implements View.OnClickListener
{

    private TextView mTvTitle,mTvNext;
    private TextView mTvEstablishment,mTvRegistTime,mTvSignTime;
    private TextView mTvPeriodOfLicenseDay,mTvPeriodOfCodeDay;
    private TextView mTvCapitalUnit,mTvCertificateType;
    private TextView mTvRegistProvince,mTvRegistCity,mTvRegistRegion;
    private TextView mTvBusinessProvince,mTvBusinessCity,mTvBusinessRegion,mTvOfficeAddress;

    private EditText mEtLicenseNo,mEtOrgCode,mEtRegisteredCapital,mEtLegalRepresentative;
    private EditText mEtIdCode,mEtLegalCode,mEtContactAddress,mEtRegistDetailAddress,mEtAgent;
    private EditText mEtRegistZipCode,mEtOperateDetailAddress,mEtOperateZipCode,mEtBusinessRegistrationDepartment;

    private LinesEditView mLeOfficeRange,mLeInspectAnnually,mLeCompanyStatus;

    private LinearLayout mLlBroker,mLlSignTime,mLlAgent,mLlInspectAnnually;
    private LinearLayout mLlCompanyStatus,mLlContactAddress,mLlLegalCode;
    private View mDividerBroker,mDividerInspectAnnually,mDividerCompanyStatus;
    private View mDividerContactAddress,mDividerLegalCode,mDividerSignTime,mDividerAgent;

    private LinearLayout mLl1,mLl2;

    private List<DataInfo> mCapitalUnits     = new ArrayList<>();
    private List<DataInfo> mCertificateInfos = new ArrayList<>();
    private List<DataInfo> mProvinces = new ArrayList<>();
    private List<DataInfo> mCities    = new ArrayList<>();
    private List<DataInfo> mRegions   = new ArrayList<>();

    private String mCapitalUnitValue = "2",mLegalPersonIdTypeValue = "1";
    private String mRegistProvinceCode = "",mRegistCityCode = "",mRegistRegionCode = "";
    private String mOfficeProvinceCode = "",mOfficeCityCode = "",mOfficeRegionCode = "";

    private HashMap<String, String> mParams;
    private String mOrgType;

    @Override
    public int setLayout() {
       return R.layout.ac_info_declare_step_three_without_credit_code;
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
        mTvTitle.setText("工商信息");

        mTvNext = findViewById(R.id.tv_next);
        mTvEstablishment = findViewById(R.id.tv_establishment);
        mTvRegistTime = findViewById(R.id.tv_regist_time);
        mTvSignTime = findViewById(R.id.tv_sign_time);
        mTvPeriodOfLicenseDay = findViewById(R.id.tv_period_of_license_day);
        mTvPeriodOfCodeDay = findViewById(R.id.tv_period_of_code_day);
        mTvOfficeAddress = findViewById(R.id.tv_office_address);

        mTvCapitalUnit = findViewById(R.id.tv_capital_unit);
        mTvCertificateType = findViewById(R.id.tv_certificate_type);

        mTvRegistProvince = findViewById(R.id.tv_regist_province);
        mTvRegistCity = findViewById(R.id.tv_regist_city);
        mTvRegistRegion = findViewById(R.id.tv_regist_region);
        mTvBusinessProvince = findViewById(R.id.tv_business_province);
        mTvBusinessCity = findViewById(R.id.tv_business_city);
        mTvBusinessRegion = findViewById(R.id.tv_business_region);

        mEtLicenseNo = findViewById(R.id.et_license_no);
        mEtOrgCode = findViewById(R.id.et_org_code);
        mEtRegisteredCapital = findViewById(R.id.et_registered_capital);
        mEtLegalRepresentative = findViewById(R.id.et_legal_representative);
        mEtIdCode = findViewById(R.id.et_id_code);
        mEtLegalCode = findViewById(R.id.et_legal_code);
        mEtContactAddress = findViewById(R.id.et_contact_address);
        mEtRegistDetailAddress = findViewById(R.id.et_regist_detail_address);
        mEtRegistZipCode = findViewById(R.id.et_regist_zip_code);
        mEtOperateDetailAddress = findViewById(R.id.et_operate_detail_address);
        mEtOperateZipCode = findViewById(R.id.et_operate_zip_code);
        mEtBusinessRegistrationDepartment = findViewById(R.id.et_business_registration_department);
        mEtAgent = findViewById(R.id.et_agent);

        mLl1 = findViewById(R.id.ll1);
        mLl2 = findViewById(R.id.ll2);

        mLeOfficeRange = findViewById(R.id.le_office_range);
        mLeInspectAnnually = findViewById(R.id.le_inspect_annually);
        mLeCompanyStatus = findViewById(R.id.le_company_status);

        mLlBroker = findViewById(R.id.ll_broker);
        mLlSignTime = findViewById(R.id.ll_sign_time);
        mLlAgent = findViewById(R.id.ll_agent);
        mLlInspectAnnually = findViewById(R.id.ll_inspect_annually);
        mLlCompanyStatus = findViewById(R.id.ll_company_status);
        mLlContactAddress = findViewById(R.id.ll_contact_address);
        mLlLegalCode = findViewById(R.id.ll_legal_code);
        mDividerBroker = findViewById(R.id.divider_broker);
        mDividerInspectAnnually = findViewById(R.id.divider_inspect_annually);
        mDividerCompanyStatus = findViewById(R.id.divider_company_status);
        mDividerContactAddress = findViewById(R.id.divider_contact_address);
        mDividerLegalCode = findViewById(R.id.divider_legal_code);
        mDividerSignTime = findViewById(R.id.divider_sign_time);
        mDividerAgent = findViewById(R.id.divider_agent);
    }

    private void initData() {
        mOrgType = getIntent().getStringExtra(UrlConfig.ORGTYPE);
        if(mOrgType.equals(UrlConfig.ORGTYPE_BROKER)){
            mLlBroker.setVisibility(View.VISIBLE);
            mDividerBroker.setVisibility(View.VISIBLE);
            mLlSignTime.setVisibility(View.GONE);
            mLlAgent.setVisibility(View.GONE);
            mLlInspectAnnually.setVisibility(View.GONE);
            mLlCompanyStatus.setVisibility(View.GONE);
            mLlContactAddress.setVisibility(View.GONE);
            mLlLegalCode.setVisibility(View.GONE);
            mDividerInspectAnnually.setVisibility(View.GONE);
            mDividerCompanyStatus.setVisibility(View.GONE);
            mDividerContactAddress.setVisibility(View.GONE);
            mDividerLegalCode.setVisibility(View.GONE);
            mDividerSignTime.setVisibility(View.GONE);
            mDividerAgent.setVisibility(View.GONE);
            mTvOfficeAddress.setText("办公地址:");
            mEtOperateDetailAddress.setHint("请输入详细办公地址");
        }

        mParams = (HashMap<String, String>) getIntent().getSerializableExtra("data");
        if(mParams == null){
            mParams = new HashMap<>();
        }
        //获取货币类型
        getCapitalUnit();
        //获取证件类型
        getCertificateType();
        //获取省
        getProvince();
    }

    private void getCapitalUnit() {
        getHttpUtils().sendMsgGet(UrlConfig.GET_CAPITAL_UNIT + "?_dc=" + System.currentTimeMillis(), null, new Callback(){

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
                        mCapitalUnits.add(certificateInfo);
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

    private void getProvince(){
        getHttpUtils().sendMsgGet(UrlConfig.GET_PROVINCE + "?_dc=" + System.currentTimeMillis(), null, new Callback(){

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
                        mProvinces.add(certificateInfo);
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
        mTvEstablishment.setOnClickListener(this);
        mTvRegistTime.setOnClickListener(this);
        mTvSignTime.setOnClickListener(this);
        mTvPeriodOfLicenseDay.setOnClickListener(this);
        mTvPeriodOfCodeDay.setOnClickListener(this);
        mTvCapitalUnit.setOnClickListener(this);
        mTvCertificateType.setOnClickListener(this);
        mTvRegistProvince.setOnClickListener(this);
        mTvRegistCity.setOnClickListener(this);
        mTvRegistRegion.setOnClickListener(this);
        mTvBusinessProvince.setOnClickListener(this);
        mTvBusinessCity.setOnClickListener(this);
        mTvBusinessRegion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mTvNext){
            String licenseNo = mEtLicenseNo.getText().toString().trim();
            if(TextUtils.isEmpty(licenseNo)){
                Toast.makeText(this,"请输入营业执照号",Toast.LENGTH_SHORT).show();
                return;
            }

            mParams.put("licenseNo",licenseNo);
            mParams.put("hasSocialCode","false");
            mParams.put("licenseEnd",mTvPeriodOfLicenseDay.getText().toString());
            mParams.put("orgCode",mEtOrgCode.getText().toString().trim());
            mParams.put("orgcodeEnd",mTvPeriodOfCodeDay.getText().toString());
            mParams.put("foundDate",mTvEstablishment.getText().toString());
            mParams.put("regiCapital",mEtRegisteredCapital.getText().toString().trim());
            mParams.put("capitalUnit",mCapitalUnitValue);
            mParams.put("legalPerson",mEtLegalRepresentative.getText().toString().trim());
            mParams.put("legalPersonIdType",mLegalPersonIdTypeValue);
            mParams.put("legalPersonId",mEtIdCode.getText().toString().trim());
            mParams.put("legalPersonCode",mEtLegalCode.getText().toString().trim());
            mParams.put("contAddress",mEtContactAddress.getText().toString().trim());
            mParams.put("province",mRegistProvinceCode);
            mParams.put("regiCity",mRegistCityCode);
            mParams.put("regioncode",mRegistRegionCode);
            mParams.put("regiAddress",mEtRegistDetailAddress.getText().toString().trim());
            mParams.put("regiZip",mEtRegistZipCode.getText().toString().trim());
            mParams.put("officeProvince",mOfficeProvinceCode);
            mParams.put("officeCity",mOfficeCityCode);
            mParams.put("officeRegioncode",mOfficeRegionCode);
            mParams.put("officeAddress",mEtOperateDetailAddress.getText().toString().trim());
            mParams.put("officeZip",mEtOperateZipCode.getText().toString().trim());
            mParams.put("businessScope",mLeOfficeRange.getContentText());
            mParams.put("annualInspection",mLeInspectAnnually.getContentText());
            mParams.put("companyProfile",mLeCompanyStatus.getContentText());
            mParams.put("registerDepart",mEtBusinessRegistrationDepartment.getText().toString().trim());
            mParams.put("registerTime",mTvRegistTime.getText().toString().trim());
            mParams.put("estateDate",mTvSignTime.getText().toString().trim());
            mParams.put("agent",mEtAgent.getText().toString().trim());

            Intent intent = new Intent(this, InfoDeclareStepFourAc.class);
            intent.putExtra("data",mParams);
            intent.putExtra("mandator_data",getIntent().getSerializableExtra("mandator_data"));
            intent.putExtra(UrlConfig.ORGTYPE,mOrgType);
            startActivity(intent);
        }else if(v == mTvEstablishment){
            DateUtil.setTime(this, mTvEstablishment);
        }else if(v == mTvRegistTime){
            DateUtil.setTime(this,mTvRegistTime);
        }else if(v == mTvSignTime){
            DateUtil.setTime(this,mTvSignTime);
        }else if(v == mTvPeriodOfLicenseDay){
            DateUtil.setTime(this,mTvPeriodOfLicenseDay);
        }else if(v == mTvPeriodOfCodeDay){
            DateUtil.setTime(this,mTvPeriodOfCodeDay);
        }else if(v == mTvCapitalUnit){
            SingleSelectHelper.singleSelect(this, mCapitalUnits, mTvCapitalUnit,
                                            new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
                @Override
                public void afterChooseItem(DataInfo info) {
                    mTvCapitalUnit.setText(info.getName());
                    mCapitalUnitValue = info.getValue();
                }
            });
        }else if(v == mTvCertificateType) {
            SingleSelectHelper.singleSelect(this, mCertificateInfos, mTvCertificateType, new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
                @Override
                public void afterChooseItem(DataInfo info) {
                    mTvCertificateType.setText(info.getName());
                    mLegalPersonIdTypeValue = info.getValue();
                }
            });
        }else if(v == mTvRegistProvince){
            //SingleSelectAdapter<DataInfo> adapter = new SingleSelectAdapter<DataInfo>(this) {
            //    @Override
            //    public void bindData(DataInfo certificateInfo, TextView tv) {
            //        tv.setText(certificateInfo.getName());
            //    }
            //};
            //SingleSelectPopupwindw<DataInfo> popupwindw = new SingleSelectPopupwindw<>(this, adapter);
            //popupwindw.setData(mProvinces);
            //popupwindw.show(mTvRegistProvince);
            //popupwindw.setChooseItemListener(new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
            //    @Override
            //    public void afterChooseItem(DataInfo info) {
            //        mTvRegistProvince.setText(info.getName());
            //        mTvRegistCity.setText("");
            //        mTvRegistRegion.setText("");
            //        //根据省获取对应的市
            //        getCities(info.getValue());
            //    }
            //});
            SingleSelectHelper.singleSelect(this, mProvinces, mLl1,
                                            new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
                @Override
                public void afterChooseItem(DataInfo info) {
                    mTvRegistProvince.setText(info.getName());
                    mTvRegistCity.setText("");
                    mTvRegistRegion.setText("");
                    mRegistProvinceCode = info.getValue();
                    //根据省获取对应的市
                    getCities(mRegistProvinceCode);
                }
            });
        }else if(v == mTvRegistCity){
            String province = mTvRegistProvince.getText().toString();
            if(TextUtils.isEmpty(province)){
                Toast.makeText(this,"请先选择省",Toast.LENGTH_SHORT).show();
                return;
            }
            SingleSelectHelper.singleSelect(this, mCities, mLl1,
                                            new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
                @Override
                public void afterChooseItem(DataInfo info) {
                    mTvRegistCity.setText(info.getName());
                    mTvRegistRegion.setText("");
                    mRegistCityCode = info.getValue();
                    //根据市获取对应的区
                    getRegions(mRegistCityCode);
                }
            });
            //SingleSelectAdapter<DataInfo> adapter = new SingleSelectAdapter<DataInfo>(this) {
            //    @Override
            //    public void bindData(DataInfo certificateInfo, TextView tv) {
            //        tv.setText(certificateInfo.getName());
            //    }
            //};
            //SingleSelectPopupwindw<DataInfo> popupwindw = new SingleSelectPopupwindw<>(this, adapter);
            //popupwindw.setData(mCities);
            //popupwindw.show(mTvRegistCity);
            //popupwindw.setChooseItemListener(new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
            //    @Override
            //    public void afterChooseItem(DataInfo info) {
            //        mTvRegistCity.setText(info.getName());
            //        mTvRegistRegion.setText("");
            //        //根据市获取对应的区
            //        getRegions(info.getValue());
            //    }
            //});
        }else if(v == mTvRegistRegion){
            String city = mTvRegistCity.getText().toString();
            if(TextUtils.isEmpty(city)){
                Toast.makeText(this,"请先选择市",Toast.LENGTH_SHORT).show();
                return;
            }
            SingleSelectHelper.singleSelect(this, mRegions, mLl1,
                                            new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
                @Override
                public void afterChooseItem(DataInfo info) {
                    mTvRegistRegion.setText(info.getName());
                    mRegistRegionCode = info.getValue();
                }
            });
        }else if(v == mTvBusinessProvince){
            SingleSelectHelper.singleSelect(this, mProvinces, mLl2,
                                            new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
                @Override
                public void afterChooseItem(DataInfo info) {
                    mTvBusinessProvince.setText(info.getName());
                    mTvBusinessCity.setText("");
                    mTvBusinessRegion.setText("");
                    mOfficeProvinceCode = info.getValue();
                    //根据省获取对应的市
                    getCities(mOfficeProvinceCode);
                }
            });
            //SingleSelectAdapter<DataInfo> adapter = new SingleSelectAdapter<DataInfo>(this) {
            //    @Override
            //    public void bindData(DataInfo certificateInfo, TextView tv) {
            //        tv.setText(certificateInfo.getName());
            //    }
            //};
            //SingleSelectPopupwindw<DataInfo> popupwindw = new SingleSelectPopupwindw<>(this, adapter);
            //popupwindw.setData(mProvinces);
            //popupwindw.show(mTvBusinessProvince);
            //popupwindw.setChooseItemListener(new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
            //    @Override
            //    public void afterChooseItem(DataInfo info) {
            //        mTvBusinessProvince.setText(info.getName());
            //        mTvBusinessCity.setText("");
            //        mTvBusinessRegion.setText("");
            //        //根据省获取对应的市
            //        getCities(info.getValue());
            //    }
            //});
        }else if(v == mTvBusinessCity){
            String province = mTvBusinessProvince.getText().toString();
            if(TextUtils.isEmpty(province)){
                Toast.makeText(this,"请先选择省",Toast.LENGTH_SHORT).show();
                return;
            }
            SingleSelectHelper.singleSelect(this, mCities, mLl2,
                                            new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
                                                @Override
                                                public void afterChooseItem(DataInfo info) {
                                                    mTvBusinessCity.setText(info.getName());
                                                    mTvBusinessRegion.setText("");
                                                    mOfficeCityCode = info.getValue();
                                                    //根据市获取对应的区
                                                    getRegions(mOfficeCityCode);
                                                }
                                            });
            //SingleSelectAdapter<DataInfo> adapter = new SingleSelectAdapter<DataInfo>(this) {
            //    @Override
            //    public void bindData(DataInfo certificateInfo, TextView tv) {
            //        tv.setText(certificateInfo.getName());
            //    }
            //};
            //SingleSelectPopupwindw<DataInfo> popupwindw = new SingleSelectPopupwindw<>(this, adapter);
            //popupwindw.setData(mCities);
            //popupwindw.show(mTvBusinessCity);
            //popupwindw.setChooseItemListener(new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
            //    @Override
            //    public void afterChooseItem(DataInfo info) {
            //        mTvBusinessCity.setText(info.getName());
            //        mTvBusinessRegion.setText("");
            //        //根据市获取对应的区
            //        getRegions(info.getValue());
            //    }
            //});
        }else if(v == mTvBusinessRegion){
            String city = mTvBusinessCity.getText().toString();
            if(TextUtils.isEmpty(city)){
                Toast.makeText(this, "请先选择市", Toast.LENGTH_SHORT).show();
                return;
            }
            SingleSelectHelper.singleSelect(this, mRegions, mLl2,
                                            new SingleSelectPopupwindw.ChooseItemListener<DataInfo>() {
                @Override
                public void afterChooseItem(DataInfo info) {
                    mTvBusinessRegion.setText(info.getName());
                    mOfficeRegionCode = info.getValue();
                }
            });
        }
    }

    private void getCities(String value) {
        HashMap<String,String> params = new HashMap<>();
        params.put("province",value);
        params.put("page","1");
        params.put("start","0");
        params.put("limit","25");
        getHttpUtils().sendMsgPost(UrlConfig.GET_CITY + "?_dc=" + System.currentTimeMillis(),
                                   params,
                                   new Callback() {
                                       @Override
                                       public void onSuccess(HttpInfo info)
                                               throws IOException
                                       {
                                           getHttpUtils().initSucessLog(info,true);
                                           try {
                                               mCities.clear();
                                               JSONObject jsonObject = new JSONObject(info.getRetDetail());
                                               JSONArray  array      = jsonObject.optJSONObject("result").optJSONArray("list");
                                               for(int i = 0; i < array.length(); i++){
                                                   DataInfo cityInfo = new DataInfo();
                                                   cityInfo.setName(array.optJSONObject(i).optString("name"));
                                                   cityInfo.setValue(array.optJSONObject(i).optString("value"));
                                                   mCities.add(cityInfo);
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

    private void getRegions(String value) {
        HashMap<String,String> params = new HashMap<>();
        params.put("city",value);
        params.put("page","1");
        params.put("start","0");
        params.put("limit","50");
        getHttpUtils().sendMsgPost(UrlConfig.GET_REGION + "?_dc=" + System.currentTimeMillis(),
                                   params,
                                   new Callback() {
                                       @Override
                                       public void onSuccess(HttpInfo info)
                                               throws IOException
                                       {
                                           getHttpUtils().initSucessLog(info,true);
                                           try {
                                               mRegions.clear();
                                               JSONObject jsonObject = new JSONObject(info.getRetDetail());
                                               JSONArray  array      = jsonObject.optJSONObject("result").optJSONArray("list");
                                               for(int i = 0; i < array.length(); i++){
                                                   DataInfo regionInfo = new DataInfo();
                                                   regionInfo.setName(array.optJSONObject(i).optString("name"));
                                                   regionInfo.setValue(array.optJSONObject(i).optString("value"));
                                                   mRegions.add(regionInfo);
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
}
