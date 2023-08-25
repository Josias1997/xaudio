package com.eidogs.xaudio.model.smartplaylist

import com.eidogs.xaudio.App
import com.eidogs.xaudio.R
import com.eidogs.xaudio.model.Song
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