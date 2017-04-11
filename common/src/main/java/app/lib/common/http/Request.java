
package app.lib.common.http;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenhao on 17/4/11.
 */

public class Request {
    public final static String METHOD_GET = "GET";
    public final static String METHOD_POST = "POST";

    private String mMethod;
    private String mUrl;
    private Map<String, String> mHeaders;
    private List<KeyValuePair> mQueryParams;

    public Request(Builder builder) {
        mMethod = builder.method;
        mUrl = builder.url;
        mHeaders = builder.headers;
        mQueryParams = builder.queryParams;
    }

    public String getUrl() {
        return mUrl;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public String getMethod() {
        if (TextUtils.isEmpty(mMethod)) {
            throw new IllegalArgumentException("method == null");
        }
        return mMethod;
    }

    public List<KeyValuePair> getQueryParams() {
        return mQueryParams;
    }

    public static class Builder {
        private String method;
        private String url;
        private Map<String, String> headers = new HashMap<>();
        private List<KeyValuePair> queryParams = new ArrayList<>(8);

        public Builder method(String method) {
            if (method == null) {
                throw new IllegalArgumentException("method == null");
            }
            this.method = method;
            return this;
        }

        public Builder url(String url) {
            if (url == null) {
                throw new IllegalArgumentException("url == null");
            }
            this.url = url;
            return this;
        }

        public Builder addHeaders(Map<String, String> headers) {
            if (headers == null) {
                throw new IllegalArgumentException("headers == null");
            }
            this.headers = headers;
            return this;
        }

        public Builder addQueries(List<KeyValuePair> queries) {
            if (queries == null) {
                throw new IllegalArgumentException("queries == null");
            }
            this.queryParams = queries;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
