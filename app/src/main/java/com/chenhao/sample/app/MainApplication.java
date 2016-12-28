
package com.chenhao.sample.app;

import android.app.Application;

import com.chenhao.lib.plugin.frame.runtime.api.PluginApiImpl;

/**
 * Created by chenhao on 16/12/24.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        new PluginApiImpl();
    }
}