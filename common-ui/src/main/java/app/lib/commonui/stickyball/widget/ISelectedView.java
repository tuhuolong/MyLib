
package app.lib.commonui.stickyball.widget;

import android.view.View;

public interface ISelectedView {
    void onCreatedIndicator(DotIndicatorInfo info);

    void onSelected(int position);

    View getView(View parent);
}
