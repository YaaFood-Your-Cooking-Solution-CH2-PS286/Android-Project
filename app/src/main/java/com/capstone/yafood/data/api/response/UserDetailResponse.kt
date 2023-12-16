package com.capstone.yafood.data.api.response

import com.capstone.yafood.data.entity.User
import com.google.gson.annotations.SerializedName

data class UserDetailResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("data")
	val user: User,

	@field:SerializedName("success")
	val success: Boolean
)

