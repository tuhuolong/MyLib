
package app.lib.common.http.async;

import java.io.IOException;

import app.lib.common.http.Error;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by chenhao on 17/4/11.
 */

public abstract class TextAsyncHandler extends AsyncHandler<String, Error> {

    @Override
    final public void processResponse(Response response) {
        if (response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            String responseStr = "";
            try {
                responseStr = responseBody.string();
            } catch (Exception e) {
                sendFailureMessage(new Error(response.code(), ""), e, response);
                return;
            }

            sendSuccessMessage(responseStr, response);
        } else {
            sendFailureMessage(new Error(response.code(), ""), null, response);
        }
    }

    @Override
    final public void processFailure(Call call, IOException e) {
        sendFailureMessage(new Error(-1, ""), e, null);
    }

    @Override
    public abstract void onSuccess(String result, Response response);

    @Override
    public abstract void onFailure(Error error, Exception e, Response response);

}
