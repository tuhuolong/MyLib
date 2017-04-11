
package app.lib.common.http;

/**
 * Created by chenhao on 17/4/11.
 */

public class KeyValuePair {
    private final String key;
    private final String value;

    public KeyValuePair(final String key, final String value) {
        if (key == null) {
            throw new IllegalArgumentException("Key may not be null");
        }
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
