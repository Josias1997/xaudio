package com.eidogs.xaudio.extensions

import androidx.core.view.WindowInsetsCompat
import com.eidogs.xaudio.util.PreferenceUtil
import com.eidogs.xaudio.util.RetroUtil

fun WindowInsetsCompat?.getBottomInsets(): Int {
    return if (PreferenceUtil.isFullScreenMode) {
        return 0
    } else {
        this?.getInsets(WindowInsetsCompat.Type.systemBars())?.bottom
            ?: RetroUtil.navigationBarHeight
    }
}
