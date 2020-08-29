package com.caldremch.android.plugin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sb = StringBuilder()
        sb.append("空壳App").append("\n")
        try {
            val a = Class.forName("com.caldremch.android.module.a.ModuleA")
            a.newInstance()
            sb.append("依赖了module-a").append("\n")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            val b = Class.forName("com.caldremch.android.module.b.ModuleB")
            b.newInstance()
            sb.append("依赖了module-b").append("\n")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        tv.text = sb.toString()
    }
}