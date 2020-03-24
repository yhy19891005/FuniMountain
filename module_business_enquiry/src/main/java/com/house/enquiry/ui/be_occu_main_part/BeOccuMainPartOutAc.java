package com.house.enquiry.ui.be_occu_main_part;

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

/**
 * 从业主体查询
 */
public class BeOccuMainPartOutAc extends SuperBaseActivity {
    ImageView ivImgSearch;
    TextView tvSearchBt;
    TextView  tvTitle;
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
        tvTitle=findViewById(R.id.tvTitle);
        ivImgSearch=findViewById(R.id.iv_img_search);
        tvSearchBt=findViewById(R.id.tv_search_bt);
        llSearchContainer=findViewById(R.id.ll_search_container);
        ivImgSearch.setImageResource(R.mipmap.occu_main_part_big_pic);
        dealWithOnClickAndUiWithType();
    }

    private void dealWithOnClickAndUiWithType() {
        tvTitle.setText("从业主体查询");
        View serchLayoutCode=View.inflate(this,R.layout.be_search_editext,null);
        initItemUi(serchLayoutCode);
        llSearchContainer.removeAllViews();
        llSearchContainer.addView(serchLayoutCode);
        tvSearchBt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ScreenUtils.hideSoft(BeOccuMainPartOutAc.this,codeEt);
                if(MyUtils.isEmpty(codeEt)){
                    showTs("请先输入搜索内容");
                    return;
                }
                Intent intent=new Intent(BeOccuMainPartOutAc.this,BeOccuMainPartSearchAc.class);
                intent.putExtra("keyWords",codeEt.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void initItemUi(View serchLayoutCode) {
        ImageView codeIv=serchLayoutCode.findViewById(R.id.iv_first_icon);
        TextView codeTv=serchLayoutCode.findViewById(R.id.tv_search_name);
        codeEt=serchLayoutCode.findViewById(R.id.et_search);
        codeIv.setImageResource(R.mipmap.sear_be);
        codeTv.setText("从业主体");
        codeEt.setHint("输入主体名称");
        codeEt.setImeOptions(EditorInfo.IME_ACTION_DONE);
        codeEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
    }

    @Override
    protected void initFirst() {

    }
}
