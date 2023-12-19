package com.capstone.yafood.adapter

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @field:SerializedName("success")
    val success: String,

    @field:SerializedName("msg")
    val message: String,
)
