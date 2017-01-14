
package com.chenhao.plugin.demo;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import app.lib.plugin.sdk.activity.PluginBaseActivity;

/**
 * Created by chenhao on 17/1/9.
 */

public class DemoMainActivity extends PluginBaseActivity {

    WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ((TextView) findViewById(R.id.version)).setText(
                getPluginContext().getPluginId() + ":" + getPluginContext().getVersionCode());
        mWebView = (WebView) findViewById(R.id.webview);

        initWebView();

        mWebView.loadUrl("https://www.baidu.com");
    }

    void initWebView() {
        WebSettings settings = mWebView.getSettings();

        settings.setLoadsImagesAutomatically(true);
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);// 使用LocalStorage则必须打开
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }
}
