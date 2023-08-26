package com.fullstapp.splitit.model.smartplaylist

import com.fullstapp.splitit.App
import com.fullstapp.splitit.R
import com.fullstapp.splitit.model.Song
import kotlinx.parcelize.Parcelize

@Parcelize
class LastAddedPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.last_added),
    iconRes = R.drawable.ic_library_add
) {
    override fun songs(): List<Song> {
        return lastAddedRepository.recentSongs()
    }
}