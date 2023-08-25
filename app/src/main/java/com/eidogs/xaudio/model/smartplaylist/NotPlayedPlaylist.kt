package com.eidogs.xaudio.model.smartplaylist

import com.eidogs.xaudio.App
import com.eidogs.xaudio.R
import com.eidogs.xaudio.model.Song
import kotlinx.parcelize.Parcelize

@Parcelize
class NotPlayedPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.not_recently_played),
    iconRes = R.drawable.ic_audiotrack
) {
    override fun songs(): List<Song> {
        return topPlayedRepository.notRecentlyPlayedTracks()
    }
}