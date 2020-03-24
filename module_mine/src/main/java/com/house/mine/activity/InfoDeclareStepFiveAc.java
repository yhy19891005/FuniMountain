package com.house.mine.activity;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.house.mine.R;
import com.house.mine.util.UrlConfig;
import com.house.mine.view.CustomDialog;
import com.house.mine.view.ImgUploadItemView;
import com.okhttplib.HttpInfo;
import com.okhttplib.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import yin.deng.dyutils.base.SuperBaseActivity;
import yin.deng.dyutils.http.BaseHttpInfo;

//第五步:要件信息
public class InfoDeclareStepFiveAc extends SuperBaseActivity
        implements View.OnClickListener
{

    private TextView mTvTitle,mTvNext;
    private ImgUploadItemView mUivLinence,mUivOrgCode,mUivQualification,mUivLegalpersonId;
    private ImgUploadItemView mUivLandUserightCertificate,mUivBuildingPermit,mUivUserightPlanningPermit;
    private ImgUploadItemView mUivConstructionPermit,mUivAgent,mUivAgentIdcard;
    public View mDividerQualification,mDividerLandUserightCertificate,mDividerBuildingPermit;
    public View mDividerUserightPlanningPermit,mDividerConstructionPermit,mDividerAgent;

    public HashMap<String, String> mParams;
    public String mOrgType;
    public HashMap<String, String> mOrgData;

    @Override
    public int setLayout() {
        return R.layout.ac_info_declare_step_five;
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
        mTvTitle.setText("要件信息");
        mUivLinence = findViewById(R.id.uiv_linence);
        mUivOrgCode = findViewById(R.id.uiv_orgcode);
        mUivQualification = findViewById(R.id.uiv_qualification);
        mUivLegalpersonId = findViewById(R.id.uiv_legalpersonid);
        mUivLandUserightCertificate = findViewById(R.id.uiv_land_useright_certificate);
        mUivBuildingPermit = findViewById(R.id.uiv_building_permit);
        mUivUserightPlanningPermit = findViewById(R.id.uiv_land_useright_planning_permit);
        mUivConstructionPermit = findViewById(R.id.uiv_construction_permit);
        mUivAgent = findViewById(R.id.uiv_agent);
        mUivAgentIdcard = findViewById(R.id.uiv_agent_idcard);

        mDividerQualification = findViewById(R.id.divider_qualification);
        mDividerLandUserightCertificate = findViewById(R.id.divider_land_useright_certificate);
        mDividerBuildingPermit = findViewById(R.id.divider_building_permit);
        mDividerUserightPlanningPermit = findViewById(R.id.divider_land_useright_planning_permit);
        mDividerConstructionPermit = findViewById(R.id.divider_construction_permit);
        mDividerAgent = findViewById(R.id.divider_agent);

        mTvNext = findViewById(R.id.tv_next);
    }

    private void initData() {
        mOrgType = getIntent().getStringExtra(UrlConfig.ORGTYPE);
        mParams = (HashMap<String, String>) getIntent().getSerializableExtra("data");
        mOrgData = (HashMap<String, String>) getIntent().getSerializableExtra("org_data");
        if(mParams == null){
            mParams = new HashMap<>();
        }
        if(mOrgData == null){
            mOrgData = new HashMap<>();
        }
        if(mOrgType.equals(UrlConfig.ORGTYPE_BROKER)){
            mUivQualification.setVisibility(View.GONE);
            mUivLandUserightCertificate.setVisibility(View.GONE);
            mUivBuildingPermit.setVisibility(View.GONE);
            mUivUserightPlanningPermit.setVisibility(View.GONE);
            mUivConstructionPermit.setVisibility(View.GONE);
            mUivAgent.setVisibility(View.GONE);
            mUivAgentIdcard.setVisibility(View.GONE);

            mDividerQualification.setVisibility(View.GONE);
            mDividerLandUserightCertificate.setVisibility(View.GONE);
            mDividerBuildingPermit.setVisibility(View.GONE);
            mDividerUserightPlanningPermit.setVisibility(View.GONE);
            mDividerConstructionPermit.setVisibility(View.GONE);
            mDividerAgent.setVisibility(View.GONE);
        }
        //获取要件
        getFiles();
    }

    private void getFiles() {
        final HashMap<String,String> params = new HashMap<>();
        params.put("orgType",mOrgType);
        params.put("entOrgQualifyId",mParams.get("orgQualifyId"));
        params.put("page","1");
        params.put("start","0");
        params.put("limit","25");
        getHttpUtils().sendMsgPost(UrlConfig.FORORGFILE_GETFILES + "?_dc=" + System.currentTimeMillis(),
                                   params,
                                   new Callback() {

                                       @Override
                                       public void onSuccess(HttpInfo info)
                                               throws IOException
                                       {
                                           getHttpUtils().initSucessLog(info,true);
                                           try {
                                               JSONObject jsonObject = new JSONObject(info.getRetDetail());
                                               JSONArray array = jsonObject.optJSONObject("result").optJSONArray("list");
                                               for(int i = 0; i < array.length(); i ++){
                                                   String fileName = array.optJSONObject(i).optString("fileName");
                                                   String entFileConfId = array.optJSONObject(i).optString("entFileConfId");
                                                   Log.e("ddddddd",fileName + " <-----> " + entFileConfId);
                                               }
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

    private void initListener() {
        mTvNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mTvNext){
            final CustomDialog dialog = new CustomDialog(this);
            dialog.setMessage("您确定要提交?", Gravity.CENTER_HORIZONTAL);
            dialog.setNegativeBtn("否", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setPositiveBtn("是", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    commit();
                }
            });
            if(!isFinishing()) dialog.show();
        }
    }

    private void commit() {
        HashMap<String, String> mandatorData = (HashMap<String, String>) getIntent().getSerializableExtra("mandator_data");
        getHttpUtils().sendMsgPost(UrlConfig.FORMANDATOR_SAVE + "?_dc=" + System.currentTimeMillis(),
                                   mandatorData,
                                   new Callback() {

                                       @Override
                                       public void onSuccess(HttpInfo info)
                                               throws IOException
                                       {
                                           getHttpUtils().initSucessLog(info,true);
                                           saveInfo();
                                       }

                                       @Override
                                       public void onFailure(HttpInfo info)
                                               throws IOException
                                       {
                                           getHttpUtils().initSucessLog(info,false);
                                       }
                                   });

    }

    private void saveInfo() {
        getHttpUtils().sendMsgPost(UrlConfig.FORORGBASE_SAVE + "?_dc=" + System.currentTimeMillis(),
                                   mParams,
                                   new Callback(){

                    @Override
                    public void onSuccess(HttpInfo info)
                            throws IOException
                    {
                        getHttpUtils().initSucessLog(info,true);
                        Intent intent = new Intent(InfoDeclareStepFiveAc.this, InfoDeclareStepSixAc.class);
                        intent.putExtra("data",mParams);
                        intent.putExtra("mandator_data",getIntent().getSerializableExtra("mandator_data"));
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(HttpInfo info)
                            throws IOException
                    {
                        getHttpUtils().initSucessLog(info,false);
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
    }

    @Override
    protected void onResume() {
        super.onResume();

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 1f;
        getWindow().setAttributes(params);
    }
}
