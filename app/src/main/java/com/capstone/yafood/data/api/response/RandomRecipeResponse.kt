package com.capstone.yafood.data.api.response

import com.capstone.yafood.data.entity.RecipeIdea
import com.google.gson.annotations.SerializedName

data class RandomRecipeResponse(

    @field:SerializedName("msg")
    val msg: String,

    @field:SerializedName("data")
    val data: RecipeIdea,

    @field:SerializedName("success")
    val success: Boolean
)
