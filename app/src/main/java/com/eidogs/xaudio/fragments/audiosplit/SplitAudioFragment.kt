package com.eidogs.xaudio.fragments.audiosplit

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.eidogs.xaudio.R
import com.eidogs.xaudio.fragments.base.AbsMainActivityFragment
import com.eidogs.xaudio.util.logD

class SplitAudioFragment: AbsMainActivityFragment(R.layout.fragment_split_audio) {
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        logD("SplitAudioFragment onCreateMenu")
    }
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        logD("SplitAudioFragment onMenuItemSelected")
        return true
    }
}