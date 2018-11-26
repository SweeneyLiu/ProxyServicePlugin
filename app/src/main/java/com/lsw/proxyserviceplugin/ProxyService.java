package com.lsw.proxyserviceplugin;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


import com.lsw.pluginlibrary.AppConstants;
import com.lsw.pluginlibrary.IRemoteService;

import java.io.File;
import java.lang.reflect.Constructor;

import dalvik.system.DexClassLoader;

public class ProxyService extends Service {
    
    private static final String TAG = "ProxyService";

    private String mClass;
    private IRemoteService mRemoteService;

    @Override
    public void onCreate() {
        super.onCreate();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        mDexPath = intent.getStringExtra(AppConstants.EXTRA_DEX_PATH);
        mClass = intent.getStringExtra(AppConstants.EXTRA_CLASS);

        loadClassLoader();

        try {
            //反射出插件的Service对象
            Class<?> localClass = dexClassLoader.loadClass(mClass);
            Constructor<?> localConstructor = localClass.getConstructor(new Class[] {});
            Object instance = localConstructor.newInstance(new Object[] {});

            mRemoteService = (IRemoteService) instance;
            mRemoteService.setProxy(this, mDexPath);

            return mRemoteService.onStartCommand(intent, flags, startId);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, TAG + " onDestroy");

        mRemoteService.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, TAG + " onBind");
        return mRemoteService.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, TAG + " onUnbind");
        return mRemoteService.onUnbind(intent);
    }

    protected String mDexPath;
    protected ClassLoader dexClassLoader;

    protected void loadClassLoader() {
        File dexOutputDir = this.getDir("dex", Context.MODE_PRIVATE);
        final String dexOutputPath = dexOutputDir.getAbsolutePath();
        dexClassLoader = new DexClassLoader(mDexPath,
                dexOutputPath, null, getClassLoader());
    }
}

