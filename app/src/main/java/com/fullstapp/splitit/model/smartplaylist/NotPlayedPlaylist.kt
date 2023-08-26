package com.fullstapp.splitit.model.smartplaylist

import com.fullstapp.splitit.App
import com.fullstapp.splitit.R
import com.fullstapp.splitit.model.Song
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