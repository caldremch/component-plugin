package com.caldremch.andorid.coponent.service

import android.text.TextUtils

/**
 * @author Caldremch
 * @date 2020-08-30
 * @email caldremch@163.com
 * @describe
 **/
/**
 * @author Caldremch
 * @date 2019-03-08 10:29
 * @email caldremch@163.com
 * @describe
 */
class ServiceManager private constructor() {

    private val services = HashMap<String, Any>()
    private val serviceClzs = HashMap<String, Class<out BaseService?>>()

    @Synchronized
    fun addService(serviceName: String?, serviceImpl: Any?) {
        if (serviceName == null || serviceImpl == null) {
            return
        }
        services[serviceName] = serviceImpl
    }

    @Synchronized
    fun  <T>getService(serviceName: String): T? {
        return if (TextUtils.isEmpty(serviceName)) {
            null
        } else{
           var service =  services[serviceName]
            if(service == null){
                service =   serviceClzs[serviceName]?.newInstance()?.apply {
                    services[serviceName] = this
                }
            }
            service as T?
        }
    }

    @Synchronized
    fun <T> getService(clz: Class<T>): T? {
        return if (TextUtils.isEmpty(clz.simpleName)) {
            null
        } else getService(clz.simpleName)
    }

    @Synchronized
    fun removeService(serviceName: String) {
        if (TextUtils.isEmpty(serviceName)) {
            return
        }
        services.remove(serviceName)
    }

    companion object {
        //注册的组件的集合
         val instance: ServiceManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            ServiceManager()
        }


        @Synchronized
        fun <T> start(clz: Class<T>, callback: IServiceCallback<T>?): Boolean {
            val service = instance!!.getService(clz)
            if (service != null && callback != null) {
                callback.onService(service)
            }
            if (service == null && callback != null) {
                callback.noService()
            }
            return service == null
        }
    }
}