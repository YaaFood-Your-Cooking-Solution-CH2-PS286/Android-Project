package com.capstone.yafood.data.api.response

import com.google.gson.annotations.SerializedName

data class PostArticleResponse(
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("msg")
    val message: String,
)