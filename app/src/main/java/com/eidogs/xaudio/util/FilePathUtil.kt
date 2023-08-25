package com.eidogs.xaudio.util

import android.os.Environment

object FilePAppThemeUtil {
    fun blacklistFilePaths(): List<String> {
        return listOf(
            getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS),
            getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES),
            getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS)
        ).map {
            FileUtil.safeGetCanonicalPath(it)
        }
    }
}