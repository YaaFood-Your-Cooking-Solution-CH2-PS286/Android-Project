package com.capstone.yafood.utils

sealed class UserState<out T : Any?> {

    object Loading : UserState<Nothing>()

    data class Success<out T : Any>(val data: T) : UserState<T>()

    object Unauthorized : UserState<Nothing>()
}