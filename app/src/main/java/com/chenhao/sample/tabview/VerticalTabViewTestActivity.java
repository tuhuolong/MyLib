
package com.chenhao.sample.tabview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chenhao.sample.R;
import com.chenhao.sample.tabfragment.MainFragment;

import app.lib.common.util.DisplayUtil;
import app.lib.commonui.viewpager.NoScrollViewPager;
import app.lib.commonui.tabview.PagerSlidingVerticalTabView;

/**
 * Created by chenhao on 17/2/9.
 */

public class VerticalTabViewTestActivity extends FragmentActivity {

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

    PagerSlidingVerticalTabView mPagerSlidingVerticalTabView;
    NoScrollViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vertical_tab_view);

        mPagerSlidingVerticalTabView = (PagerSlidingVerticalTabView) findViewById(R.id.tab_view);
        // mPagerSlidingVerticalTabView.setShouldExpand(true);
        mPagerSlidingVerticalTabView.setDividerColor(0);
        mPagerSlidingVerticalTabView.setSelectedTextColor(Color.parseColor("#b60909"));
        mPagerSlidingVerticalTabView.setTextColor(Color.parseColor("#333333"));
        mPagerSlidingVerticalTabView.setIndicatorWidth(10);
        mPagerSlidingVerticalTabView.setIndicatorColor(Color.parseColor("#b60909"));
        mPagerSlidingVerticalTabView.setTabPaddingLeftRight(DisplayUtil.dip2px(this, 10));
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (NoScrollViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(pagerAdapter);
        mPagerSlidingVerticalTabView.setViewPager(mViewPager);

        mViewPager.setScrollEnabled(true);
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
