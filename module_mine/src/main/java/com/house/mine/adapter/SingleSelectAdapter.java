package com.house.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public abstract class SingleSelectAdapter<T> extends BaseAdapter {

    private List<T> mData = new ArrayList<>();
    private Context mContext;

    public SingleSelectAdapter(Context context) {
        mContext = context;
    }

    public List<T> getData(){
        return mData;
    }

    public void setData(List<T> data){
        if(data != null && data.size() > 0){
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> data){
        if(data != null && data.size() > 0){
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(mContext,android.R.layout.simple_list_item_1,null);
        }
        T t = mData.get(position);
        bindData(t,(TextView)convertView);
        return convertView;
    }

    public abstract void bindData(T t, TextView tv);
}
