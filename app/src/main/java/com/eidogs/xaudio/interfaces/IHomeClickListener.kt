package com.eidogs.xaudio.interfaces

import com.eidogs.xaudio.model.Album
import com.eidogs.xaudio.model.Artist
import com.eidogs.xaudio.model.Genre

interface IHomeClickListener {
    fun onAlbumClick(album: Album)

    fun onArtistClick(artist: Artist)

    fun onGenreClick(genre: Genre)
}