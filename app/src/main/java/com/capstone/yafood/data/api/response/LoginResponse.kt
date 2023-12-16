package com.capstone.yafood.data.api.response

import com.capstone.yafood.data.entity.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("msg")
    val message: String,

    @field:SerializedName("accessToken")
    val accessToken: String,

    @field:SerializedName("user")
    val user: User
)

