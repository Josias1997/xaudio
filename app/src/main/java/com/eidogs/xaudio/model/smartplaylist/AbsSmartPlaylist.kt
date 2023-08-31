package com.eidogs.xaudio.model.smartplaylist

import androidx.annotation.DrawableRes
import com.eidogs.xaudio.R
import com.eidogs.xaudio.model.AbsCustomPlaylist

abstract class AbsSmartPlaylist(
    name: String,
    @DrawableRes val iconRes: Int = R.drawable.ic_queue_music
) : AbsCustomPlaylist(
    id = PlaylistIdGenerator(name, iconRes),
    name = name
)