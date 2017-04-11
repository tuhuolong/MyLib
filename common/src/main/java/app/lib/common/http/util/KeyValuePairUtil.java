
package app.lib.common.http.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import app.lib.common.http.KeyValuePair;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by chenhao on 17/4/11.
 */

public class KeyValuePairUtil {
    private static final String PARAMETER_SEPARATOR = "&";
    private static final String NAME_VALUE_SEPARATOR = "=";

    public static String getUrlWithQueryString(String url, List<KeyValuePair> params) {
        if (params != null && !params.isEmpty()) {
            String queryString = format(params, "UTF-8");
            if (!url.contains("?")) {
                url += "?" + queryString;
            } else {
                url += "&" + queryString;
            }
        }

        return url;
    }

    private static String format(final List<? extends KeyValuePair> parameters,
            final String encoding) {
        final StringBuilder result = new StringBuilder();
        for (final KeyValuePair parameter : parameters) {
            final String encodedName = encode(parameter.getKey(), encoding);
            final String value = parameter.getValue();
            final String encodedValue = value != null ? encode(value, encoding) : "";
            if (result.length() > 0)
                result.append(PARAMETER_SEPARATOR);
            result.append(encodedName);
            result.append(NAME_VALUE_SEPARATOR);
            result.append(encodedValue);
        }
        return result.toString();
    }

    private static String encode(final String content, final String encoding) {
        try {
            // return URLEncoder.encode(content,
            // encoding != null ? encoding : HTTP.DEFAULT_CONTENT_CHARSET);
            return URLEncoder.encode(content, encoding != null ? encoding : "ISO-8859-1");
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalArgumentException(problem);
        }
    }

    public static RequestBody getRequestBody(List<KeyValuePair> params) {
        if (params == null || params.isEmpty()) {
            return null;
        }

        FormBody.Builder builder = new FormBody.Builder();

        for (KeyValuePair pair : params) {
            String key = pair.getKey();
            String value = pair.getValue();
            if (!TextUtils.isEmpty(key) && value != null) {
                builder.add(key, value);
            }
        }

        return builder.build();
    }
}
