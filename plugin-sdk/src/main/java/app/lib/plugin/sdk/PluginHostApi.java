
package app.lib.plugin.sdk;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import app.lib.plugin.sdk.activity.PluginBaseActivity;

/**
 * Created by chenhao on 16/12/24.
 */
public abstract class PluginHostApi {

    private static PluginHostApi sInstance = null;

    protected PluginHostApi() {
        sInstance = this;
    }

    public static PluginHostApi getInstance() {
        return sInstance;
    }

    /**
     * ApiLevel: 1
     */
    public abstract int getApiLevel();

    /**
     * ApiLevel: 1
     */
    public abstract Application getApplication();

    /**
     * ApiLevel: 1
     */
    public abstract Context getAppContext();

    /**
     * ApiLevel: 1 获取app versionCode
     *
     * @return
     */
    public abstract int getAppVersionCode();

    /**
     * ApiLevel: 1 获取app versionName
     *
     * @return
     */
    public abstract String getAppVersionName();

    public abstract void startActivity(Context context, PluginContext pluginContext,
            Class<? extends PluginBaseActivity> activityClass, Intent intent);

    public abstract void startActivityForResult(Context context, PluginContext pluginContext,
            Class<? extends PluginBaseActivity> activityClass, Intent intent, int requestCode);

    public abstract void loadLibrary(PluginContext pluginContext, String libName);

}
