package com.capstone.yafood.data.api.response

import com.capstone.yafood.data.entity.Ingredient
import com.capstone.yafood.data.entity.Recipe
import com.google.gson.annotations.SerializedName

data class RecomendationResponse(
    @field:SerializedName("success")
    val success: String,

    @field:SerializedName("msg")
    val message: String,

    @field:SerializedName("data")
    val result: RecomendationResult
)

data class RecomendationResult(
    @field:SerializedName("ingredient_detected")
    val ingredientDetected: List<String>,

    @field:SerializedName("recipe")
    val recipe: List<Recipe>
)