package com.capstone.yafood.data.api.response

import com.capstone.yafood.data.entity.Article
import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    val success: Boolean,

    @field:SerializedName("msg")
    val message: String,

    val data: List<Article>
)