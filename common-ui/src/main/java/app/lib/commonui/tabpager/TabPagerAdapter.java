
package app.lib.commonui.tabpager;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by chenhao on 17/2/8.
 */

public abstract class TabPagerAdapter extends FragmentPagerAdapter {
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public abstract int getCount();

    public abstract int getTabIcon(int position);

    public abstract CharSequence getTabTitle(int position);

    public abstract int getTabTitleColor(int position);
}
