package com.lsw.plungin1;

import android.content.Intent;
import android.util.Log;

import com.lsw.pluginlibrary.BasePluginService;


public class TestService1 extends BasePluginService {

    private static final String TAG = "TestService1";
    
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, TAG + " onDestroy");
    }
}
