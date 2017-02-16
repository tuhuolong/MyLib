
package com.chenhao.sample.tabview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.chenhao.sample.R;
import com.chenhao.sample.tabfragment.MainFragment;

import app.lib.common.util.DisplayUtil;
import app.lib.commonui.tabview.HorizontalTabView;
import app.lib.commonui.tabview.PagerSlidingTabStrip;

/**
 * Created by chenhao on 17/2/9.
 */

public class TabViewTestActivity extends FragmentActivity {

    private static final String[] TAB_TITLE = new String[] {
            "AAA",
            "BBB",
            "CCC",
            "DDD",
            "EEE",
            "FFF",
            "GGG",
            "HHH"
    };

    HorizontalTabView mTabView;

    PagerSlidingTabStrip mPagerSlidingTabStrip;
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tab_view);

        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tab_strp);
        // mPagerSlidingTabStrip.setShouldExpand(true);
        mPagerSlidingTabStrip.setDividerColor(0);
        mPagerSlidingTabStrip.setSelectedTextColor(Color.parseColor("#b60909"));
        mPagerSlidingTabStrip.setTextColor(Color.parseColor("#333333"));
        mPagerSlidingTabStrip.setIndicatorHeight(10);
        mPagerSlidingTabStrip.setIndicatorColor(Color.parseColor("#b60909"));
        mPagerSlidingTabStrip.setTabPaddingLeftRight(DisplayUtil.dip2px(this, 10));
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(pagerAdapter);
        mPagerSlidingTabStrip.setViewPager(mViewPager);
    }

    class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_TITLE[position];
        }

        @Override
        public Fragment getItem(int position) {
            return new MainFragment();
        }

        @Override
        public int getCount() {
            return TAB_TITLE.length;
        }
    }
}
