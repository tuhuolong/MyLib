
package app.lib.common.http.sync;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by chenhao on 17/4/11.
 */
public class TextSyncHandler extends SyncHandler<String> {
    @Override
    public String processResponse(Response response) throws Exception {
        if (response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            String responseStr = "";
            try {
                responseStr = responseBody.string();

                return responseStr;
            } catch (Exception e) {
                throw e;
            }
        } else {
            throw new RuntimeException("failure code:" + response.code());
        }
    }
}
