
package app.lib.common.http.async;

import java.io.IOException;

import app.lib.common.http.Error;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by chenhao on 17/4/11.
 */

public abstract class BinaryAsyncHandler extends AsyncHandler<byte[], Error> {

    @Override
    public void processResponse(Response response) {
        if (response.isSuccessful()) {
            byte[] result = null;
            try {
                result = response.body().bytes();
            } catch (Exception e) {
                sendFailureMessage(new Error(response.code(), ""), e, response);
                return;
            }

            sendSuccessMessage(result, response);
        } else {
            sendFailureMessage(new Error(response.code(), ""), null, response);
        }
    }

    @Override
    public void processFailure(Call call, IOException e) {
        sendFailureMessage(new Error(-1, ""), e, null);
    }

    @Override
    public abstract void onSuccess(byte[] result, Response response);

    @Override
    public abstract void onFailure(Error error, Exception e, Response response);
}
