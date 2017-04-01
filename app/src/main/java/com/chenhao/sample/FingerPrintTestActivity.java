
package com.chenhao.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import app.lib.fingerprint.AsymmetricFingerprintActivity;

/**
 * Created by chenhao on 17/4/1.
 */

public class FingerPrintTestActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fingerprint_test);

        Intent intent = new Intent(this, AsymmetricFingerprintActivity.class);
        startActivity(intent);

        finish();
    }
}
