package com.eidogs.xaudio.interfaces

import android.view.View
import com.eidogs.xaudio.db.PlaylistWithSongs

interface IPlaylistClickListener {
    fun onPlaylistClick(playlistWithSongs: PlaylistWithSongs, view: View)
}