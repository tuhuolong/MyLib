
package app.lib.asynccallback;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Created by chenhao on 16/12/22.
 */

public abstract class AsyncCallback<R, E extends Error> {

    private static final int MSG_SUCCESS = 1;
    private static final int MSG_FAILURE = 2;

    private Handler mDispatcher;

    public AsyncCallback() {
        Looper looper = Looper.myLooper();

        if (looper == null) {
            if (!(this instanceof SyncCallback)) {
                Log.e("AsyncCallback", "async callback must have looper");
                // throw new RuntimeException("async callback must have looper");
                mDispatcher = new Dispatcher<R, E>(this, Looper.getMainLooper());
            }
        } else {
            mDispatcher = new Dispatcher<R, E>(this, looper);
        }
    }

    public abstract void onSuccess(R result);

    public abstract void onFailure(E error);

    /**
     * 向调用线程发送成功消息
     *
     * @param result
     */
    public void sendSuccessMessage(R result) {
        mDispatcher.sendMessage(mDispatcher.obtainMessage(MSG_SUCCESS, result));
    }

    /**
     * 向调用线程发送失败消息
     */
    public void sendFailureMessage(E error) {
        mDispatcher.sendMessage(mDispatcher.obtainMessage(MSG_FAILURE, error));
    }

    private static class Dispatcher<R, E extends Error> extends Handler {
        private AsyncCallback<R, E> mCallback;

        Dispatcher(AsyncCallback callback, Looper looper) {
            super(looper);

            mCallback = callback;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SUCCESS:
                    R result = (R) msg.obj;
                    mCallback.onSuccess(result);
                    break;
                case MSG_FAILURE:
                    E err = (E) msg.obj;
                    mCallback.onFailure(err);
                    break;
            }
        }
    }
}
