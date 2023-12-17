package com.capstone.yafood.data.entity

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @field:SerializedName("bahan")
    val name: String,
    val imageUrl: String
)
