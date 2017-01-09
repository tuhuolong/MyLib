
package com.chenhao.plugin.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import app.lib.plugin.sdk.IMessageReceiver;
import app.lib.plugin.sdk.PluginContext;
import app.lib.plugin.sdk.PluginHostApi;
import app.lib.plugin.sdk.PluginMsgType;

/**
 * Created by chenhao on 17/1/6.
 */

public class MessageReceiver implements IMessageReceiver {
    @Override
    public boolean handleMessage(Context context, PluginContext pluginContext, int msgType,
            Bundle msgArg) {
        switch (msgType) {
            case PluginMsgType.LAUNCH:
                PluginHostApi.getInstance().startActivity(context, pluginContext,
                        DemoMainActivity.class, new Intent());
                break;
        }
        return true;
    }
}
