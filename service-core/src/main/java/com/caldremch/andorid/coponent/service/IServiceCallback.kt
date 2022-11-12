package com.caldremch.andorid.coponent.service

/**
 * @author Caldremch
 * @date 2019-06-03 18:27
 * @email caldremch@163.com
 * @describe
 */
interface IServiceCallback<T> {
    fun onService(service: T)
    fun noService() {}
}