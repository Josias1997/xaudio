package com.eidogs.appthemehelper.common

import androidx.appcompat.widget.Toolbar
import com.eidogs.appthemehelper.util.ToolbarContentTintHelper

class AppThemeActionBarActivity: AppThemeToolbarActivity() {
    override fun getATHToolbar(): Toolbar? {
        return ToolbarContentTintHelper.getSupportActionBarView(supportActionBar)
    }
}