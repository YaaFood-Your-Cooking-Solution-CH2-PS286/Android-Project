package com.capstone.yafood.data.entity

data class Article(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val userCreated: User,
    val likeCount: Int = 0,
    val commentCount: Int = 0,
)