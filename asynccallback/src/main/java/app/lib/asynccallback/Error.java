
package app.lib.asynccallback;

/**
 * Created by chenhao on 16/12/22.
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

    @Override
    public String toString() {
        return "Error{" +
                "mCode=" + mCode +
                ", mDetail='" + mDetail + '\'' +
                '}';
    }
}
