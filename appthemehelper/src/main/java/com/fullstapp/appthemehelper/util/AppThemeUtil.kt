package com.fullstapp.appthemehelper.util

import android.content.Context
import android.graphics.Color
import androidx.annotation.AttrRes

object AppThemeUtil {
    fun isWindowBackgroundDark(context: Context): Boolean {
        return !ColorUtil.isColorLight(resolveColor(context, android.R.attr.windowBackground))
    }

    @JvmOverloads
    fun resolveColor(context: Context, @AttrRes attrRes: Int, fallback: Int = 0): Int {
        context.theme.obtainStyledAttributes(intArrayOf(attrRes)).use {
            return try {
                it.getColor(0, fallback)
            } catch (e: Exception) {
                Color.BLACK
            }
        }
    }
}