package com.capstone.yafood.data.entity

data class Recipe(
    val id:Int,
    val name: String,
    val ingredients: String,
    val procedure: String,
    val imageUrl:String
)