package app.lib.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLDecoder;

public class WebShellActivity extends FragmentActivity {
    /**
     * 与JS的通信协议
     */
    //消息协议名
    private final static String MESSAGE_SCHEME = "xxx_scheme://";
    //消息信号量
    private final static String MESSAGE_SEMAPHORE = "_MESSAGE_SEMAPHORE_";
    //消息队列，用于url通道传输的数据
    private final static String MESSAGE_QUEUE = "_MESSAGE_QUEUE_";
    private static final String MESSAGE_SPERATOR = "_MESSAGE_SEPERATOR_";
    /**
     * JS通道标识
     */
    private static final String CHANNEL_CONSOLE_LOG = "consoleLog";
    private static final String CHANNEL_URL = "url";
    //默认使用Console.Log通道
    private static String mChannel = CHANNEL_CONSOLE_LOG;
    Context mContext;
    WebView mWebView;
    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //协议解析
            if (url.startsWith(MESSAGE_SCHEME)) {
                String body = url.replace(MESSAGE_SCHEME, "");
                if (body.startsWith(MESSAGE_SEMAPHORE)) {
                    //消息信号量
                    view.loadUrl("javascript:XXXXBridge.getMessage(\"" + mChannel + "\");");
                    return true;
                } else if (body.startsWith(MESSAGE_QUEUE)) {
                    //url通道的消息队列
                    String messageQueue = body.replace(MESSAGE_QUEUE, "");
                    try {
                        messageQueue = URLDecoder.decode(messageQueue, "UTF-8");
                        parseMessageQueue(messageQueue);
                    } catch (Exception e) {
                    }
                    return true;
                }
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView view, String url) {

        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    };

    /**
     * 解析JS消息队列
     *
     * @param messageQueue
     */
    private void parseMessageQueue(String messageQueue) {
        if (TextUtils.isEmpty(messageQueue)) {
            return;
        }

        if (messageQueue.contains(MESSAGE_SPERATOR)) {
            String[] jsMsgArray = messageQueue.split(MESSAGE_SPERATOR);
            for (int i = 0; i < jsMsgArray.length; i++) {
                parseJSMessage(jsMsgArray[i]);
            }
        } else {
            String jsMsg = messageQueue;
            parseJSMessage(jsMsg);
        }
    }


    /**
     * 通知JS运行时
     *
     * @param jsonStr
     */
    private void notifyJSRuntime(String jsonStr) {
        mWebView.loadUrl("javascript:XXXXBridge.notify(" + jsonStr + ");");
    }

    /**
     * 解析单个JS消息
     *
     * @param jsMsg
     */
    private void parseJSMessage(String jsMsg) {
        if (!TextUtils.isEmpty(jsMsg) && JSONUtils.isJSON(jsMsg)) {
//            //创建命令
//            BaseCommand command = CommandSimpleFactory.createCommand(mComId,
//                    BaseCommand.resolveEventNameFromJSMessage(jsMsg));
//            if (command == null) {
//                return;
//            }
//            //初始化
//            command.init(mComId, this, jsMsg, mHandler);
//            //执行
//            command.execute();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        setContentView(R.layout.activity_webshell);

        mWebView = (WebView) findViewById(R.id.webview);

        initWebView();

        mWebView.loadUrl("http://www.baidu.com");
    }

    private void initWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        }
        mWebView.removeJavascriptInterface("accessibility");
        mWebView.removeJavascriptInterface("accessibilityTraversal");

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccess(true);

        mWebView.setFocusable(true);
        mWebView.setFocusableInTouchMode(true);
        mWebView.requestFocusFromTouch();
        mWebView.requestFocus();
        mWebView.setVisibility(View.VISIBLE);

        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                try {
                    intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
