package com.capstone.yafood.data.api.response

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("msg")
    val message: String,

    @field:SerializedName("data")
    val result: String,
)