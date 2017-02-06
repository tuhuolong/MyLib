
package app.lib.asynccallback;

import java.lang.ref.WeakReference;

/**
 * Created by chenhao on 16/12/22.
 */

public class AsyncHandle<H> {

    final private WeakReference<H> mHandle;

    public AsyncHandle(H handle) {
        mHandle = new WeakReference<>(handle);
    }

    final public void cancel() {
        if (mHandle == null) {
            return;
        }

        H handle = mHandle.get();
        if (handle != null) {
            if (handle instanceof Handle) {
                ((Handle) handle).cancel();
            }
        }
    }

    public interface Handle {
        void cancel();
    }

}
