package com.lsw.plungin1;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.lsw.pluginlibrary.BasePluginService;
import com.lsw.pluginlibrary.ITestServiceInterface;

public class TestService3 extends BasePluginService {

    private static final String TAG = "TestService1";

    private class MyBinder extends Binder implements ITestServiceInterface {

        @Override
        public int sum(int a, int b) {
            return a + b;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, TAG + " onStartCommand");
        return 0;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, TAG + " onBind");
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, TAG + " onUnbind");
        return super.onUnbind(intent);
    }
}
