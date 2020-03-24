package com.funi.view.pullview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * 上下拉动list刷新
 * 步骤：（可参照DemoActivity）
 * 1.在onCreate里面初始化list的adapter
 * 2.实现PullListViewService接口重写getServiceData()获取数据
 * 3.在2的onSuccess里面调用reFreshListView(list);刷新list
 * Created by LQ on 2014/4/26.
 */
public class PullListView<T> extends PullToRefreshListView {

    protected FuniBaseAdapter baseAdapter;
    protected ListView mListView;
    protected int currentPage;     //页码
    protected boolean isSuccessGetData;//是否成功获取数据
    protected PullListViewService pullListViewService;//接口

    public PullListView(Context context) {
        super(context);
    }

    public PullListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullListView(Context context, int mode){
        super(context,mode);
    }

    /**
     * 初始化拉动ListView
     */
    public void initPullRefreshPage() {
        this.setOnRefreshListener(mOnrefreshListener);
        mListView = this.getRefreshableView();
        mListView.setAdapter(baseAdapter);

        currentPage = 1;
        getServiceData();
    }

    /**
     * 滚动到顶部
     */
    public void scrollTop(){
        if (!mListView.isStackFromBottom()) {
            mListView.setStackFromBottom(true);
        }
        mListView.setStackFromBottom(false);
    }



    public void setDividerHeight(int h) {
        mListView.setDividerHeight(h);
    }

    public void setSelection(int i) {
//        mListView.setSelection(i);
//        mListView.smoothScrollToPosition(i);
        mListView.setSelection(i);
    }

    /**
     * 列表刷新动作监听
     */
    OnRefreshListener mOnrefreshListener = new OnRefreshListener() {
        public void onRefresh() {
            listViewFresh();
        }
    };

    /**
     *
     */
    private void listViewFresh() {
        if (!isSuccessGetData) {
            listDataAppend(null);
        }
        getServiceData();
    }


    protected void getServiceData() {
        if (null != pullListViewService) {
            pullListViewService.getServiceData();
        }
    }

    /**
     * 列表刷新
     */
    public void reFreshListView(ArrayList resps) {
        if (null != resps && resps.size() > 0) {
            isSuccessGetData = Boolean.TRUE;
            if (getRefreshType() == 0 || getRefreshType() == 1) {
                clearAdapterItems();
            }
            listDataAppend(resps);
        } else {
            isSuccessGetData = Boolean.FALSE;
        }
        baseAdapter.notifyDataSetChanged();
        this.onRefreshComplete();
    }

    /**
     * 列表分页页码和adapter数据源处理
     *
     * @param list
     */
    private void listDataAppend(ArrayList list) {
        int pullState = this.getRefreshType();
        if (pullState == 1 ||
                pullState == 0) {
            //下拉情况原列表数据 重新加载新数据
//            clearAdapterItems();
            baseAdapter.listItems = list;
            currentPage = 1;
        } else if (pullState == 2) {
            //上拉列表数据源追加  页码++
            if (null != list) {
                judgeEmpty();
                baseAdapter.listItems.addAll(list);
            }
            currentPage++;
        }
    }

    /**
     * 清空原数据
     */
    private void clearAdapterItems() {
        if (null != baseAdapter.listItems) {
            baseAdapter.listItems.clear();
        }
    }

    /**
     * 排除数据源对象为空
     */
    private void judgeEmpty() {
        if (null == baseAdapter.listItems) {
            baseAdapter.listItems = new ArrayList<T>();
        }
    }

    public FuniBaseAdapter getBaseAdapter() {
        return baseAdapter;
    }

    public void setBaseAdapter(FuniBaseAdapter baseAdapter) {
        this.baseAdapter = baseAdapter;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public PullListViewService getPullListViewService() {
        return pullListViewService;
    }

    public void setPullListViewService(PullListViewService pullListViewService) {
        this.pullListViewService = pullListViewService;
    }

    public void setOnItemClickListener(ListView.OnItemClickListener onItemClickListener) {
        this.getRefreshableView().setOnItemClickListener(onItemClickListener);
    }

    public void setOnItemLongClickListener(ListView.OnItemLongClickListener onItemLongClickListener) {
        this.getRefreshableView().setOnItemLongClickListener(onItemLongClickListener);
    }
}
