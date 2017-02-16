
package app.lib.commonui.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by chenhao on 17/2/8. <br/>
 * <br/>
 * 支持关闭侧滑 <br/>
 * <br/>
 */
public class NoScrollViewPager extends android.support.v4.view.ViewPager {

    private boolean mScrollEnabled = false;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mScrollEnabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.mScrollEnabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    /**
     * 设置关闭侧滑
     * 
     * @param enabled
     */
    public void setScrollEnabled(boolean enabled) {
        this.mScrollEnabled = enabled;
    }
}
