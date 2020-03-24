package com.house.enquiry.ui;

import android.view.View;

import com.business.enquiry.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;
import yin.deng.dyutils.utils.NoDoubleClickListener;

public class SearchHistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    List<String> datas;
    public SearchHistoryAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        datas=data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final String item) {
       helper.setText(R.id.tv_history_text,item);
       helper.getView(R.id.ll_item_root).setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if(clearClickListener!=null){
                    clearClickListener.onItemClick(item);
                }
            }
        });
       if(helper.getAdapterPosition()==datas.size()-1){
           helper.getView(R.id.tv_history_clear).setVisibility(View.VISIBLE);
           helper.getView(R.id.tv_history_clear).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                 if(clearClickListener!=null){
                     clearClickListener.onClear();
                 }
               }
           });
       }else{
           helper.getView(R.id.tv_history_clear).setVisibility(View.GONE);
       }
    }

    OnClearClickListener clearClickListener;
    public void setClearClickListener(OnClearClickListener clearClickListener){
        this.clearClickListener=clearClickListener;
    }
    public interface OnClearClickListener{
        void onClear();
        void onItemClick(String item);
    }

}

