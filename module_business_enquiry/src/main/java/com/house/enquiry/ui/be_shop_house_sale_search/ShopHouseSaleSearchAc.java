package com.house.enquiry.ui.be_shop_house_sale_search;


import android.content.Intent;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
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
 * 商品房买卖合同查询
 */
public class ShopHouseSaleSearchAc extends SuperBaseActivity {
    ImageView ivImgSearch;
    TextView tvSearchBt;
    TextView tvTitle;
    LinearLayout llSearchContainer;
    private EditText codeEt;
    private EditText bahEt;
    //private EditText pwdEt;

    @Override
    public int setLayout() {
        return R.layout.normal_search_ac;
    }

    @Override
    public void onMsgHere(BaseHttpInfo info) {

    }

    @Override
    public void bindViewWithId() {
        tvTitle = findViewById(R.id.tvTitle);
        ivImgSearch = findViewById(R.id.iv_img_search);
        tvSearchBt = findViewById(R.id.tv_search_bt);
        llSearchContainer = findViewById(R.id.ll_search_container);
        ivImgSearch.setImageResource(R.mipmap.shop_house_big_pic);
        dealWithOnClickAndUiWithType();
    }

    private void dealWithOnClickAndUiWithType() {
        tvTitle.setText("商品房买卖合同查询");
        View serchLayoutCode = View.inflate(this, R.layout.be_search_editext, null);
        View serchLayoutBah = View.inflate(this, R.layout.be_search_editext, null);
        //View serchLayoutPwd = View.inflate(this, R.layout.be_search_editext, null);
        initItemUi(serchLayoutCode, serchLayoutBah);//, serchLayoutPwd);
        llSearchContainer.removeAllViews();
        llSearchContainer.addView(serchLayoutCode);
        llSearchContainer.addView(serchLayoutBah);
        //llSearchContainer.addView(serchLayoutPwd);
        tvSearchBt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                startToSearchResultAc();
            }
        });
    }


    private void startToSearchResultAc() {
        if (MyUtils.isEmpty(codeEt)) {
            showTs("请输入合同编号");
            return;
        }
        if (MyUtils.isEmpty(bahEt)) {
            showTs("请输入合同预留手机号");
            return;
        }
        //toOtherAc();
        //if (MyUtils.isEmpty(pwdEt)) {
        //    showTs("请输入验证码");
        //    return;
        //}
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("contractCode", codeEt.getText().toString().trim());
        jsonObject.addProperty("phoneNumber", bahEt.getText().toString().trim());
        getHttpUtils().sendMsgPost(NetConfig.IS_HT_EXIST_XF, jsonObject, new Callback() {
            @Override
            public void onSuccess(HttpInfo info) throws IOException {
                getHttpUtils().initSucessLog(info, true);
                String data = info.getRetDetail();
                Gson gson = new Gson();
                IsOkInfo okInfo = gson.fromJson(data, IsOkInfo.class);
                if (okInfo.isSuccess()) {
                    ScreenUtils.hideSoft(ShopHouseSaleSearchAc.this, codeEt);
                    Log.e("ddddddddddddd","result = " + okInfo.getResult());
                    //跳转到结果展示页
                    Intent intent = new Intent(ShopHouseSaleSearchAc.this, ShopHouseSaleSearchResultAc.class);
                    intent.putExtra("result",okInfo.getResult());
                    startActivity(intent);
                    //跳转到H5结果页
                    //Intent intent = new Intent(ShopHouseSaleSearchAc.this, NormalWebAc.class);
                    //intent.putExtra("title", "商品房买卖合同摘要");
                    //intent.putExtra("url", WebUrlConfig.SHOP_HOUSE_SALE_SEARCH_URL + "?contractCode=" + codeEt.getText().toString().trim() + "&phoneNumber=" + bahEt.getText().toString().trim());// + "&password=" + pwdEt.getText().toString().trim());
                    //startActivity(intent);
                } else {
                    showTs(okInfo.getMessage());
                }
            }

            @Override
            public void onFailure(HttpInfo info) throws IOException {
                getHttpUtils().initSucessLog(info, false);
                String   data   = info.getRetDetail();
                Gson     gson   = new Gson();
                IsOkInfo okInfo = gson.fromJson(data, IsOkInfo.class);
                showTs(okInfo.getMessage());
            }
        });
    }

    private void initItemUi(View serchLayoutCode, View serchLayoutBah){//, View serchLayoutPwd) {
        ImageView codeIv = serchLayoutCode.findViewById(R.id.iv_first_icon);
        ImageView bahIv = serchLayoutBah.findViewById(R.id.iv_first_icon);
        //ImageView pwdIv = serchLayoutPwd.findViewById(R.id.iv_first_icon);
        TextView codeTv = serchLayoutCode.findViewById(R.id.tv_search_name);
        TextView bahTv = serchLayoutBah.findViewById(R.id.tv_search_name);
        //TextView pwdTv = serchLayoutPwd.findViewById(R.id.tv_search_name);
        //Button btnSendMessage = serchLayoutPwd.findViewById(R.id.btn_send_message);
        //btnSendMessage.setVisibility(View.VISIBLE);
        //btnSendMessage.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        getMessageCode();
        //    }
        //});

        codeEt = serchLayoutCode.findViewById(R.id.et_search);
        bahEt = serchLayoutBah.findViewById(R.id.et_search);
        //pwdEt = serchLayoutPwd.findViewById(R.id.et_search);
        //设置输入最大长度
        bahEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        //pwdEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        bahEt.setInputType(InputType.TYPE_CLASS_PHONE);

        codeIv.setImageResource(R.mipmap.ht_bh);
        bahIv.setImageResource(R.mipmap.num_search_icon);
        //pwdIv.setImageResource(R.mipmap.lock_search_icon);
        codeTv.setMinWidth(ScreenUtils.dipTopx(this, 85));
        bahTv.setMinWidth(ScreenUtils.dipTopx(this, 85));
        //pwdTv.setMinWidth(ScreenUtils.dipTopx(this, 85));
        codeTv.setText("合同编号");
        bahTv.setText("合同预留手机号");
        //pwdTv.setText("验证码");
        codeEt.setHint("请输入合同编号");
        bahEt.setHint("请输入合同预留手机号");
        //pwdEt.setHint("请输入验证码");
        codeEt.setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.inputtype_digits)));
        bahEt.setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.inputtype_digits)));
        //pwdEt.setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.inputtype_digits)));
        codeEt.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        bahEt.setImeOptions(EditorInfo.IME_ACTION_DONE);
        //pwdEt.setImeOptions(EditorInfo.IME_ACTION_DONE);

    }

    //获取短信验证码
    //void getMessageCode() {
    //    if (MyUtils.isEmpty(bahEt)) {
    //        showTs("请输入合同预留手机号");
    //        return;
    //    } else {

    //    }
    //}

    @Override
    protected void initFirst() {

    }
}
