
package com.chenhao.sample.tabfragmentnoslide;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.chenhao.sample.R;
import com.chenhao.sample.tabfragment.MainFragment;
import com.chenhao.sample.tabfragment.PersonalFragment;

import app.lib.commonui.tabfragmentnoslide.TabChangedListenerNoSlide;
import app.lib.commonui.tabfragmentnoslide.TabFragmentAdapterNoSlide;
import app.lib.commonui.tabfragmentnoslide.TabFragmentMainViewNoSlide;

/**
 * Created by chenhao on 16/12/22.
 */

public class TabFragmentNoSlideTestActivity extends FragmentActivity implements TabChangedListenerNoSlide {

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

    Context mContext;
    TabFragmentMainViewNoSlide mTabFragmentMainView;
    TabFragmentAdapterNoSlide mTabFragmentAdapter;

    @Override
    public void onFragmentChanged(int lastIndex, int currentIndex) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        setContentView(R.layout.tab_fragment_no_slide_layout);

        mTabFragmentMainView = (TabFragmentMainViewNoSlide) findViewById(R.id.main);

        mTabFragmentAdapter = new TabFragmentMainAdapter();

        mTabFragmentMainView.init(getSupportFragmentManager(), mTabFragmentAdapter);
        mTabFragmentMainView.setTabFragmentChanged(this);
    }

    class TabFragmentMainAdapter extends TabFragmentAdapterNoSlide {

        @Override
        public Fragment getFragment(int position) {
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
