
package com.chenhao.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chenhao.sample.tabpage.TabFragmentTestActivity;

import app.lib.commonui.dialog.ProgressDialog;
import app.lib.plugin.frame.PluginApi;
import app.lib.plugin.sdk.PluginMsgType;

public class MainActivity extends AppCompatActivity {

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        setContentView(R.layout.activity_main);

        findViewById(R.id.tabfragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TabFragmentTestActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.banner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BannerActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.nestedscroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NestedScrollActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.plugin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PluginApi.getInstance().sendMessage(mContext, "demo", PluginMsgType.LAUNCH,
                        new Bundle(), new PluginApi.SendMessageCallback());
            }
        });

        findViewById(R.id.common_ui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommonUIActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog dialog = new ProgressDialog(mContext);
                dialog.setMessage("测试");
                dialog.show();
            }
        });
    }
}
