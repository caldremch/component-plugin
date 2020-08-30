package com.caldremch.android.module.a

import android.util.Log
import android.widget.Toast
import com.caldremch.andorid.coponent.service.IAService

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
class ModuleAService : IAService {
    override fun test() {
        Log.d("ModuleAService", "ModuleAService: test")
    }
}