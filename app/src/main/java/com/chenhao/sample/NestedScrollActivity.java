
package com.chenhao.sample;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chenhao.lib.nestedscroll.ChildWebView;
import com.chenhao.lib.nestedscroll.ParentScrollView;

/**
 * Created by chenhao on 16/12/22.
 */

public class NestedScrollActivity extends AppCompatActivity {

    private ParentScrollView mScrollView;
    private ChildWebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nestedscroll);

        mScrollView = (ParentScrollView) findViewById(R.id.parent_scrollview);
        mWebView = (ChildWebView) findViewById(R.id.child_webview);

        initWebView(mWebView);

        mWebView.loadUrl("http://home.mi.com");
    }

    private void initWebView(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setDatabaseEnabled(true);
        if (Build.VERSION.SDK_INT < 19) {
            settings.setDatabasePath(
                    "/data/data/" + webView.getContext().getPackageName() + "/databases/");
        }
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setBlockNetworkLoads(false);
        settings.setBlockNetworkImage(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 17) {
            webView.removeJavascriptInterface("searchBoxJavaBridge_");
        }

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }
}
