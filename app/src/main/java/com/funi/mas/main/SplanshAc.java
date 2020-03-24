package com.funi.mas.main;

import android.content.Intent;
import android.os.Handler;

import com.funi.mas.R;

import yin.deng.dyutils.base.SuperBaseActivity;
import yin.deng.dyutils.http.BaseHttpInfo;

public class SplanshAc extends SuperBaseActivity {
    public long delayTime = 2500;

    @Override
    public int setLayout() {
        return R.layout.splansh_ac;
    }

    @Override
    public void onMsgHere(BaseHttpInfo info) {

    }

    @Override
    protected void initFirst() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplanshAc.this, MainActivity.class));
            finish();
        }, delayTime);
    }
}
