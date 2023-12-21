package com.capstone.yafood.data.api.response

import com.capstone.yafood.data.entity.Comment
import com.google.gson.annotations.SerializedName

data class PostCommentResponse(

    @field:SerializedName("msg")
    val msg: String,

    @field:SerializedName("data")
    val data: Comment,

    @field:SerializedName("success")
    val success: Boolean
)

