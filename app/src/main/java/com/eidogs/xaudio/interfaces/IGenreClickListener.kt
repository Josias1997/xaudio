package com.eidogs.xaudio.interfaces

import android.view.View
import com.eidogs.xaudio.model.Genre

interface IGenreClickListener {
    fun onClickGenre(genre: Genre, view: View)
}