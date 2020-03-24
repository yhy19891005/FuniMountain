package com.house.enquiry.ui.be_re_certify_search;


import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.business.enquiry.R;

import yin.deng.dyutils.base.SuperBaseActivity;
import yin.deng.dyutils.http.BaseHttpInfo;
import yin.deng.dyutils.utils.MyUtils;
import yin.deng.dyutils.utils.NoDoubleClickListener;
import yin.deng.dyutils.utils.ScreenUtils;
import yin.deng.dyutils.web.NormalWebAc;
import yin.deng.dyutils.web.WebUrlConfig;

/**
 * 预售证查询
 */
public class BeReCertifySearchAc extends SuperBaseActivity {
    ImageView ivImgSearch;
    TextView tvSearchBt;
    TextView tvTitle;
    LinearLayout llSearchContainer;
    private EditText codeEt;

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
        ivImgSearch.setImageResource(R.mipmap.re_sale_certify_search);
        dealWithOnClickAndUiWithType();
    }

    private void dealWithOnClickAndUiWithType() {
        tvTitle.setText("预售证查询");
        View serchLayoutCode = View.inflate(this, R.layout.be_search_editext, null);
        initItemUi(serchLayoutCode);
        llSearchContainer.removeAllViews();
        llSearchContainer.addView(serchLayoutCode);
        tvSearchBt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                startToSearchResultAc();
            }
        });
    }

    private void startToSearchResultAc() {
        if (MyUtils.isEmpty(codeEt)) {
            showTs("请输入查询的内容");
            return;
        }
        ScreenUtils.hideSoft(BeReCertifySearchAc.this, codeEt);
        //跳转到H5结果页
        Intent intent = new Intent(BeReCertifySearchAc.this, NormalWebAc.class);
        intent.putExtra("title", "预售证查询结果");
        intent.putExtra("url", WebUrlConfig.RE_SALE_CERTIFY_SEARCH_URL + "?keyword=" + codeEt.getText().toString());
        startActivity(intent);
    }

    private void initItemUi(View serchLayoutCode) {
        ImageView codeIv = serchLayoutCode.findViewById(R.id.iv_first_icon);
        TextView codeTv = serchLayoutCode.findViewById(R.id.tv_search_name);
        codeEt = serchLayoutCode.findViewById(R.id.et_search);
        codeIv.setImageResource(R.mipmap.sear_be);
        codeTv.setText("查询");
        codeEt.setHint("输入项目名称、预售证号、开发商名称");
        codeEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        codeEt.setImeOptions(EditorInfo.IME_ACTION_DONE);
    }


    @Override
    protected void initFirst() {

    }
}
