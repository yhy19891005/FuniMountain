package com.funi.view.webview;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.funi.view.R;
import com.funi.view.activity.BaseNormalActivity;

import java.util.Locale;

import androidx.annotation.NonNull;
import yin.deng.dyutils.refresh.api.RefreshLayout;
import yin.deng.dyutils.refresh.listener.OnRefreshListener;
import yin.deng.dyutils.refresh.util.DensityUtil;

/**
 * 网页
 */
public class MyWebviewActivity extends BaseNormalActivity {
    @Override
    protected void needLoading() {

    }

    @Override
    protected void setLayoutId() {
        layoutId = R.layout.my_web_view_activity;
    }

    @Override
    protected void queryData() {

    }

    public static final String TITLE = "info_title";
    public static final String URL = "info_url";

    @Override
    protected void onCreate() {
        setNavigationBarStyle(getIntent().getStringExtra(TITLE));

        final WebView webView = findViewById(R.id.webView);
        final RefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                webView.loadUrl("http://demo.funi365.com/v3/masWap/demo/news_detail.shtml?id=4");
            }
        });
        refreshLayout.autoRefresh();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            @SuppressWarnings("deprecation")
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                refreshLayout.finishRefresh();
                view.loadUrl(String.format(Locale.CHINA, "javascript:document.body.style.paddingTop='%fpx'; void 0", DensityUtil.px2dp(webView.getPaddingTop())));
            }
        });
    }
}
