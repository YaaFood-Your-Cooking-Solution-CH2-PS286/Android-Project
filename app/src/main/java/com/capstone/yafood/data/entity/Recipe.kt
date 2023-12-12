package com.capstone.yafood.data.entity

data class Recipe(
    val id:Int,
    val name: String,
    val ingredients: List<String>,
    val procedure: List<String>,
    val imageUrl:String
)