
package app.lib.plugin.sdk;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by chenhao on 16/12/24.
 */

public interface IMessageReceiver {

    /**
     * 所有插件必须实现该接口 ApiLevel:1
     */
    public boolean handleMessage(Context context, PluginContext pluginContext, int msgType,
            Bundle param);
}
