
package app.lib.common.http;

/**
 * Created by chenhao on 17/4/11.
 */

public class Error {
    private int mCode;
    private String mDetail;

    public Error(int code, String detail) {
        mCode = code;
        mDetail = detail;
    }

    final public int getCode() {
        return mCode;
    }

    final public String getDetail() {
        return mDetail;
    }
}
