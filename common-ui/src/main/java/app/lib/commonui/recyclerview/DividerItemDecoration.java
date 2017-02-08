
package app.lib.commonui.recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by chenhao on 17/2/8.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    /* static section */

    public static final int DIVIDER_CONDITION_EITHER = 0;
    public static final int DIVIDER_CONDITION_BOTH = 1;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerIntrinsicHeight;

    /* non-static section */
    @DividerCondition
    private int mDividerCondition;

    public DividerItemDecoration(Drawable divider, int dividerHeight, int dividerCondition) {
        setDivider(divider);
        mDividerHeight = dividerHeight;
        mDividerCondition = dividerCondition;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mDivider == null) {
            return;
        }
        final int childCount = parent.getChildCount();
        final int width = parent.getWidth();
        final int dividerHeight = mDividerHeight != 0 ? mDividerHeight : mDividerIntrinsicHeight;
        for (int childViewIndex = 0; childViewIndex < childCount; childViewIndex++) {
            final View view = parent.getChildAt(childViewIndex);
            if (shouldDrawDividerBelow(view, parent)) {
                final int top = (int) ViewCompat.getY(view) + view.getHeight();
                mDivider.setBounds(0, top, width, top + dividerHeight);
                mDivider.draw(c);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {
        if (shouldDrawDividerBelow(view, parent)) {
            outRect.bottom = mDividerHeight != 0 ? mDividerHeight : mDividerIntrinsicHeight;
        }
    }

    private boolean shouldDrawDividerBelow(View view, RecyclerView parent) {
        final RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);
        final int index = holder.getLayoutPosition();
        final int lastItemIndex = parent.getAdapter().getItemCount() - 1;
        if ((holder instanceof DividedViewHolder)) {
            if (((DividedViewHolder) holder).isDividerAllowedBelow()) {
                if (mDividerCondition == DIVIDER_CONDITION_EITHER) {
                    // Draw the divider without consulting the next item if we only
                    // need permission for either above or below.
                    return true;
                }
            } else if (mDividerCondition == DIVIDER_CONDITION_BOTH || index == lastItemIndex) {
                // Don't draw if the current view holder doesn't allow drawing below
                // and the current theme requires permission for both the item below and above.
                // Also, if this is the last item, there is no item below to ask permission
                // for whether to draw a divider above, so don't draw it.
                return false;
            }
        }
        // Require permission from index below to draw the divider.
        if (index < lastItemIndex) {
            final RecyclerView.ViewHolder nextHolder = parent
                    .findViewHolderForLayoutPosition(index + 1);
            if ((nextHolder instanceof DividedViewHolder)
                    && !((DividedViewHolder) nextHolder).isDividerAllowedAbove()) {
                // Don't draw if the next view holder doesn't allow drawing above
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the drawable currently used as the divider.
     */
    public Drawable getDivider() {
        return mDivider;
    }

    /**
     * Sets the drawable to be used as the divider.
     */
    public void setDivider(Drawable divider) {
        if (divider != null) {
            mDividerIntrinsicHeight = divider.getIntrinsicHeight();
        } else {
            mDividerIntrinsicHeight = 0;
        }
        mDivider = divider;
    }

    /**
     * Gets the divider height, in pixels.
     */
    public int getDividerHeight() {
        return mDividerHeight;
    }

    /**
     * Sets the divider height, in pixels.
     */
    public void setDividerHeight(int dividerHeight) {
        mDividerHeight = dividerHeight;
    }

    /**
     * Gets whether the divider needs permission from both the item view holder below and above from
     * where the divider would draw itself or just needs permission from one or the other before
     * drawing itself.
     */
    @DividerCondition
    public int getDividerCondition() {
        return mDividerCondition;
    }

    /**
     * Sets whether the divider needs permission from both the item view holder below and above from
     * where the divider would draw itself or just needs permission from one or the other before
     * drawing itself.
     */
    public void setDividerCondition(@DividerCondition int dividerCondition) {
        mDividerCondition = dividerCondition;
    }

    public interface DividedViewHolder {

        /**
         * Returns whether divider is allowed above this item. A divider will be shown only if both
         * items immediately above and below it allows this divider.
         */
        boolean isDividerAllowedAbove();

        /**
         * Returns whether divider is allowed below this item. A divider will be shown only if both
         * items immediately above and below it allows this divider.
         */
        boolean isDividerAllowedBelow();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            DIVIDER_CONDITION_EITHER,
            DIVIDER_CONDITION_BOTH
    })
    public @interface DividerCondition {
    }
}
