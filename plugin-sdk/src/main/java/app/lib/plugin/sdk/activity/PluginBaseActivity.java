
package app.lib.plugin.sdk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import app.lib.plugin.sdk.PluginContext;

public class PluginBaseActivity extends FragmentActivity {
    private static final String TAG = "PluginBaseActivity";

    public static String FINISH_TAG = "plugin_finish";
    public static String LAST_FINISH_ACTIVITY = "plugin_last_finish_activity";
    public static int START_ACTIVITY_TAG = 10000;

    // 插件上下文
    private PluginContext mPluginContext;
    // 宿主Activity
    private FragmentActivity mHostActivity;

    // 判断是否是由宿主activity调用
    boolean isLocalLaunch() {
        return mHostActivity == null;
    }

    // 获取宿主activity，提供Context环境，用于获取资源，获取service服务，创建Dialog等
    public Activity activity() {
        if (isLocalLaunch()) {
            return this;
        } else {
            return mHostActivity;
        }
    }

    // 在宿主activity中创建完成后，调用attach，传递宿主信息
    public void attach(FragmentActivity hostActivity, PluginContext pluginContext) {
        mHostActivity = hostActivity;
        mPluginContext = pluginContext;

        attachBaseContext(mHostActivity);
    }

    public Activity getHostActivity() {
        return mHostActivity;
    }

    public PluginContext getPluginContext() {
        return mPluginContext;
    }

