
package com.chenhao.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import app.lib.commonui.widget.SettingItemView;

/**
 * Created by chenhao on 16/12/28.
 */

public class CommonUIActivity extends FragmentActivity {

    SettingItemView mItem4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_common_ui);

        mItem4 = (SettingItemView) findViewById(R.id.item_4);

        // mItem4.postDelayed(new Runnable() {
        // @Override
        // public void run() {
        // mItem4.setSelect(false);
        // }
        // }, 5000);

    }
}
