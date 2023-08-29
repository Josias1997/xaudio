package com.fullstapp.splitit.network

import com.fullstapp.splitit.model.SplitAudioResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SplitAudioService {
    @Multipart
    @POST("/split-audio")
    fun splitAudio(
        @Part file: MultipartBody.Part,
        @Part("file") name: RequestBody
    ) : Call<SplitAudioResponse>
}