package com.capstone.yafood.data.api.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @field:SerializedName("errors")
    val errors: List<ErrorBody>,

    @field:SerializedName("msg")
    val msg: String,

    @field:SerializedName("Success")
    val success: Boolean,

    )

data class ErrorBody(
    @field:SerializedName("msg")
    val message: String
)
