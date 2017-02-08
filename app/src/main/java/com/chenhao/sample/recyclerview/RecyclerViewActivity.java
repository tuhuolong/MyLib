
package com.chenhao.sample.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chenhao.sample.R;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import app.lib.commonui.recyclerview.DividerItemDecoration;

import static app.lib.commonui.recyclerview.DividerItemDecoration.DIVIDER_CONDITION_EITHER;

/**
 * Created by chenhao on 17/2/6.
 */

public class RecyclerViewActivity extends FragmentActivity {
    Context mContext;

    RecyclerView mRecyclerView;
    RecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        Fresco.initialize(mContext);

        setContentView(R.layout.activity_recycler_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerViewAdapter = new RecyclerViewAdapter();
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                getResources().getDrawable(R.drawable.recycler_view_divider), 1,
                DIVIDER_CONDITION_EITHER));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ViewData> viewDataList = new ArrayList<>();
        Type1ViewData type1ViewData = new Type1ViewData();
        List<String> urlList = new ArrayList<>();
        urlList.add(
                "http://static.home.mi.com/app/shop/img?id=shop_fdc444ea5b838c26857d8f6e959095a1.jpeg&w=1080&h=1270&w=1080&h=1080");
        urlList.add(
                "http://static.home.mi.com/app/shop/img?id=shop_331eba8f81d50fb97df3d19af9312062.jpg&w=1080&h=1080");
        urlList.add(
                "http://static.home.mi.com/app/shop/img?id=shop_6b11ba2758ba9f3b578522eb15b211af.jpg&w=1080&h=1080");
        type1ViewData.urlList = urlList;
        viewDataList.add(type1ViewData);

        for (int i = 0; i < 25; i++) {
            Type2ViewData type2ViewData = new Type2ViewData();
            type2ViewData.title = "Title" + i;
            viewDataList.add(type2ViewData);
        }

        mRecyclerViewAdapter.updateData(viewDataList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Fresco.shutDown();
    }

}
