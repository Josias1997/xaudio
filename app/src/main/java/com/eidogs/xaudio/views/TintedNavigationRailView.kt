/*
 * Copyright (c) 2019 Hemanth Savarala.
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by
 *  the Free Software Foundation either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package com.eidogs.xaudio.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import com.eidogs.appthemehelper.util.AppThemeUtil
import com.eidogs.xaudio.extensions.accentColor
import com.eidogs.xaudio.extensions.addAlpha
import com.eidogs.xaudio.extensions.setItemColors
import com.eidogs.xaudio.util.PreferenceUtil
import com.google.android.material.navigationrail.NavigationRailView

class TintedNavigationRailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : NavigationRailView(context, attrs, defStyleAttr) {

    init {
        if (!isInEditMode) {
            labelVisibilityMode = PreferenceUtil.tabTitleMode

            if (!PreferenceUtil.materialYou) {
                val iconColor = AppThemeUtil.resolveColor(context, android.R.attr.colorControlNormal)
                val accentColor = context.accentColor()
                setItemColors(iconColor, accentColor)
                itemRippleColor = ColorStateList.valueOf(accentColor.addAlpha(0.08F))
                itemActiveIndicatorColor = ColorStateList.valueOf(accentColor.addAlpha(0.12F))
            }
        }
    }
}