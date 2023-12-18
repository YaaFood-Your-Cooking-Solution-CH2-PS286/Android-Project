package com.capstone.yafood.data.api.response

import com.capstone.yafood.data.entity.Article
import com.google.gson.annotations.SerializedName

data class ArticleDetailResponse(

    @field:SerializedName("msg")
    val msg: String,

    @field:SerializedName("data")
    val data: Article,

    @field:SerializedName("success")
    val success: Boolean
)

