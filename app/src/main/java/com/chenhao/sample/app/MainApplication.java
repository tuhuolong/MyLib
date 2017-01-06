
package com.chenhao.sample.app;

import android.app.Application;

import app.lib.plugin.frame.Plugin;

/**
 * Created by chenhao on 16/12/24.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Plugin.getInstance().start(this);
    }
}
