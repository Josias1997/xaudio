package com.fullstapp.splitit.fragments.audiosplit

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.fullstapp.splitit.R
import com.fullstapp.splitit.fragments.base.AbsMainActivityFragment
import com.fullstapp.splitit.util.logD

class SplitAudioFragment: AbsMainActivityFragment(R.layout.fragment_split_audio) {
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        logD("SplitAudioFragment onCreateMenu")
    }
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        logD("SplitAudioFragment onMenuItemSelected")
        return true
    }
}