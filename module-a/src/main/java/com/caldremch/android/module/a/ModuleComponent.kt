package com.caldremch.android.module.a

import android.content.Context
import com.caldremch.andorid.coponent.service.IAService
import com.caldremch.andorid.coponent.service.ServiceManager
import com.caldremch.andorid.plugin.api.RegisterComponent
import com.caldremch.andorid.plugin.api.IRegisterComponent

/**
 *
 * @author Caldremch
 *
 * @date 2020-08-30
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
@RegisterComponent
class ModuleComponent : IRegisterComponent {
    override fun init(context: Context) {
        ServiceManager.getInstance().addService(IAService::class.simpleName,  ModuleAService())
    }
}