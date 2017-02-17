
package app.lib.commonui.stickyball.utils;

import android.content.Context;

public class DimensionUtil {
    /**
     * px to dip
     *
     * @param context context
     * @param pxValue px
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dip to px
     *
     * @param context context
     * @param dipValue dp
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
