package com.house.mine.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.house.mine.R;

import java.util.HashMap;

import yin.deng.dyutils.base.SuperBaseActivity;
import yin.deng.dyutils.http.BaseHttpInfo;

//第六步:展示申请结果页面
public class InfoDeclareStepSixAc extends SuperBaseActivity
        implements View.OnClickListener
{

    private TextView    mTvTitle,mTvResult;
    private FrameLayout mFmLeft;

    @Override
    public int setLayout() {
        return R.layout.ac_info_declare_step_six;
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
        mTvResult = findViewById(R.id.tv_result);
        mFmLeft = findViewById(R.id.fm_left);
    }

    private void initData() {
        HashMap<String, String> mandatorData = (HashMap<String, String>) getIntent().getSerializableExtra("mandator_data");
        if(mandatorData != null && !TextUtils.isEmpty(mandatorData.get("dealCode"))){
            mTvTitle.setText("提交成功");
            Drawable sucessDrawable = getResources().getDrawable(R.drawable.success);
            sucessDrawable.setBounds(0, 0, sucessDrawable.getMinimumWidth(), sucessDrawable.getMinimumHeight());
            mTvResult.setCompoundDrawables(null, sucessDrawable, null, null);
            mTvResult.setText("业务件号为:" + mandatorData.get("dealCode") +"的注册申请已提交成功!");
        }else {
            mTvTitle.setText("提交失败");
            Drawable failDrawable = getResources().getDrawable(R.drawable.success);
            failDrawable.setBounds(0, 0, failDrawable.getMinimumWidth(), failDrawable.getMinimumHeight());
            mTvResult.setCompoundDrawables(null, failDrawable, null, null);
            mTvResult.setText("提交失败");
        }
    }

    private void initListener() {
        mFmLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mFmLeft){
            back();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            back();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void back() {
        Intent intent = new Intent();
        intent.setClassName("com.funi.mas", "com.funi.mas.main.MainActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
