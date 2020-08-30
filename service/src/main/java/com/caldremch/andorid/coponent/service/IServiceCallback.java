package com.caldremch.andorid.coponent.service;

import androidx.annotation.NonNull;

/**
 * @author Caldremch
 * @date 2019-06-03 18:27
 * @email caldremch@163.com
 * @describe
 **/
public interface IServiceCallback<T> {
    void onService(@NonNull T service);
    default void noService(){}
}
