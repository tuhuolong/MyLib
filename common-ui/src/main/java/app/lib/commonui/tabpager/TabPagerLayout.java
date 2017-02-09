
package app.lib.commonui.tabpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.lib.commonui.R;
import app.lib.commonui.tabfragment.TabChangedListener;

/**
 * Created by chenhao on 17/2/8.
 */

public class TabPagerLayout extends LinearLayout implements View.OnClickListener {

    ViewPager mViewPager;
    LinearLayout mIndicatorView;
    FragmentManager mFragmentManager;
    TabPagerAdapter mTabPagerAdapter;

    boolean mSmoothScroll = true;

    int mCurrentIndex = -1;
    View mCurrentView = null;

    TabChangedListener mTabChangedListener;

    LayoutInflater mLayoutInflater;

    public TabPagerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLayoutInflater = LayoutInflater.from(getContext());
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mIndicatorView = (LinearLayout) findViewById(R.id.indicator);
    }

    public void setTabChangedListener(TabChangedListener tabChangedListener) {
        mTabChangedListener = tabChangedListener;
    }

    public void init(FragmentManager fragmentManager, TabPagerAdapter adapter) {
        mFragmentManager = fragmentManager;
        mTabPagerAdapter = adapter;

        mViewPager.setAdapter(mTabPagerAdapter);
        mViewPager.addOnPageChangeListener(
                new android.support.v4.view.ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset,
                            int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        setCurrentItem(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

        mIndicatorView.removeAllViews();
        for (int i = 0; i < mTabPagerAdapter.getCount(); i++) {
            CharSequence pageTitle = mTabPagerAdapter.getTabTitle(i);
            int pageIcon = mTabPagerAdapter.getTabIcon(i);
            int pageTitleColor = mTabPagerAdapter.getTabTitleColor(i);
            if (pageTitleColor == 0) {
                pageTitleColor = R.color.tabfragment_tab_text_default_color;
            }

            addTabView(i, pageTitle, pageIcon, pageTitleColor);
        }
        if (mCurrentView == null) {
            setCurrentItem(0);
        } else {
            setCurrentView(mCurrentView);
        }
    }

    /**
     * 是否允许侧滑
     *
     * @param enabled
     */
    public void setPagingEnabled(boolean enabled) {
        mViewPager.setPagingEnabled(enabled);
    }

    /**
     * 滑动动画
     *
     * @param smoothScroll
     */
    public void setSmoothScroll(boolean smoothScroll) {
        mSmoothScroll = smoothScroll;
    }

    /**
     * ViewPager边缘阴影
     *
     * @param overScrollMode
     */
    public void setPagerOverScrollMode(int overScrollMode) {
        mViewPager.setOverScrollMode(overScrollMode);
    }

    void addTabView(int index, CharSequence title, int resIcon, int color) {
        View view = mLayoutInflater.inflate(R.layout.tabfragment_tab_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        imageView.setImageResource(resIcon);
        imageView.setFocusable(true);
        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(title);
        textView.setTextColor(getResources().getColorStateList(color));
        textView.setFocusable(true);
        Tag tag = new Tag();
        tag.index = index;
        view.setTag(tag);
        view.setOnClickListener(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        lp.gravity = Gravity.CENTER;
        mIndicatorView.addView(view, lp);
    }

    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    public void setCurrentItem(int index) {
        if (index >= 0 && index < mTabPagerAdapter.getCount()) {
            setCurrentView(mIndicatorView.getChildAt(index));
        }
    }

    private void setCurrentView(View view) {
        if (view == mCurrentView)
            return;
        Object object = view.getTag();
        if (object != null && object instanceof Tag) {
            int lastIndex = mCurrentIndex;
            if (mCurrentView != null) {
                mCurrentView.setSelected(false);
            }

            mCurrentIndex = ((Tag) object).index;
            mCurrentView = view;
            mCurrentView.setSelected(true);

            mViewPager.setCurrentItem(mCurrentIndex, mSmoothScroll);

            if (mTabChangedListener != null) {
                mTabChangedListener.onFragmentChanged(lastIndex, mCurrentIndex);
            }
        }
    }

    public Fragment getFragment(int index) {
        String tag = makeFragmentName(mViewPager.getId(), index);
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        setCurrentView(v);
    }

    public static class Tag {
        public int index;
    }
}
