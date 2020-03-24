package com.house.enquiry.ui.be_save_house_sale_search;


import android.content.Intent;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.business.enquiry.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.house.enquiry.ui.IsOkInfo;
import com.okhttplib.HttpInfo;
import com.okhttplib.callback.Callback;

import java.io.IOException;

import yin.deng.dyutils.base.SuperBaseActivity;
import yin.deng.dyutils.config.NetConfig;
import yin.deng.dyutils.http.BaseHttpInfo;
import yin.deng.dyutils.utils.MyUtils;
import yin.deng.dyutils.utils.NoDoubleClickListener;
import yin.deng.dyutils.utils.ScreenUtils;

/**
 * 存量房房买卖合同查询
 */
public class SaveHouseSaleSearchAc extends SuperBaseActivity {
    ImageView ivImgSearch;
    TextView tvSearchBt;
    TextView  tvTitle;
    LinearLayout llSearchContainer;
    private EditText pwdEt;
    private EditText codeEt;
    private EditText bahEt;
    private EditText phEt;

    @Override
    public int setLayout() {
        return R.layout.normal_search_ac;
    }

    @Override
    public void onMsgHere(BaseHttpInfo info) {

    }

    @Override
    public void bindViewWithId() {
        tvTitle=findViewById(R.id.tvTitle);
        ivImgSearch=findViewById(R.id.iv_img_search);
        tvSearchBt=findViewById(R.id.tv_search_bt);
        llSearchContainer=findViewById(R.id.ll_search_container);
        ivImgSearch.setImageResource(R.mipmap.save_house_big_img);
        //根据界面不同，设置不同的标题和搜索框
        dealWithOnClickAndUiWithType();
    }

