package com.capstone.yafood.data.api.response

import com.capstone.yafood.data.entity.Comment
import com.google.gson.annotations.SerializedName

data class GetCommentResponse(
    @field:SerializedName("msg")
    val msg: String,

    @field:SerializedName("data")
    val data: List<Comment>,

    @field:SerializedName("success")
    val success: Boolean
)
