// IBridgeCallback.aidl
package app.lib.plugin.frame.runtime.bridge;

import app.lib.plugin.frame.runtime.bridge.BridgeError;

interface IBridgeCallback {
    void onSuccess(in Bundle result);

    void onFailure(in BridgeError error);
}
