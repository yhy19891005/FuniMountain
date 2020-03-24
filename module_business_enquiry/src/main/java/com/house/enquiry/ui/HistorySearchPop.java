package com.house.enquiry.ui;

import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.business.enquiry.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import yin.deng.dyutils.base.SuperBaseActivity;
import yin.deng.dyutils.refresh.SmartRefreshLayout;

public class HistorySearchPop {
    private SuperBaseActivity mContext;
    private  PopupWindow popupWindow;
    private View contentView;
    private HistorySearchPop.ViewHolder holder;
    List<String>datas=new ArrayList<>();
    SearchHistoryAdapter adapter;
    public HistorySearchPop(SuperBaseActivity mContext) {
        this.mContext = mContext;
    }


    public View getContentView() {
        return contentView;
    }

    public HistorySearchPop showPopupWindow(View view, List<String>datas) {
        if(popupWindow==null) {
            // 一个自定义的布局，作为显示的内容
            contentView = LayoutInflater.from(mContext).inflate(
                    R.layout.history_search_popwindow_layout, null);
            popupWindow = new PopupWindow(contentView,
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
            popupWindow.setTouchable(true);
            holder = new HistorySearchPop.ViewHolder(contentView);
            holder.smRf.setEnableRefresh(false);
            holder.smRf.setEnableLoadmore(false);
            //在PopupWindow里面就加上下面代码，让键盘弹出时，不会挡住pop窗口。
            popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            //点击空白处时，隐藏掉pop窗口
            popupWindow.setFocusable(false);
            popupWindow.setTouchInterceptor(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                    // 这里如果返回true的话，touch事件将被拦截
                    // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                }
            });

            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
            // 我觉得这里是API的一个bug
            popupWindow.setBackgroundDrawable(null);
            // 设置好参数之后再show
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                    dismissPop();
                }
            });
            backgroundAlpha(1f);
        }
        this.datas.clear();
        this.datas.addAll(datas);
        setAdapter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            popupWindow.showAsDropDown(view, -20, 0, Gravity.RIGHT);
        }
        return this;
    }

    private void setAdapter() {
        if(adapter==null){
            LinearLayoutManager manager=new LinearLayoutManager(mContext);
            manager.setOrientation(RecyclerView.VERTICAL);
            holder.rcView.setLayoutManager(manager);
            adapter=new SearchHistoryAdapter(R.layout.history_pop_item,datas);
            holder.rcView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    public void setFocleAble(){
        if(popupWindow!=null) {
            popupWindow.setFocusable(true);
        }
    }


    public SearchHistoryAdapter getHistoryAdapter() throws Exception {
        if(popupWindow==null){
            throw new Exception("请先执行showPopupWindow获取对象");
        }
        return adapter;
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mContext.getWindow().setAttributes(lp);
    }


    public  void dismissPop() {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    static class ViewHolder {
        private SmartRefreshLayout smRf;
        private RecyclerView rcView;




        ViewHolder(View view) {
            smRf = (SmartRefreshLayout) view.findViewById(R.id.smRf);
            rcView = (RecyclerView) view.findViewById(R.id.rcView);
        }
    }
}
