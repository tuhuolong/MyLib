
package app.lib.common.http.async;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import app.lib.common.http.Error;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by chenhao on 17/4/11.
 */

public abstract class AsyncHandler<R, E extends Error> {

    private static final int MSG_SUCCESS = 0;
    private static final int MSG_PROGRESS = 1;
    private static final int MSG_FAILURE = 2;

    private Handler mDispatchHandler;

    public AsyncHandler() {
        Looper looper = Looper.myLooper();
        if (looper == null) {
            Log.e("AsyncHandler", "Async Callback must have Looper");
            looper = Looper.getMainLooper();
            // throw new RuntimeException("Async Callback must have Looper");
        }

        mDispatchHandler = new DispatchHandler<R, E>(this, looper);
    }

    public abstract void processResponse(Response response);

    public abstract void processFailure(Call call, IOException e);

    public abstract void onSuccess(R result, Response response);

    public void onProgress(long bytesWritten, long totalSize) {
    }

    public abstract void onFailure(Error error, Exception e, Response response);

    /**
     * 向调用线程发送成功消息
     *
     * @param result
     */
    final public void sendSuccessMessage(R result, Response reponse) {
        SuccessMessage message = new SuccessMessage();
        message.result = result;
        message.response = reponse;
        mDispatchHandler.sendMessage(mDispatchHandler.obtainMessage(MSG_SUCCESS, message));
    }

    /**
     * 向调用线程发送进度
     *
     * @param bytesWritten
     * @param totalSize
     */
    final public void sendProgressMessage(long bytesWritten, long totalSize) {
        ProgressMessage message = new ProgressMessage();
        message.bytesWritten = bytesWritten;
        message.totalSize = totalSize;
        mDispatchHandler.sendMessage(mDispatchHandler.obtainMessage(MSG_PROGRESS, message));
    }

    /**
     * 向调用线程发送失败消息
     *
     * @param error
     */
    final public void sendFailureMessage(E error, Exception e, Response response) {
        FailureMessage message = new FailureMessage();
        message.error = error;
        message.e = e;
        message.response = response;
        mDispatchHandler.sendMessage(mDispatchHandler.obtainMessage(MSG_FAILURE, message));
    }

    private static class SuccessMessage<R> {
        R result;
        Response response;
    }

    private static class ProgressMessage {
        long bytesWritten;
        long totalSize;
    }

    private static class FailureMessage<E extends Error> {
        E error;
        Exception e;
        Response response;
    }

    private static class DispatchHandler<R, E extends Error> extends Handler {
        private AsyncHandler<R, E> mCallback;

        DispatchHandler(AsyncHandler callback, Looper looper) {
            super(looper);

            mCallback = callback;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SUCCESS:
                    SuccessMessage<R> successMessage = (SuccessMessage<R>) msg.obj;
                    mCallback.onSuccess(successMessage.result, successMessage.response);
                    break;
                case MSG_PROGRESS:
                    ProgressMessage progressMessage = (ProgressMessage) msg.obj;
                    mCallback.onProgress(progressMessage.bytesWritten, progressMessage.totalSize);
                    break;
                case MSG_FAILURE:
                    FailureMessage<E> failureMessage = (FailureMessage<E>) msg.obj;
                    mCallback.onFailure(failureMessage.error, failureMessage.e,
                            failureMessage.response);
                    break;
            }
        }
    }
}
