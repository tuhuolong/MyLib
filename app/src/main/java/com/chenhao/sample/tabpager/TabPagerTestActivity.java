
package com.chenhao.sample.tabpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.chenhao.sample.R;
import com.chenhao.sample.tabfragment.MainFragment;
import com.chenhao.sample.tabfragment.PersonalFragment;

import app.lib.commonui.tabfragment.TabChangedListener;
import app.lib.commonui.tabpager.TabPagerAdapter;
import app.lib.commonui.tabpager.TabPagerLayout;

import static android.view.View.OVER_SCROLL_NEVER;

/**
 * Created by chenhao on 17/2/8.
 */

public class TabPagerTestActivity extends FragmentActivity implements TabChangedListener {
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
    TabPagerLayout mTabPagerLayout;
    MainAdapter mMainAdapter;

    @Override
    public void onFragmentChanged(int lastIndex, int currentIndex) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab_pager_layout);

        mTabPagerLayout = (TabPagerLayout) findViewById(R.id.tab_pager);

        mMainAdapter = new MainAdapter(getSupportFragmentManager());

        mTabPagerLayout.init(getSupportFragmentManager(), mMainAdapter);
        mTabPagerLayout.setTabChangedListener(this);
        mTabPagerLayout.setPagingEnabled(true);
        mTabPagerLayout.setSmoothScroll(false);
        mTabPagerLayout.setPagerOverScrollMode(OVER_SCROLL_NEVER);
    }

    class MainAdapter extends TabPagerAdapter {
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
