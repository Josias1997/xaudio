package com.fullstapp.splitit.extensions

import androidx.core.view.WindowInsetsCompat
import com.fullstapp.splitit.util.PreferenceUtil
import com.fullstapp.splitit.util.RetroUtil

fun WindowInsetsCompat?.getBottomInsets(): Int {
    return if (PreferenceUtil.isFullScreenMode) {
        return 0
    } else {
        this?.getInsets(WindowInsetsCompat.Type.systemBars())?.bottom
            ?: RetroUtil.navigationBarHeight
    }
}
