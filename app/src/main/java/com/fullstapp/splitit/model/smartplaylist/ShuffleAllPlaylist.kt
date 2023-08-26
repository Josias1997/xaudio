package com.fullstapp.splitit.model.smartplaylist

import com.fullstapp.splitit.App
import com.fullstapp.splitit.R
import com.fullstapp.splitit.model.Song
import kotlinx.parcelize.Parcelize

@Parcelize
class ShuffleAllPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.action_shuffle_all),
    iconRes = R.drawable.ic_shuffle
) {
    override fun songs(): List<Song> {
        return songRepository.songs()
    }
}