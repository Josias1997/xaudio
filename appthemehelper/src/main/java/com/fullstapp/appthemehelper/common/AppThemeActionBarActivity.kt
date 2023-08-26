package com.fullstapp.appthemehelper.common

import androidx.appcompat.widget.Toolbar
import com.fullstapp.appthemehelper.util.ToolbarContentTintHelper

class AppThemeActionBarActivity: AppThemeToolbarActivity() {
    override fun getATHToolbar(): Toolbar? {
        return ToolbarContentTintHelper.getSupportActionBarView(supportActionBar)
    }
}