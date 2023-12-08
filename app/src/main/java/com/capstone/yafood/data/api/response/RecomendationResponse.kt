package com.capstone.yafood.data.api.response

import com.google.gson.annotations.SerializedName

data class RecomendationResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    val result: RecomendationResult
)

data class RecomendationResult(
    val recipe: List<String>
)