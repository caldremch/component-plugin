package com.caldremch.plugin.utils

import java.util.regex.Pattern

/**
 *
 *Created by Caldremch on 2019/11/24.
 *
 **/
object RegularUtils {
    //not need to handle R$xxx.class R.class, BuildConfig.class
    fun `isR$xxClass`(name: String): Boolean {
        val reg = ".*R\\$.*.class$"
        return (name.endsWith("R.class")
                || name.endsWith("BuildConfig.class")
                || Pattern.matches(reg, name)

                )
    }
}