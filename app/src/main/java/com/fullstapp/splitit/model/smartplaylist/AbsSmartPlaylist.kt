package com.fullstapp.splitit.model.smartplaylist

import androidx.annotation.DrawableRes
import com.fullstapp.splitit.R
import com.fullstapp.splitit.model.AbsCustomPlaylist

abstract class AbsSmartPlaylist(
    name: String,
    @DrawableRes val iconRes: Int = R.drawable.ic_queue_music
) : AbsCustomPlaylist(
    id = PlaylistIdGenerator(name, iconRes),
    name = name
)