package com.capstone.yafood.data.entity

data class User(
    val id: Int,
    val username: String,
    val name: String,
    val rankPoints: Int,
    val photoUrl: String? = null
)