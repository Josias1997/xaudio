package com.fullstapp.splitit.interfaces

import android.view.View
import com.fullstapp.splitit.model.Genre

interface IGenreClickListener {
    fun onClickGenre(genre: Genre, view: View)
}