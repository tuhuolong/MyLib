
package com.chenhao.sample.tabfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.chenhao.sample.R;

import app.lib.commonui.tabfragment.TabChangedListener;
import app.lib.commonui.tabfragment.TabFragmentAdapter;
import app.lib.commonui.tabfragment.TabFragmentLayout;

import static android.view.View.OVER_SCROLL_NEVER;

/**
 * Created by chenhao on 17/2/8.
 */

public class TabFragmentTestActivity extends FragmentActivity implements TabChangedListener {
    private static final int[] TAB_TITLE = new int[] {
            R.string.tab_main,
            R.string.tab_profile,
    };
    private static final int[] TAB_TITLE_COLOR = new int[] {
            R.color.tab_text_color,
            R.color.tab_text_color
    };
    private static final int[] TAB_ICON = new int[] {
            R.drawable.tab_main,
            R.drawable.tab_profile,
    };
    TabFragmentLayout mTabFragmentLayout;
    MainAdapter mMainAdapter;

    @Override
    public void onFragmentChanged(int lastIndex, int currentIndex) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab_fragment_layout);

        mTabFragmentLayout = (TabFragmentLayout) findViewById(R.id.tab_pager);

        mMainAdapter = new MainAdapter(getSupportFragmentManager());

        mTabFragmentLayout.init(getSupportFragmentManager(), mMainAdapter);
        mTabFragmentLayout.setTabChangedListener(this);
        mTabFragmentLayout.setPagingEnabled(true);
        mTabFragmentLayout.setSmoothScroll(false);
        mTabFragmentLayout.setPagerOverScrollMode(OVER_SCROLL_NEVER);
    }

    class MainAdapter extends TabFragmentAdapter {
        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new MainFragment();
            } else if (position == 1) {
                return new PersonalFragment();
            } else {
                return new Fragment();
            }
        }

        @Override
        public CharSequence getTabTitle(int position) {
            return getResources().getString(TAB_TITLE[position]);
        }

        @Override
        public int getCount() {
            return TAB_TITLE.length;
        }

        @Override
        public int getTabIcon(int position) {
            return TAB_ICON[position];
        }

        @Override
        public int getTabTitleColor(int position) {
            return TAB_TITLE_COLOR[position];
        }
    }
}
