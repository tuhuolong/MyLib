
package app.lib.common.http;

import java.io.IOException;
import java.net.CookieManager;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import app.lib.common.http.async.AsyncHandler;
import app.lib.common.http.handle.HttpAsyncHandle;
import app.lib.common.http.util.HeaderUtil;
import app.lib.common.http.util.KeyValuePairUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.Headers;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.internal.Util;

/**
 * Created by chenhao on 17/4/11.
 */

public class HttpUtil {

    private static OkHttpClient httpClient;
    private static Object lock = new Object();

    private static OkHttpClient getClient() {
        if (httpClient == null) {
            synchronized (lock) {
                // 有可能在其他线程已创建
                if (httpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .dispatcher(
                                    new Dispatcher(new ThreadPoolExecutor(6, Integer.MAX_VALUE, 60,
                                            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
                                            Util.threadFactory("OkHttp Dispatcher", false))))
                            .connectTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .cookieJar(new JavaNetCookieJar(new CookieManager()));

                    httpClient = builder.build();
                }
            }
        }
        return httpClient;
    }

    public static HttpAsyncHandle sendRequest(Request request, final AsyncHandler callback) {

        okhttp3.Request okRequest;
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        Headers headers = HeaderUtil.getHeaders(request.getHeaders());
        if (headers != null) {
            builder.headers(headers);
        }

        if (request.getMethod().equals(Request.METHOD_POST)) {
            builder.url(request.getUrl())
                    .post(KeyValuePairUtil.getRequestBody(request.getQueryParams()));
        } else if (request.getMethod()
                .equals(Request.METHOD_GET)) {
            builder.url(KeyValuePairUtil.getUrlWithQueryString(request.getUrl(),
                    request.getQueryParams()));
        } else {
            throw new RuntimeException("method unsupported");
        }

        okRequest = builder.build();

        final Call call = getClient().newCall(okRequest);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                if (callback != null) {
                    callback.processFailure(call, e);
                }
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (callback != null) {
                    callback.processResponse(response);
                }
            }
        });

        return new HttpAsyncHandle(call);
    }

    public static HttpAsyncHandle sendRequest(OkHttpClient client, Request request,
            final AsyncHandler callback) {
        if (client == null) {
            throw new RuntimeException("client is null");
        }

        okhttp3.Request okRequest;
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        Headers headers = HeaderUtil.getHeaders(request.getHeaders());
        if (headers != null) {
            builder.headers(headers);
        }

        if (request.getMethod().equals(Request.METHOD_POST)) {
            builder.url(request.getUrl())
                    .post(KeyValuePairUtil.getRequestBody(request.getQueryParams()));
        } else if (request.getMethod()
                .equals(Request.METHOD_GET)) {
            builder.url(KeyValuePairUtil.getUrlWithQueryString(request.getUrl(),
                    request.getQueryParams()));
        } else {
            throw new RuntimeException("method unsupported");
        }

        okRequest = builder.build();

        final Call call = client.newCall(okRequest);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                if (callback != null) {
                    callback.processFailure(call, e);
                }
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (callback != null) {
                    callback.processResponse(response);
                }
            }
        });

        return new HttpAsyncHandle(call);
    }
}
