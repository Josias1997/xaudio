package com.eidogs.xaudio.model

import com.google.gson.annotations.SerializedName

data class SplitAudioResponse(
    @SerializedName("drums")
    val drums: String,
    @SerializedName("bass")
    val bass: String,
    @SerializedName("piano")
    val piano: String,
    @SerializedName("vocals")
    val vocals: String,
    @SerializedName("other")
    val other: String
)