    private void dealWithOnClickAndUiWithType() {
        tvTitle.setText("存量房房买卖合同查询");
        View serchLayoutCode=View.inflate(this,R.layout.be_search_editext,null);
        View serchLayoutBah=View.inflate(this,R.layout.be_search_editext,null);
        View serchLayoutPh=View.inflate(this,R.layout.be_search_editext,null);
        View serchLayoutPwd=View.inflate(this,R.layout.be_search_editext,null);
        initItemUi(serchLayoutCode,serchLayoutBah,serchLayoutPh,serchLayoutPwd);
        llSearchContainer.removeAllViews();
        llSearchContainer.addView(serchLayoutCode);
        llSearchContainer.addView(serchLayoutBah);
        llSearchContainer.addView(serchLayoutPh);
        llSearchContainer.addView(serchLayoutPwd);
        tvSearchBt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                startToSearchResultAc();
            }
        });
    }

    private void startToSearchResultAc() {
        if(MyUtils.isEmpty(codeEt)){
            showTs("请输入合同编号");
            return;
        }
        if(MyUtils.isEmpty(bahEt)){
            showTs("请输入备案号");
            return;
        }
        if(MyUtils.isEmpty(phEt)){
            showTs("请输入合同预留手机号");
            return;
        }
        if(MyUtils.isEmpty(pwdEt)){
            showTs("请输入查询密码");
            return;
        }

        JsonObject jsonObject =new JsonObject();
        jsonObject.addProperty("contractCode",codeEt.getText().toString().trim());
        jsonObject.addProperty("recordNo",bahEt.getText().toString().trim());
        jsonObject.addProperty("phoneNumber",phEt.getText().toString().trim());
        jsonObject.addProperty("password",pwdEt.getText().toString().trim());
        getHttpUtils().sendMsgPost(NetConfig.IS_HT_EXIST_ESF, jsonObject, new Callback() {
            @Override
            public void onSuccess(HttpInfo info) throws IOException {
                getHttpUtils().initSucessLog(info,true);
                String data=info.getRetDetail();
                Gson gson=new Gson();
                IsOkInfo okInfo=gson.fromJson(data,IsOkInfo.class);
                if(okInfo.isSuccess()) {
                    ScreenUtils.hideSoft(SaveHouseSaleSearchAc.this,codeEt);
                    //跳转到结果展示页
                    Intent intent = new Intent(SaveHouseSaleSearchAc.this, SaveHouseSaleSearchResultAc.class);
                    intent.putExtra("result",okInfo.getResult());
                    startActivity(intent);
                    //跳转到H5结果页
                    //Intent intent =new Intent(SaveHouseSaleSearchAc.this, NormalWebAc.class);
                    //intent.putExtra("title","存量房买卖合同摘要");
                    //intent.putExtra("url", WebUrlConfig.SAVE_HOUSE_SALE_SEARCH_URL
                    //        +"?contractCode="+codeEt.getText().toString()
                    //        +"&recordNo="+bahEt.getText().toString().trim()
                    //        +"&phoneNumber="+phEt.getText().toString().trim()
                    //        +"&password="+pwdEt.getText().toString());
                    //startActivity(intent);
                }else{
                    showTs(okInfo.getMessage());
                }
            }

            @Override
            public void onFailure(HttpInfo info) throws IOException {
                getHttpUtils().initSucessLog(info,false);
                String   data   =info.getRetDetail();
                Gson     gson   =new Gson();
                IsOkInfo okInfo =gson.fromJson(data, IsOkInfo.class);
                showTs(okInfo.getMessage());
            }
        });
    }

    private void initItemUi(View serchLayoutCode,View serchLayoutBah,View serchLayoutPh,View serchLayoutPwd) {
        ImageView codeIv=serchLayoutCode.findViewById(R.id.iv_first_icon);
        ImageView bahIv=serchLayoutBah.findViewById(R.id.iv_first_icon);
        ImageView phIv=serchLayoutPh.findViewById(R.id.iv_first_icon);
        ImageView pwdIv=serchLayoutPwd.findViewById(R.id.iv_first_icon);
        TextView codeTv=serchLayoutCode.findViewById(R.id.tv_search_name);
        TextView bahTv=serchLayoutBah.findViewById(R.id.tv_search_name);
        TextView phTv=serchLayoutPh.findViewById(R.id.tv_search_name);
        TextView pwdTv=serchLayoutPwd.findViewById(R.id.tv_search_name);
        codeEt=serchLayoutCode.findViewById(R.id.et_search);
        bahEt=serchLayoutBah.findViewById(R.id.et_search);
        phEt=serchLayoutPh.findViewById(R.id.et_search);
        pwdEt=serchLayoutPwd.findViewById(R.id.et_search);
        codeIv.setImageResource(R.mipmap.ht_bh);
        bahIv.setImageResource(R.mipmap.num_search_icon);
        phIv.setImageResource(R.mipmap.num_search_icon);
        pwdIv.setImageResource(R.mipmap.lock_search_icon);
        codeTv.setMinWidth(ScreenUtils.dipTopx(this,85));
        bahTv.setMinWidth(ScreenUtils.dipTopx(this,85));
        phTv.setMinWidth(ScreenUtils.dipTopx(this,85));
        pwdTv.setMinWidth(ScreenUtils.dipTopx(this,85));
        codeTv.setText("合同编号");
        bahTv.setText("合同备案号");
        phTv.setText("合同预留手机号");
        pwdTv.setText("查询密码");
        codeEt.setHint("请输入合同编号");
        bahEt.setHint("请输入合同备案号");
        phEt.setHint("请输入合同预留手机号");
        pwdEt.setHint("请输入查询密码");

        codeEt.setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.inputtype_digits)));
        bahEt.setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.inputtype_digits)));
        phEt.setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.inputtype_digits)));
        pwdEt.setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.inputtype_digits)));
        codeEt.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        bahEt.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        phEt.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        pwdEt.setImeOptions(EditorInfo.IME_ACTION_DONE);
    }

    @Override
    protected void initFirst() {

    }
}
