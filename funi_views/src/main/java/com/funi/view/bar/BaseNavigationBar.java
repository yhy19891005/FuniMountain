package com.funi.view.bar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * AbsNavigationBar
 *
 * @Description:导航栏基类
 * @Author: pengqiang.zou
 * @CreateDate: 2018-11-30 10:19
 */
public class BaseNavigationBar<B extends BaseNavigationBar.Builder> implements INavigation {
    private B mBuilder;
    private View mNavigationBar;

    protected BaseNavigationBar(B Builder) {
        this.mBuilder = Builder;
        createNavigationBar();
    }

    @Override
    public void createNavigationBar() {
        mNavigationBar = LayoutInflater.from(mBuilder.mContext)
                .inflate(mBuilder.mLayoutId, mBuilder.mParent);

        attachParent(mNavigationBar, mBuilder.mParent);

        attachNavigationParams();
    }

    @Override
    public void attachNavigationParams() {
        // 设置文本
        Map<Integer, CharSequence> textMaps = mBuilder.mTextMaps;
        for (Map.Entry<Integer, CharSequence> entry : textMaps.entrySet()) {
            TextView textView = findViewById(entry.getKey());
            textView.setText(entry.getValue());
        }

        Map<Integer, Integer> imageMaps = mBuilder.mImageViewMaps;
        for (Map.Entry<Integer, Integer> entry : imageMaps.entrySet()) {
            ImageView imageView = findViewById(entry.getKey());
            imageView.setImageResource(entry.getValue());
        }

        // 设置点击事件
        Map<Integer, View.OnClickListener> clickListenerMaps = mBuilder.mCLickListenerMaps;
        for (Map.Entry<Integer, View.OnClickListener> entry : clickListenerMaps.entrySet()) {
            View view = findViewById(entry.getKey());
            view.setOnClickListener(entry.getValue());
        }
    }

    public <T extends View> T findViewById(int viewId) {
        return (T) mNavigationBar.findViewById(viewId);
    }


    @Override
    public void attachParent(View navigationBar, ViewGroup parent) {

    }

    /**
     * 返回 Builder
     *
     * @return
     */
    public B getBuilder() {
        return mBuilder;
    }

    /**
     * Builder构建类
     *
     * @param <B>
     */
    public static abstract class Builder<B extends Builder> {
        public Context mContext;
        public int mLayoutId;
        public ViewGroup mParent;
        public Map<Integer, CharSequence> mTextMaps;
        public Map<Integer, View.OnClickListener> mCLickListenerMaps;
        public Map<Integer, Integer> mImageViewMaps;

        public Builder(Context context, int layoutId, ViewGroup parent) {
            this.mContext = context;
            this.mLayoutId = layoutId;
            this.mParent = parent;
            mTextMaps = new HashMap<>();
            mCLickListenerMaps = new HashMap<>();
            mImageViewMaps = new HashMap<>();
        }

        public abstract BaseNavigationBar create();


        /**
         * 设置控件的显示文本
         *
         * @param viewId
         * @param text
         * @return
         */
        public B setText(int viewId, String text) {
            mTextMaps.put(viewId, text);
            return (B) this;
        }

        /**
         * 设置图片控件
         *
         * @param viewId
         * @param imageId
         * @return
         */
        public B setImageResource(int viewId, int imageId) {
            mImageViewMaps.put(viewId, imageId);
            return (B) this;
        }

        /**
         * 设置控件点击事件
         *
         * @param viewId
         * @param clickListener
         * @return
         */
        public B setOnClickListener(int viewId, View.OnClickListener clickListener) {
            mCLickListenerMaps.put(viewId, clickListener);
            return (B) this;
        }
    }
}
