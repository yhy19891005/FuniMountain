package com.house.enquiry.ui.be_most_new_house_building_search;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.business.enquiry.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.house.enquiry.modle.MostNewSearchInfo;
import com.house.enquiry.ui.SearchHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import yin.deng.dyutils.base.SuperBaseActivity;
import yin.deng.dyutils.config.NetConfig;
import yin.deng.dyutils.http.BaseHttpInfo;
import yin.deng.dyutils.refresh.SmartRefreshLayout;
import yin.deng.dyutils.utils.LogUtils;
import yin.deng.dyutils.utils.MyUtils;
import yin.deng.dyutils.utils.NoDoubleClickListener;
import yin.deng.dyutils.utils.ScreenUtils;
import yin.deng.dyutils.web.NormalWebAc;
import yin.deng.dyutils.web.WebUrlConfig;

public class MostNewHouseBuildingSearchAc extends SuperBaseActivity<MostNewSearchInfo.ResultBean.ListBean> {
    TextView tvTitle;
    LinearLayout llSearchContainer;
    EditText etSearch;
    ImageView ivDelete;
    SmartRefreshLayout smRf;
    RecyclerView rcView;
    TextView tvLineTitle;
    NewHouseAdapter adapter;
    private int page;
    public String keyWords;
    public List<String> searchKeyWords=new ArrayList<>();
    private SmartRefreshLayout smRfSearchHistory;
    private RecyclerView rcViewSearchHistory;
    private SearchHistoryAdapter historyAdapter;
    private int maxSize=3;

    @Override
    public int setLayout() {
        return R.layout.most_new_house_search_ac;
    }

    @Override
    public void onMsgHere(BaseHttpInfo info) {
        if(info instanceof MostNewSearchInfo){
            if(smRfSearchHistory.getVisibility()==View.VISIBLE){
                smRfSearchHistory.setVisibility(View.GONE);
            }
            MostNewSearchInfo searchInfo= (MostNewSearchInfo) info;
            LogUtils.d("获取到数据："+searchInfo.toString());
            if(searchInfo!=null&&searchInfo.getResult()!=null&&searchInfo.getResult().getList()!=null) {
                if (smState == REFRESH) {
                    smRf.finishRefresh();
                    _infos.clear();
                } else {
                    smRf.finishLoadmore();
                }
                _infos.addAll(searchInfo.getResult().getList());
            }else{
                if (smState == REFRESH) {
                    smRf.finishRefresh();
                } else {
                    smRf.finishLoadmore();
                    smRf.setLoadmoreFinished(true);
                }
            }
            setAdapter();
        }
    }


