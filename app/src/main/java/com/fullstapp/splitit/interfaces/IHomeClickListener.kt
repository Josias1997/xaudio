package com.fullstapp.splitit.interfaces

import com.fullstapp.splitit.model.Album
import com.fullstapp.splitit.model.Artist
import com.fullstapp.splitit.model.Genre

interface IHomeClickListener {
    fun onAlbumClick(album: Album)

    fun onArtistClick(artist: Artist)

    fun onGenreClick(genre: Genre)
}