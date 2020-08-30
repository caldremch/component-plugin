package com.caldremch.andorid.coponent.service;
import android.text.TextUtils;

/**
 * @author Caldremch
 * @date 2020-08-30
 * @email caldremch@163.com
 * @describe
 **/



import java.util.HashMap;

/**
 * @author Caldremch
 * @date 2019-03-08 10:29
 * @email caldremch@163.com
 * @describe
 **/
public class ServiceManager {

    private HashMap<String, Object> services = new HashMap<>();

    //注册的组件的集合
    private static volatile ServiceManager sInstance;

    private ServiceManager() {

    }

    public static ServiceManager getInstance() {

        if (sInstance == null) {

            synchronized (ServiceManager.class) {

                if (sInstance == null) {
                    sInstance = new ServiceManager();
                }

            }

        }

        return sInstance;
    }


    public synchronized void addService(String serviceName, Object serviceImpl) {
        if (serviceName == null || serviceImpl == null) {
            return;
        }

        services.put(serviceName, serviceImpl);

    }

    public synchronized Object getService(String serviceName) {
        if (TextUtils.isEmpty(serviceName)) {
            return null;
        }

        return services.get(serviceName);
    }


    public static <T> boolean start(Class<T> clz, IServiceCallback<T> callback) {
        T service = getInstance().getService(clz);
        if (service != null && callback != null) {
            callback.onService(service);
        }

        if (service == null && callback != null){
//            ToastUtils.show("组件未加载");
            callback.noService();
        }
        return service == null;
    }

    public synchronized <T> T getService(Class<T> clz) {
        if (TextUtils.isEmpty(clz.getSimpleName())) {
            return null;
        }

        return (T) services.get(clz.getSimpleName());
    }

    public synchronized void removeService(String serviceName) {
        if (TextUtils.isEmpty(serviceName)) {
            return;
        }

        services.remove(serviceName);
    }


}
