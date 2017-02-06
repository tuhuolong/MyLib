
package com.chenhao.sample.app;

import android.app.Application;
import android.content.Context;

import app.lib.plugin.frame.Plugin;

/**
 * Created by chenhao on 16/12/24.
 */

public class MainApplication extends Application {

    static Application sApplication;

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getAppContext() {
        return sApplication.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sApplication = this;

        Plugin.getInstance().start(this, true);
    }
}
