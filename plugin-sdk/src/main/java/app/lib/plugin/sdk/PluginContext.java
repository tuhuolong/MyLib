
package app.lib.plugin.sdk;

import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * Created by chenhao on 16/12/24.
 */

public class PluginContext {
    private String mPluginId;

    private int mVersionCode;

    private DexClassLoader mClassLoader;

    private AssetManager mAssetManager;

    private Resources mResources;

    private IMessageReceiver mMessageReceiver;

    public PluginContext(String pluginId, int versionCode, DexClassLoader loader,
            AssetManager assetManager, Resources resources, IMessageReceiver messageReceiver) {
        mPluginId = pluginId;
        mVersionCode = versionCode;
        mClassLoader = loader;
        mAssetManager = assetManager;
        mResources = resources;
        mMessageReceiver = messageReceiver;
    }

    public synchronized DexClassLoader getClassLoader() {
        return mClassLoader;
    }

    public synchronized AssetManager getAssetManager() {
        return mAssetManager;
    }

    public synchronized Resources getResources() {
        return mResources;
    }

    public synchronized IMessageReceiver getMessageReceiver() {
        return mMessageReceiver;
    }

    public synchronized String getPluginId() {
        return mPluginId;
    }

    public synchronized void setPluginId(String pluginId) {
        mPluginId = pluginId;
    }

    public synchronized int getVersionCode() {
        return mVersionCode;
    }

    public synchronized void setVersionCode(int versionCode) {
        mVersionCode = versionCode;
    }
}
