package com.eidogs.appthemehelper.common.views

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.isVisible
import com.eidogs.appthemehelper.AppThemeHelper
import com.eidogs.appthemehelper.ThemeStore
import com.google.android.material.materialswitch.MaterialSwitch

class ATESwitch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : MaterialSwitch(context, attrs, defStyleAttr){
    init {
        if(!isInEditMode && !ThemeStore.isMD3Enabled(context)) {
            AppThemeHelper.setTint(this, ThemeStore.accentColor(context))
        }
    }

    override fun isShown(): Boolean {
        return parent != null && isVisible
    }

}