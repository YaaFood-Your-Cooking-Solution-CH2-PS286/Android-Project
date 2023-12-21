package com.capstone.yafood.data.entity

import com.google.gson.annotations.SerializedName

data class Comment(
    val id: Int,
    @field:SerializedName("contentComment")
    val content: String,
    @field:SerializedName("user")
    val user: User
)