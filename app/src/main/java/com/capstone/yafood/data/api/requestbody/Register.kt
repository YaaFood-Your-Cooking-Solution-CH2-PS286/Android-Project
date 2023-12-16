package com.capstone.yafood.data.api.requestbody

data class Register(
    val name:String,
    val email:String,
    val password:String,
    val confPassword:String,
)