
package com.chenhao.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by chenhao on 17/2/6.
 */

public class PtrActivity extends FragmentActivity {
    PtrFrameLayout mPtrFrameLayout;
    PtrIndicator mPtrIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycler_view);

        mPtrIndicator = new PtrIndicator();

        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptr);
        mPtrFrameLayout.setPtrIndicator(mPtrIndicator);
        mPtrFrameLayout.setResistance(1.7f);
        mPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrameLayout.setDurationToClose(300);
        mPtrFrameLayout.setDurationToCloseHeader(500);
        mPtrFrameLayout.setKeepHeaderWhenRefresh(true);
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrameLayout.refreshComplete();
                    }
                }, 2000);
            }
        });

    }
}
