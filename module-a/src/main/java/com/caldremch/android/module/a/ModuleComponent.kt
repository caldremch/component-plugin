package com.caldremch.android.module.a

import android.content.Context
import com.caldremch.andorid.coponent.service.IRegisterComponent
import com.caldremch.andorid.coponent.service.RegisterComponent

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
//        ServiceManager.instance.addService(IAService::class.simpleName,  ModuleAService())
    }
}