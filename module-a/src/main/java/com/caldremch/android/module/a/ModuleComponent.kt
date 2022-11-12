package com.caldremch.android.module.a

import android.content.Context
import com.caldremch.andorid.coponent.service.IAService
import com.caldremch.andorid.coponent.service.ServiceManager
import com.caldremch.andorid.plugin.api.Component
import com.caldremch.andorid.plugin.api.IComponent

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
@Component
class ModuleComponent : IComponent {
    override fun init(context: Context) {
        ServiceManager.instance.addService(IAService::class.simpleName,  ModuleAService())
    }
}