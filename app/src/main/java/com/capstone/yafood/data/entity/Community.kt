package com.capstone.yafood.data.entity

data class Community(
    val name: String,
    val photoUrl: String,
    val users: List<User>
)