    boolean isEditActionDoing=false;
    boolean isTextChangeSearch=false;
    @Override
    public void bindViewWithId() {
        initSearchKeyWords();
        smRfSearchHistory=findViewById(R.id.smRf_search_history);
        rcViewSearchHistory=findViewById(R.id.rcView_search_history);
        tvLineTitle=findViewById(R.id.tv_line_title);
        tvLineTitle.setVisibility(View.GONE);
        tvTitle = findViewById(R.id.tvTitle);
        etSearch = findViewById(R.id.et_search);
        ivDelete = findViewById(R.id.iv_delete);
        llSearchContainer = findViewById(R.id.ll_et_root);
        rcView=findViewById(R.id.rcView);
        smRf=findViewById(R.id.smRf);
        tvTitle.setText("新房楼盘");
        etSearch.setHint("输入楼盘名称");
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH) {
                    smState = REFRESH;
                    keyWords=etSearch.getText().toString();
                    sendMsgBase();
                    if(smRfSearchHistory.getVisibility()==View.VISIBLE){
                        smRfSearchHistory.setVisibility(View.GONE);
                    }
                    ScreenUtils.hideSoft(MostNewHouseBuildingSearchAc.this, etSearch);
                    isEditActionDoing=true;
                }
                return false;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    if(ivDelete.getVisibility()==View.GONE) {
                        ivDelete.setVisibility(View.VISIBLE);
                    }
                    smState=REFRESH;
                    keyWords=s.toString();
                    isEditActionDoing=true;
                    isTextChangeSearch=true;
                    sendMsgBase();
                }else{
                    if(ivDelete.getVisibility()==View.VISIBLE) {
                        ivDelete.setVisibility(View.GONE);
                    }
                }
            }
        });
        ivDelete.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                etSearch.setText("");
            }
        });
        etSearch.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
               setSearchHistoryListAdapter();
            }
        });
        etSearch.requestFocus();
    }

    public void setSearchHistoryListAdapter(){
        if(searchKeyWords==null||searchKeyWords.size()==0){
            if(smRfSearchHistory.getVisibility()==View.VISIBLE){
                smRfSearchHistory.setVisibility(View.GONE);
            }
            return;
        }
        if(historyAdapter==null){
            historyAdapter=new SearchHistoryAdapter(R.layout.history_pop_item,searchKeyWords);
            try {
                historyAdapter.setClearClickListener(new SearchHistoryAdapter.OnClearClickListener() {
                    @Override
                    public void onClear() {
                        //清理所有历史记录
                            searchKeyWords.clear();
                            clearSearchKeyWords();
                            etSearch.setText("");
                            smRfSearchHistory.setVisibility(View.GONE);
                    }
                    @Override
                    public void onItemClick(String s){
                        smRfSearchHistory.setVisibility(View.GONE);
                        etSearch.setText(s);
                        etSearch.setSelection(etSearch.getText().toString().length());
                        if (ivDelete.getVisibility() == View.GONE) {
                            ivDelete.setVisibility(View.VISIBLE);
                        }
                        smState = REFRESH;
                        keyWords = etSearch.getText().toString().trim();
                        sendMsgBase();
                        if(smRfSearchHistory.getVisibility()==View.VISIBLE){
                            smRfSearchHistory.setVisibility(View.GONE);
                        }
                        ScreenUtils.hideSoft(MostNewHouseBuildingSearchAc.this, etSearch);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MostNewHouseBuildingSearchAc.this);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            rcViewSearchHistory.setLayoutManager(linearLayoutManager);
            rcViewSearchHistory.setAdapter(historyAdapter);
        }else{
            historyAdapter.notifyDataSetChanged();
        }
        if(!isEditActionDoing) {
            smRfSearchHistory.setVisibility(View.VISIBLE);
        }
        isEditActionDoing=false;
    }

    private void clearSearchKeyWords(){
        getSharedPreferenceUtil().saveObject(null,"searchHistory_MostNew");
        initSearchKeyWords();
        setSearchHistoryListAdapter();
    }

    private void initSearchKeyWords() {
        searchKeyWords.clear();
        SearchHistoryList list= (SearchHistoryList) getSharedPreferenceUtil().getObject("searchHistory_MostNew");
        if(list!=null&&list.getHistorys()!=null&&list.getHistorys().size()>0) {
            for(int i=0;i<list.getHistorys().size();i++){
                if(MyUtils.isEmpty(list.getHistorys().get(i))){
                    list.getHistorys().remove(i);
                }
            }
            searchKeyWords.addAll(list.getHistorys());
        }
        getSharedPreferenceUtil().saveObject(list,"searchHistory_MostNew");
    }

    private void saveSearchHistory(String s){
        SearchHistoryList list= (SearchHistoryList) getSharedPreferenceUtil().getObject("searchHistory_MostNew");
        if(list!=null&&list.getHistorys()!=null&&list.getHistorys().size()>0) {
            for(int i=0;i<list.getHistorys().size();i++){
                String history=list.getHistorys().get(i);
                if(history.equals(s)|| MyUtils.isEmpty(s)|| MyUtils.isEmpty(s.trim())){
                    return;
                }
            }
            if(list.getHistorys().size()>maxSize){
                list.getHistorys().remove(maxSize-1);
            }
            list.getHistorys().add(0,s);
        }else{
            list=new SearchHistoryList();
            list.getHistorys().add(s);
        }
        getSharedPreferenceUtil().saveObject(list,"searchHistory_MostNew");
        initSearchKeyWords();
    }

    @Override
    public void sendMsgBase() {
        if(!isTextChangeSearch) {
            saveSearchHistory(etSearch.getText().toString().trim());
        }
        isTextChangeSearch=false;
        if(smState==REFRESH){
            page=1;
        }else{
            page++;
        }
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("page",String.valueOf(page));
        jsonObject.addProperty("limit",String.valueOf(10));
        jsonObject.addProperty("keyword",keyWords);
        getHttpUtils().sendMsgPost(NetConfig.MOST_NEW_HOUSE_LIST_SEARCH_URL,jsonObject,MostNewSearchInfo.class);
    }

    @SuppressLint("WrongConstant")
    public void setAdapter(){
        if(adapter==null){
            LinearLayoutManager manager=new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rcView.setLayoutManager(manager);
            adapter=new NewHouseAdapter(R.layout.new_house_item_search,_infos,this);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
               @Override
               public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                   Intent intent=new Intent(MostNewHouseBuildingSearchAc.this, NormalWebAc.class);
                   intent.putExtra("title","楼盘详情");
                   intent.putExtra("url", WebUrlConfig.MOST_NEW_HOUSE_BUILDING_SEARCH_URL+_infos.get(position).getId());
                   startActivity(intent);
               }
           });
            rcView.setAdapter(adapter);
            adapter.bindToRecyclerView(rcView);
            adapter.setEmptyView(R.layout.be_no_data);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initFirst() {
        sendMsgBase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
