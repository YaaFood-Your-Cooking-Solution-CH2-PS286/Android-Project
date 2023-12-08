package com.capstone.yafood.data.api

import com.capstone.yafood.data.api.response.RecomendationResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("/recomendation")
    fun getRecomendation(
        @Part image: MultipartBody.Part
    ): Call<RecomendationResponse>

}