    /**
     * 按照调用栈一直回退finish，直到lastActivityClass为止,当lastActivityClass为null会一直回退到设备列表
     *
     * @param lastActivityClass
     */
    public void finishParent(String lastActivityClass) {
        Intent data = new Intent();
        data.putExtra(FINISH_TAG, true);
        if (!TextUtils.isEmpty(lastActivityClass))
            data.putExtra(LAST_FINISH_ACTIVITY, lastActivityClass);
        activity().setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void setContentView(View view) {
        if (isLocalLaunch()) {
            super.setContentView(view);
        } else {
            mHostActivity.setContentView(view);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (isLocalLaunch()) {
            super.setContentView(view, params);
        } else {
            mHostActivity.setContentView(view, params);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (isLocalLaunch()) {
            super.setContentView(layoutResID);
        } else {
            mHostActivity.setContentView(layoutResID);
        }
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        if (isLocalLaunch()) {
            super.addContentView(view, params);
        } else {
            mHostActivity.addContentView(view, params);
        }
    }

    @Override
    public Looper getMainLooper() {
        if (isLocalLaunch()) {
            return super.getMainLooper();
        } else {
            return mHostActivity.getMainLooper();
        }
    }

    @Override
    public View findViewById(int id) {
        if (isLocalLaunch()) {
            return super.findViewById(id);
        } else {
            return mHostActivity.findViewById(id);
        }
    }

    @Override
    public Intent getIntent() {
        if (isLocalLaunch()) {
            return super.getIntent();
        } else {
            return mHostActivity.getIntent();
        }
    }

    @Override
    public void setIntent(Intent newIntent) {
        if (isLocalLaunch()) {
            super.setIntent(newIntent);
        } else {
            mHostActivity.setIntent(newIntent);
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        if (isLocalLaunch()) {
            return super.getClassLoader();
        } else {
            return mHostActivity.getClassLoader();
        }
    }

    @Override
    public Resources getResources() {
        if (isLocalLaunch()) {
            return super.getResources();
        } else {
            return mHostActivity.getResources();
        }
    }

    @Override
    public AssetManager getAssets() {
        if (isLocalLaunch()) {
            return super.getAssets();
        } else {
            return mHostActivity.getAssets();
        }
    }

    @Override
    public MenuInflater getMenuInflater() {
        if (isLocalLaunch()) {
            return super.getMenuInflater();
        } else {
            return mHostActivity.getMenuInflater();
        }
    }

    // @Override
    // public String getPackageName() {
    // if (isLocalLaunch()) {
    // return super.getPackageName();
    // } else {
    // if (mPluginPackage != null)
    // return mPluginPackage.packageRawInfo.mPackageName;
    // else
    // return mHostActivity.getPackageName();
    // }
    // }
    //
    // @Override
    // public LayoutInflater getLayoutInflater() {
    // if (isLocalLaunch()) {
    // return super.getLayoutInflater();
    // } else {
    // return LayoutInflaterManager.getInstance().getLayoutInflater(this);
    // }
    // }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        if (isLocalLaunch()) {
            return super.getSharedPreferences(name, mode);
        } else {
            return mHostActivity.getSharedPreferences(name, mode);
        }
    }

    @Override
    public Context getApplicationContext() {
        if (isLocalLaunch()) {
            return super.getApplicationContext();
        } else {
            return mHostActivity.getApplicationContext();
        }
    }

    @Override
    public WindowManager getWindowManager() {
        if (isLocalLaunch()) {
            return super.getWindowManager();
        } else {
            return mHostActivity.getWindowManager();
        }
    }

    @Override
    public Window getWindow() {
        if (isLocalLaunch()) {
            return super.getWindow();
        } else {
            return mHostActivity.getWindow();
        }
    }

    @Override
    public Object getSystemService(String name) {
        if (isLocalLaunch()) {
            return super.getSystemService(name);
        } else {
            return mHostActivity.getSystemService(name);
        }
    }

    @Override
    public void finish() {
        if (isLocalLaunch()) {
            super.finish();
        } else {
            mHostActivity.finish();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (isLocalLaunch()) {
            super.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onBackPressed() {
        if (isLocalLaunch()) {
            super.onBackPressed();
        } else {
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isLocalLaunch()) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        if (data != null) {
            boolean isFinish = data.getBooleanExtra(FINISH_TAG, false);
            String activityName = this.getClass().getName();
            String lastActivity = data.getStringExtra(LAST_FINISH_ACTIVITY);
            if (isFinish && !activityName.equals(lastActivity)) {
                finishParent(lastActivity);
            }
        }
    }

    @Override
    public void onStart() {
        if (isLocalLaunch()) {
            super.onStart();
        }
    }

    @Override
    public void onRestart() {
        if (isLocalLaunch()) {
            super.onRestart();
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (isLocalLaunch()) {
            super.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (isLocalLaunch()) {
            super.onSaveInstanceState(outState);
        }
    }

    public void onNewIntent(Intent intent) {
        if (isLocalLaunch()) {
            super.onNewIntent(intent);
        }
    }

    @Override
    public void onResume() {
        if (isLocalLaunch()) {
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if (isLocalLaunch()) {
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if (isLocalLaunch()) {
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if (isLocalLaunch()) {
            super.onDestroy();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (isLocalLaunch()) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isLocalLaunch()) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (isLocalLaunch()) {
            return super.dispatchKeyEvent(event);
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isLocalLaunch()) {
            return super.onKeyDown(keyCode, event);
        }
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (isLocalLaunch()) {
            return super.onKeyUp(keyCode, event);
        }
        return false;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (isLocalLaunch()) {
            return super.onKeyLongPress(keyCode, event);
        }
        return false;
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        if (isLocalLaunch()) {
            return super.onKeyMultiple(keyCode, repeatCount, event);
        }
        return false;
    }

    @Override
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        if (isLocalLaunch()) {
            return super.onKeyShortcut(keyCode, event);
        }
        return false;
    }

    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        if (isLocalLaunch()) {
            super.onWindowAttributesChanged(params);
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (isLocalLaunch()) {
            super.onWindowFocusChanged(hasFocus);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (isLocalLaunch()) {
            return super.onCreateOptionsMenu(menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (isLocalLaunch()) {
            return onOptionsItemSelected(item);
        }
        return false;
    }

    @Override
    public void onContentChanged() {
        if (isLocalLaunch()) {
            super.onContentChanged();
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        if (isLocalLaunch()) {
            super.startActivityForResult(intent, requestCode, options);
        } else {
            mHostActivity.startActivityForResult(intent, requestCode, options);
        }

    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        if (isLocalLaunch()) {
            return super.getSupportFragmentManager();
        } else {
            return mHostActivity.getSupportFragmentManager();
        }
    }

    // ------------------------------------------------------------------------
    // methods override from FragmentActivity
    // ------------------------------------------------------------------------

    @Override
    public LoaderManager getSupportLoaderManager() {
        if (isLocalLaunch()) {
            return super.getSupportLoaderManager();
        } else {
            return mHostActivity.getSupportLoaderManager();
        }
    }

    public void setResultV0(int resultCode) {
        if (isLocalLaunch()) {
            super.setResult(resultCode);
        } else {
            mHostActivity.setResult(resultCode);
        }
    }

    public void setResultV0(int resultCode, Intent data) {
        if (isLocalLaunch()) {
            super.setResult(resultCode, data);
        } else {
            mHostActivity.setResult(resultCode, data);
        }
    }

    public boolean isFinishing() {
        if (isLocalLaunch()) {
            return super.isFinishing();
        } else {
            return mHostActivity.isFinishing();
        }
    }

    @Override
    public boolean isChangingConfigurations() {
        if (isLocalLaunch()) {
            return super.isChangingConfigurations();
        } else {
            return mHostActivity.isChangingConfigurations();
        }
    }

    @Override
    public int getRequestedOrientation() {
        if (isLocalLaunch()) {
            return super.getRequestedOrientation();
        } else {
            return mHostActivity.getRequestedOrientation();
        }
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        if (isLocalLaunch()) {
            super.setRequestedOrientation(requestedOrientation);
        } else {
            mHostActivity.setRequestedOrientation(requestedOrientation);
        }

    }

    @Override
    public int getTaskId() {
        if (isLocalLaunch()) {
            return super.getTaskId();
        } else {
            return mHostActivity.getTaskId();
        }
    }

    @Override
    public boolean isTaskRoot() {
        if (isLocalLaunch()) {
            return super.isTaskRoot();
        } else {
            return mHostActivity.isTaskRoot();
        }
    }

    @Override
    public SharedPreferences getPreferences(int mode) {
        if (isLocalLaunch()) {
            return super.getPreferences(mode);
        } else {
            return mHostActivity.getPreferences(mode);
        }
    }

    @Override
    public android.app.LoaderManager getLoaderManager() {
        if (isLocalLaunch()) {
            return super.getLoaderManager();
        } else {
            return mHostActivity.getLoaderManager();
        }
    }

    @Override
    public View getCurrentFocus() {
        if (isLocalLaunch()) {
            return super.getCurrentFocus();
        } else {
            return mHostActivity.getCurrentFocus();
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        if (isLocalLaunch()) {
            super.onPostCreate(savedInstanceState);
        }
    }

    @Override
    public void onPostResume() {
        if (isLocalLaunch()) {
            super.onPostResume();
        }
    }

    @Override
    public void onUserLeaveHint() {
        if (isLocalLaunch()) {
            super.onUserLeaveHint();
        }
    }

}
