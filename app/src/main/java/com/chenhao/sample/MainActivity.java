
package com.chenhao.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import app.lib.plugin.frame.PluginRuntimeManager;
import app.lib.plugin.sdk.IMessageReceiver;

import app.lib.commonui.dialog.ProgressDialog;

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
                Intent intent = new Intent(mContext, TabFragmentActivity.class);
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
                PluginRuntimeManager.getInstance().sendMessage(mContext, 0,
                        IMessageReceiver.MSG_LAUNCH, new Bundle());
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
