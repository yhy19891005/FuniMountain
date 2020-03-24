package com.funi.view.pullview.internal;

import android.view.View;

/**
 * Interface that allows PullToRefreshBase to hijack the call to
 * AdapterView.setEmptyView()
 *
 * @author chris
 */
public interface EmptyViewMethodAccessor {

    /**
     * Calls upto AdapterView.setEmptyView()
     * <p/>
     * to set as Empty View
     */
    public void setEmptyViewInternal(View emptyView);

    /**
     * Should call PullToRefreshBase.setEmptyView() which will then
     * automatically call through to setEmptyViewInternal()
     * <p/>
     * to set as Empty View
     */
    public void setEmptyView(View emptyView);

}
