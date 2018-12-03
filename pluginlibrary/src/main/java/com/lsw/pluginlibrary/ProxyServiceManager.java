package com.lsw.pluginlibrary;

import java.util.HashMap;

/**
 * Created by sweeneyliu on 18/12/3.
 */
public class ProxyServiceManager {
    private HashMap<String, String> pluginServices = null;

    private static ProxyServiceManager instance = null;

    private ProxyServiceManager() {
        pluginServices = new HashMap<String, String>();
        pluginServices.put("com.lsw.plungin1.TestService1", "com.lsw.proxyserviceplugin.ProxyService1");
        pluginServices.put("com.lsw.plugin2.TestService2", "com.lsw.proxyserviceplugin.ProxyService1");
        pluginServices.put("com.lsw.plugin1.TestService3", "com.lsw.proxyserviceplugin.ProxyService1");
    }

    public static ProxyServiceManager getInstance() {
        if(instance == null)
            instance = new ProxyServiceManager();

        return instance;
    }

    public String getProxyServiceName(String className) {
        return pluginServices.get(className);
    }
}
