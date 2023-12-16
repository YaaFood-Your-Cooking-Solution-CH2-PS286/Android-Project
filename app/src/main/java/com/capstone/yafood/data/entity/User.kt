package com.capstone.yafood.data.entity

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val email: String,
    val name: String,
    val rankPoints: Int = 0,
    @field:SerializedName("imageUrl")
    val photoUrl: String? = null
)