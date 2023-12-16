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
    @field:SerializedName("contentArtikel")
    val contentArticle: String,
    val ingredients: List<String>,
    val procedure: List<String>
)