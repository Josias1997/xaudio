package com.eidogs.xaudio.model.smartplaylist

import com.eidogs.xaudio.App
import com.eidogs.xaudio.R
import com.eidogs.xaudio.model.Song
import kotlinx.parcelize.Parcelize

@Parcelize
class TopTracksPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.my_top_tracks),
    iconRes = R.drawable.ic_trending_up
) {
    override fun songs(): List<Song> {
        return topPlayedRepository.topTracks()
    }
}