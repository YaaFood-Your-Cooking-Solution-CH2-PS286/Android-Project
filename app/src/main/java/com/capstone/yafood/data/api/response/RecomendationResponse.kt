package com.capstone.yafood.data.api.response

import com.capstone.yafood.data.entity.Ingredient
import com.capstone.yafood.data.entity.Recipe
import com.google.gson.annotations.SerializedName

data class RecomendationResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    val result: RecomendationResult
)

data class RecomendationResult(
    val ingredientDetected:List<Ingredient>,
    val recipe: List<Recipe>
)