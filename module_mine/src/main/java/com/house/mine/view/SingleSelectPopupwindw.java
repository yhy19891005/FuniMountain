package com.house.mine.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.house.mine.R;
import com.house.mine.adapter.SingleSelectAdapter;
import com.house.mine.util.DensityUtil;

import java.util.List;

public class SingleSelectPopupwindw<T> extends PopupWindow implements AdapterView.OnItemClickListener {

    private Context                mContext;
    private ListView               listView;
    private SingleSelectAdapter<T> mAdapter;

    public SingleSelectPopupwindw(Context context,SingleSelectAdapter<T> adapter) {
        super(context);
        mContext = context;
        mAdapter = adapter;
        View layout = View.inflate(context, R.layout.popupwindow_single_selection, null);
        listView = layout.findViewById(R.id.listView);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);

        super.setContentView(layout);
        super.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        super.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        super.setOutsideTouchable(true);
        super.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        super.update();
    }

    public void setData(List<T> data) {
        mAdapter.setData(data);
    }

    public void addData(List<T> data){
        mAdapter.addData(data);
    }

    public void setHeight(int heightDp) {
        super.setHeight(DensityUtil.dp2px(mContext, heightDp));
        super.update();
    }

    public void show(View relativeView) {
        show(relativeView, 0);
    }

    public void show(View relativeView, int widthDp) {
        show(relativeView, widthDp, 0, 0);
    }

    public void show(View relativeView, int widthDp, int xoff, int yoff) {
        if (widthDp <= 0) super.setWidth(relativeView.getWidth());
        else super.setWidth(DensityUtil.dp2px(mContext, widthDp));

        super.update();
        super.showAsDropDown(relativeView, xoff, yoff);
        super.setFocusable(true);
        super.update();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        setFocusable(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListener != null) {
            mListener.afterChooseItem(mAdapter.getData().get(position));
        }
        dismiss();
    }

    private ChooseItemListener mListener;

    public void setChooseItemListener(ChooseItemListener listener){
        mListener = listener;
    }

    /**
     * 回调
     */
    public interface ChooseItemListener<T> {
        void afterChooseItem(T t);
    }

}
