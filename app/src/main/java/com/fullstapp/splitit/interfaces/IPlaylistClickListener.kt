package com.fullstapp.splitit.interfaces

import android.view.View
import com.fullstapp.splitit.db.PlaylistWithSongs

interface IPlaylistClickListener {
    fun onPlaylistClick(playlistWithSongs: PlaylistWithSongs, view: View)
}