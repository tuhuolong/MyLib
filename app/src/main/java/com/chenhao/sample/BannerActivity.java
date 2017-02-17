
package com.chenhao.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import app.lib.commonui.banner.BannerPagerAdapter;
import app.lib.commonui.banner.BannerPagerIndicator;
import app.lib.commonui.banner.BannerViewPager;
import app.lib.commonui.stickyball.widget.DotIndicatorView;

/**
 * Created by chenhao on 16/12/19.
 */

public class BannerActivity extends AppCompatActivity {

    Context mContext;

    BannerViewPager mPager1;
    BannerPagerIndicator mIndicator1;
    BannerPagerAdapter mAdapter1;

    BannerViewPager mPager2;
    DotIndicatorView mIndicator2;
    BannerPagerAdapter mAdapter2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        Fresco.initialize(mContext);

        setContentView(R.layout.activity_banner);

        mPager1 = (BannerViewPager) findViewById(R.id.pager_1);
        mIndicator1 = (BannerPagerIndicator) findViewById(R.id.indicator_1);
        mPager1.addOnPageChangeListener(mIndicator1);

        mAdapter1 = new BannerPagerAdapter(mContext);
        mAdapter1.setAutoLoop(true);

        List<String> data1 = new ArrayList<>();
        data1.add(
                "http://static.home.mi.com/app/shop/img?id=shop_fdc444ea5b838c26857d8f6e959095a1.jpeg&w=1080&h=1270&w=1080&h=1080");
        data1.add(
                "http://static.home.mi.com/app/shop/img?id=shop_331eba8f81d50fb97df3d19af9312062.jpg&w=1080&h=1080");
        data1.add(
                "http://static.home.mi.com/app/shop/img?id=shop_6b11ba2758ba9f3b578522eb15b211af.jpg&w=1080&h=1080");
        mAdapter1.setData(data1);
        mPager1.setAdapter(mAdapter1);

        mIndicator1.setIndicatorCount(data1.size());
        mIndicator1.setSelectedIndicator(0);

        mPager2 = (BannerViewPager) findViewById(R.id.pager_2);
        mIndicator2 = (DotIndicatorView) findViewById(R.id.indicator_2);
        mIndicator2.setSelectedView(DotIndicatorView.STICKY_BALL);
        mPager2.addOnPageChangeListener(mIndicator2);

        mAdapter2 = new BannerPagerAdapter(mContext);
        mAdapter2.setAutoLoop(true);

        List<String> data2 = new ArrayList<>();
        data2.add(
                "http://static.home.mi.com/app/shop/img?id=shop_fdc444ea5b838c26857d8f6e959095a1.jpeg&w=1080&h=1270&w=1080&h=1080");
        data2.add(
                "http://static.home.mi.com/app/shop/img?id=shop_331eba8f81d50fb97df3d19af9312062.jpg&w=1080&h=1080");
        data2.add(
                "http://static.home.mi.com/app/shop/img?id=shop_6b11ba2758ba9f3b578522eb15b211af.jpg&w=1080&h=1080");
        mAdapter2.setData(data2);
        mPager2.setAdapter(mAdapter2);

        mIndicator2.setDotCount(data2.size());
        mIndicator2.setCurrentItem(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Fresco.shutDown();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
