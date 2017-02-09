
package com.chenhao.sample.tabfragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenhao.sample.R;

import app.lib.commonui.ptr.PtrHeader;
import app.lib.commonui.widget.TitleBar;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by chenhao on 17/2/7.
 */

public class MainFragment extends Fragment {

    PtrFrameLayout mPtrFrameLayout;
    PtrIndicator mPtrIndicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_main, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TitleBar titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.setupTitleText("Main");

        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptr);
        PtrHeader header = (PtrHeader) view.findViewById(R.id.ptr_header);
        mPtrFrameLayout.addPtrUIHandler(header);
        mPtrIndicator = new PtrIndicator();
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
