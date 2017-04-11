
package app.lib.common.http.util;

import java.util.Map;

import okhttp3.Headers;

/**
 * Created by chenhao on 17/4/11.
 */

public class HeaderUtil {
    public static Headers getHeaders(Map<String, String> headers) {
        if (headers == null || headers.isEmpty()) {
            return null;
        }
        return Headers.of(headers);
    }

    public static void checkName(String name) throws Exception {
        if (name == null)
            throw new IllegalArgumentException("name == null");
        if (name.isEmpty())
            throw new IllegalArgumentException("name is empty");
        for (int i = 0, length = name.length(); i < length; i++) {
            char c = name.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                throw new IllegalArgumentException(String.format(
                        "Unexpected char %#04x at %d in header name: %s", (int) c, i, name));
            }
        }
    }

    public static void checkValue(String value) throws Exception {
        if (value == null)
            throw new IllegalArgumentException("value == null");
        for (int i = 0, length = value.length(); i < length; i++) {
            char c = value.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                throw new IllegalArgumentException(String.format(
                        "Unexpected char %#04x at %d in header value: %s", (int) c, i, value));
            }
        }
    }
}
