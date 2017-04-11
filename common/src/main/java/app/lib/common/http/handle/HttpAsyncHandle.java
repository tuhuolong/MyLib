
package app.lib.common.http.handle;

import java.lang.ref.WeakReference;

import okhttp3.Call;

/**
 * Created by chenhao on 17/4/11.
 */

public class HttpAsyncHandle {
    final private WeakReference<Call> mCall;

    public HttpAsyncHandle(Call call) {
        mCall = new WeakReference<>(call);
    }

    public void cancel() {
        Call call = mCall.get();
        if (call != null) {
            call.cancel();
        }
    }
}
