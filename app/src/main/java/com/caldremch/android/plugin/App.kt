package com.caldremch.android.plugin

import android.app.Application
import com.caldremch.andorid.plugin.api.HostApp
import com.caldremch.andorid.plugin.api.KtHostApp

/**
 *
 * @author Caldremch
 *
 * @date 2020-08-31 10:28
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class App : Application(), KtHostApp{
    override fun onCreate() {
        super.onCreate()
    }
}