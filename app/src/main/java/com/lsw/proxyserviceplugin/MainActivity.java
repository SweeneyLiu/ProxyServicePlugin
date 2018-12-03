package com.lsw.proxyserviceplugin;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;


import com.lsw.pluginlibrary.AppConstants;
import com.lsw.pluginlibrary.ITestServiceInterface;
import com.lsw.pluginlibrary.ProxyServiceManager;

import java.io.File;

public class MainActivity extends Activity {
    PluginItem pluginItem1;
    PluginItem pluginItem2;

    ServiceConnection mConnection;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        Utils.extractAssets(newBase, "plugin1.apk");
        Utils.extractAssets(newBase, "plugin2.apk");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pluginItem1 = generatePluginItem("plugin1.apk");
        pluginItem2 = generatePluginItem("plugin2.apk");

        mConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                int sum = ((ITestServiceInterface) binder).sum(5, 5);
                Log.e("MainActivity", "onServiceConnected sum(5 + 5) = " + sum);
            }

            public void onServiceDisconnected(ComponentName name) {
                Log.e("MainActivity", "onServiceDisconnected");
            }
        };
    }

    public void startService1InPlugin1(View view) {
        try {
            Intent intent = new Intent();

            String serviceName = pluginItem1.packageInfo.packageName + ".TestService1";
            String proxyServiceName = ProxyServiceManager.getInstance().getProxyServiceName(serviceName);
            intent.setClass(this, Class.forName(proxyServiceName));

            intent.putExtra(AppConstants.EXTRA_DEX_PATH, pluginItem1.pluginPath);
            intent.putExtra(AppConstants.EXTRA_CLASS, serviceName);

            startService(intent);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void stopService1InPlugin1(View view) {
        try {
            Intent intent = new Intent();

            String serviceName = pluginItem1.packageInfo.packageName + ".TestService1";
            String proxyServiceName = ProxyServiceManager.getInstance().getProxyServiceName(serviceName);
            intent.setClass(this, Class.forName(proxyServiceName));

            intent.putExtra(AppConstants.EXTRA_DEX_PATH, pluginItem1.pluginPath);
            intent.putExtra(AppConstants.EXTRA_CLASS, serviceName);

            stopService(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void bindService3InPlugin1(View view) {
        try {
            Intent intent = new Intent();

            String serviceName = pluginItem1.packageInfo.packageName + ".TestService3";
            String proxyServiceName = ProxyServiceManager.getInstance().getProxyServiceName(serviceName);
            intent.setClass(this, Class.forName(proxyServiceName));

            intent.putExtra(AppConstants.EXTRA_DEX_PATH, pluginItem1.pluginPath);
            intent.putExtra(AppConstants.EXTRA_CLASS, serviceName);

            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void unbindService3InPlugin1(View view) {
        unbindService(mConnection);
    }

    public void startService2InPlugin2(View view) {
        try {
            Intent intent = new Intent();

            String serviceName = pluginItem2.packageInfo.packageName + ".TestService2";
            String proxyServiceName = ProxyServiceManager.getInstance().getProxyServiceName(serviceName);
            intent.setClass(this, Class.forName(proxyServiceName));

            intent.putExtra(AppConstants.EXTRA_DEX_PATH, pluginItem2.pluginPath);
            intent.putExtra(AppConstants.EXTRA_CLASS, serviceName);

            startService(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void stopService2InPlugin2(View view) {
        try {
            Intent intent = new Intent();

            String serviceName = pluginItem2.packageInfo.packageName + ".TestService2";
            String proxyServiceName = ProxyServiceManager.getInstance().getProxyServiceName(serviceName);
            intent.setClass(this, Class.forName(proxyServiceName));

            intent.putExtra(AppConstants.EXTRA_DEX_PATH, pluginItem2.pluginPath);
            intent.putExtra(AppConstants.EXTRA_CLASS, serviceName);

            stopService(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private PluginItem generatePluginItem(String apkName) {
        File file = getFileStreamPath(apkName);
        PluginItem item = new PluginItem();
        item.pluginPath = file.getAbsolutePath();
        item.packageInfo = DLUtils.getPackageInfo(this, item.pluginPath);

        return item;
    }
}
