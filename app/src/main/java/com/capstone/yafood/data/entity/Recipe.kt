package com.capstone.yafood.data.entity

import com.google.gson.annotations.SerializedName

data class Recipe(
//    @field:SerializedName("Id")
    val id: Int,

    @field:SerializedName("Title")
    val name: String,

    @field:SerializedName("Ingredients")
    val ingredients: List<String>,

    @field:SerializedName("Steps")
    val procedure: List<String>,

    @field:SerializedName("URL")
    val imageUrl: String
)