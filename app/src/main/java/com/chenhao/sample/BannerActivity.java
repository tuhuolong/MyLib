
package com.chenhao.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chenhao.lib.banner.BannerPagerAdapter;
import com.chenhao.lib.banner.BannerPagerIndicator;
import com.chenhao.lib.banner.BannerViewPager;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhao on 16/12/19.
 */

public class BannerActivity extends AppCompatActivity {

    Context mContext;

    BannerViewPager mBannerViewPager;
    BannerPagerIndicator mBannerIndicator;
    BannerPagerAdapter mBannerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        Fresco.initialize(mContext);

        setContentView(R.layout.activity_banner);

        mBannerViewPager = (BannerViewPager) findViewById(R.id.banner_pager);
        mBannerIndicator = (BannerPagerIndicator) findViewById(R.id.banner_indicator);
        mBannerViewPager.addOnPageChangeListener(mBannerIndicator);

        mBannerAdapter = new BannerPagerAdapter(mContext);
        mBannerAdapter.setAutoLoop(true);

        List<String> dataList = new ArrayList<>();
        dataList.add(
                "http://static.home.mi.com/app/shop/img?id=shop_fdc444ea5b838c26857d8f6e959095a1.jpeg&w=1080&h=1270&w=1080&h=1080");
        dataList.add(
                "http://static.home.mi.com/app/shop/img?id=shop_331eba8f81d50fb97df3d19af9312062.jpg&w=1080&h=1080");
        dataList.add(
                "http://static.home.mi.com/app/shop/img?id=shop_6b11ba2758ba9f3b578522eb15b211af.jpg&w=1080&h=1080");
        mBannerAdapter.setData(dataList);

        mBannerViewPager.setAdapter(mBannerAdapter);

        mBannerIndicator.setIndicatorCount(dataList.size());
        mBannerIndicator.setSelectedIndicator(0);
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
