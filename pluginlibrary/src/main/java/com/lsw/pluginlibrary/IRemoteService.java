package com.lsw.pluginlibrary;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public interface IRemoteService {

    public void onCreate();

    public int onStartCommand(Intent intent, int flags, int startId);
    
    public void onDestroy();

    public IBinder onBind(Intent intent);

    public boolean onUnbind(Intent intent);

    public void setProxy(Service proxyService, String dexPath);
}
