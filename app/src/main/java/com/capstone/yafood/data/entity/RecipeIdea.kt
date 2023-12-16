package com.capstone.yafood.data.entity

import com.google.gson.annotations.SerializedName

data class RecipeIdea(
    @field:SerializedName("Bahan")
    val ingredient: Ingredient,

    @field:SerializedName("Recipes")
    val recipes: List<Recipe>
)
