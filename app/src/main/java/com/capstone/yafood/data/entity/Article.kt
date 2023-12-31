package com.capstone.yafood.data.entity

import com.google.gson.annotations.SerializedName

data class Article(
    val id: Int,
    @field:SerializedName("titleArtikel")
    val title: String,
    val imageUrl: String,
    @field:SerializedName("user")
    val userCreated: User,
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val description: String,

    @field:SerializedName("ingredients")
    val ingredients: List<String>,

    @field:SerializedName("steps")
    val procedure: List<String>
)