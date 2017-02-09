
package app.lib.commonui.tabfragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by chenhao on 17/2/8.
 */

public abstract class TabFragmentAdapter extends FragmentPagerAdapter {
    public TabFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public abstract int getCount();

    public abstract int getTabIcon(int position);

    public abstract CharSequence getTabTitle(int position);

    public abstract int getTabTitleColor(int position);
}
