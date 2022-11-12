package com.caldremch.android.plugin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.lang.Exception
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context: Context = this

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

        findViewById<TextView>(R.id.tv).text = sb.toString()
    }
}