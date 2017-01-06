
package com.chenhao.plugin.demo;

import android.content.Context;
import android.os.Bundle;

import app.lib.plugin.sdk.IMessageReceiver;
import app.lib.plugin.sdk.PluginContext;
import app.lib.plugin.sdk.PluginHostApi;

/**
 * Created by chenhao on 17/1/6.
 */

public class MessageReceiver implements IMessageReceiver {
    @Override
    public boolean handleMessage(Context context, PluginContext pluginContext, int msgType,
            Bundle msgArg) {
        int apiLevel = PluginHostApi.getInstance().getApiLevel();
        return true;
    }
}
