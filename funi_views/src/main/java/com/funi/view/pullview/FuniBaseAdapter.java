package com.funi.view.pullview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;


import java.util.ArrayList;

/**
 * adapter 基类
 */
public abstract class FuniBaseAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected LayoutInflater mInflater;
    public ArrayList<T> listItems;        //列表数据源

    public FuniBaseAdapter() {

    }

    public FuniBaseAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public FuniBaseAdapter(Context context, ArrayList<T> listItems) {
        this(context);
        this.listItems = listItems;
    }

    //初始化时先绑定数据源为空List然后调用此方法进行数据刷新可提高加载速度
    public void setListItems(ArrayList<T> listItems) {
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        if (listItems != null) {
            return listItems.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (null != listItems && listItems.size() > 0) {
            return listItems.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
