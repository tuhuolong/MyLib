
package app.lib.common.http.sync;

import okhttp3.Response;

/**
 * Created by chenhao on 17/4/11.
 */

public abstract class SyncHandler<T> {
    public abstract T processResponse(Response response) throws Exception;
}
