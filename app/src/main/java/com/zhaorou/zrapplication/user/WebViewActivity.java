package com.zhaorou.zrapplication.user;

import android.app.AlertDialog;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity {

    private static final String url = "https://oauth.taobao.com/authorize?response_type=token&client_id=23196777&state=1212&view=web";

    @BindView(R.id.web_view)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setNetworkAvailable(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(url);
        mWebView.zoomIn();

        new AlertDialog.Builder(this).setTitle("提示")
                .setMessage("使用淘宝账号登录获取淘session，长按复制后关闭界面，返回设置淘session")
                .setPositiveButton("确定", null)
                .create()
                .show();
    }
}
