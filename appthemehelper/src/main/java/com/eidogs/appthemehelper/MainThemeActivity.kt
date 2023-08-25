
package com.eidogs.appthemehelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

open class MainThemeActivity : AppCompatActivity() {
    private var updateTime: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateTime = System.currentTimeMillis()
    }

    override fun onResume() {
        super.onResume()
        if (AppThemeHelper.didThemeValuesChange(this, updateTime)) {
            onThemeChanged()
        }
    }

    private fun onThemeChanged() {
        postRecreate()
    }

    fun postRecreate() {
        Handler(Looper.getMainLooper()).post { recreate() }
    }
}