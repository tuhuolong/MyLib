
package app.lib.commonui.tabpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by chenhao on 17/2/8. <br/>
 * <br/>
 * 支持关闭侧滑 <br/>
 * <br/>
 */
public class ViewPager extends android.support.v4.view.ViewPager {

    private boolean mPagingEnabled = true;

    public ViewPager(Context context) {
        super(context);
    }

    public ViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mPagingEnabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.mPagingEnabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    /**
     * 设置关闭侧滑
     * 
     * @param enabled
     */
    public void setPagingEnabled(boolean enabled) {
        this.mPagingEnabled = enabled;
    }
}
