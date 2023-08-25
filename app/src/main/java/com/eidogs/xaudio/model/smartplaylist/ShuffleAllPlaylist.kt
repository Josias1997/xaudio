package com.eidogs.xaudio.model.smartplaylist

import com.eidogs.xaudio.App
import com.eidogs.xaudio.R
import com.eidogs.xaudio.model.Song
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