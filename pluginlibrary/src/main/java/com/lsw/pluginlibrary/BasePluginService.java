package com.lsw.pluginlibrary;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class BasePluginService extends Service implements IRemoteService {

    public static final String TAG = "BasePluginService";
    private Service that;
    private String dexPath;

    @Override
    public void setProxy(Service proxyService, String dexPath) {
        that = proxyService;
        this.dexPath = dexPath;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, TAG + " onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, TAG + " onStartCommand");
        return 0;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, TAG + " onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, TAG + " onBind");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, TAG + " onUnbind");
        return false;
    }
}
