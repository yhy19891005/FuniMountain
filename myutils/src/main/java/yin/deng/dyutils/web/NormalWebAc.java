package yin.deng.dyutils.web;

import android.app.Instrumentation;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cunoraz.gifview.library.GifView;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import yin.deng.dyutils.R;
import yin.deng.dyutils.base.SuperBaseActivity;
import yin.deng.dyutils.http.BaseHttpInfo;
import yin.deng.dyutils.utils.LogUtils;

/**
 * 通用加载H5的腾讯X5内核浏览器界面
 */
public class NormalWebAc extends SuperBaseActivity {
    public static final String TITLE = "title";
    public static final String URL = "url";
    X5WebView x5webView;
    private TextView tvTitle;
    private LinearLayout llTitleRoot;
    private LinearLayout linearBar;
    private LinearLayout titleBar;
    private FrameLayout fmLeft;
    private ImageView ivLeft;
    private TextView tvLeft;
    private FrameLayout fmRight;
    private ImageView ivRight;
    private TextView tvRight;
    private TextView tvLineTitle;
    private FrameLayout fmLoading;
    private GifView gifView;


    @Override
    public int setLayout() {
        return R.layout.normal_web_activity;
    }

    @Override
    public void onMsgHere(BaseHttpInfo info) {

    }

    @Override
    public void bindViewWithId() {
        x5webView=findViewById(R.id.x5web_view);
        tvTitle=findViewById(R.id.tvTitle);
        llTitleRoot = (LinearLayout) findViewById(R.id.ll_title_root);
        linearBar = (LinearLayout) findViewById(R.id.linear_bar);
        titleBar = (LinearLayout) findViewById(R.id.title_bar);
        fmLeft = (FrameLayout) findViewById(R.id.fm_left);
        ivLeft = (ImageView) findViewById(R.id.iv_left);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        fmRight = (FrameLayout) findViewById(R.id.fm_right);
        ivRight = (ImageView) findViewById(R.id.iv_right);
        tvRight = (TextView) findViewById(R.id.tv_right);
        tvLineTitle = (TextView) findViewById(R.id.tv_line_title);
        x5webView = (X5WebView) findViewById(R.id.x5web_view);
        fmLoading = (FrameLayout) findViewById(R.id.fm_loading);
        gifView=findViewById(R.id.gif_view);
        fmLoading.setVisibility(View.VISIBLE);
        gifView.setGifResource(R.drawable.data_loading);
        gifView.play();
        WebViewClient client = new WebViewClient() {
            /**
             * 防止加载网页时调起系统浏览器
             */
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
//                if(fmLoading.getVisibility()==View.GONE){
//                    fmLoading.setVisibility(View.VISIBLE);
//                    gifView.setGifResource(R.drawable.data_loading);
//                    gifView.play();
//                }
                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                if(fmLoading.getVisibility()==View.VISIBLE) {
                    fmLoading.setVisibility(View.GONE);
                }
            }
        };
        x5webView.setWebViewClient(client);
        //初始化x5浏览器生命周期
        initX5WebLive();

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        try {
                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        } catch (Exception e) {
                            Log.e("dddd",e.toString());
                        }
                    }
                }.start();
            }
        });
    }

    @Override
    protected void initFirst() {
        String title=getIntent().getStringExtra("title");
        String loadUrl=getIntent().getStringExtra("url");

        LogUtils.d("当前页面地址："+loadUrl);
        tvTitle.setText(title);
        x5webView.loadUrl(loadUrl);
    }

}
