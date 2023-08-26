package com.fullstapp.splitit.model.smartplaylist

import com.fullstapp.splitit.App
import com.fullstapp.splitit.R
import com.fullstapp.splitit.model.Song
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent

@Parcelize
class HistoryPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.history),
    iconRes = R.drawable.ic_history
), KoinComponent {

    override fun songs(): List<Song> {
        return topPlayedRepository.recentlyPlayedTracks()
    }
}