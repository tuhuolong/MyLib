
package com.chenhao.sample.recyclerview;

import android.view.View;

import com.chenhao.sample.R;
import com.chenhao.sample.app.MainApplication;

import app.lib.commonui.banner.BannerPagerAdapter;
import app.lib.commonui.banner.BannerPagerIndicator;
import app.lib.commonui.banner.BannerViewPager;

/**
 * Created by chenhao on 17/2/6.
 */

public class Type1ViewHolder extends BaseViewHolder {
    BannerViewPager mBannerViewPager;
    BannerPagerIndicator mBannerIndicator;
    BannerPagerAdapter mBannerAdapter;

    public Type1ViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void initViews() {

        mBannerViewPager = (BannerViewPager) itemView.findViewById(R.id.banner_pager);
        mBannerIndicator = (BannerPagerIndicator) itemView.findViewById(R.id.banner_indicator);
        mBannerViewPager.addOnPageChangeListener(mBannerIndicator);

        mBannerIndicator.setSelectedIndicator(0);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter adapter, ViewData viewData, int position) {
        if (!(viewData instanceof Type1ViewData)) {
            return;
        }

        Type1ViewData data = (Type1ViewData) viewData;

        mBannerAdapter = new BannerPagerAdapter(MainApplication.getAppContext());
        mBannerAdapter.setAutoLoop(true);

        mBannerAdapter.setData(data.urlList);

        mBannerIndicator.setIndicatorCount(data.urlList.size());
        mBannerViewPager.setAdapter(mBannerAdapter);
    }
}
