package com.capstone.yafood.data.entity

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val username: String,
    val name: String,
    val rankPoints: Int,
    @field:SerializedName("imageUrl")
    val photoUrl: String? = null
)