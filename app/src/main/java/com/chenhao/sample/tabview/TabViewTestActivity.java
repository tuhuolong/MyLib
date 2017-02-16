
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
import app.lib.commonui.tabview.PagerSlidingTabView;

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

    PagerSlidingTabView mPagerSlidingTabView;
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tab_view);

        mPagerSlidingTabView = (PagerSlidingTabView) findViewById(R.id.tab_strp);
        // mPagerSlidingTabView.setShouldExpand(true);
        mPagerSlidingTabView.setDividerColor(0);
        mPagerSlidingTabView.setSelectedTextColor(Color.parseColor("#b60909"));
        mPagerSlidingTabView.setTextColor(Color.parseColor("#333333"));
        mPagerSlidingTabView.setIndicatorHeight(10);
        mPagerSlidingTabView.setIndicatorColor(Color.parseColor("#b60909"));
        mPagerSlidingTabView.setTabPaddingLeftRight(DisplayUtil.dip2px(this, 10));
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(pagerAdapter);
        mPagerSlidingTabView.setViewPager(mViewPager);
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
