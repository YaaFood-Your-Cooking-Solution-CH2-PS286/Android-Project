package com.capstone.yafood.data.repository

import android.content.Context
import com.capstone.yafood.data.entity.User
import com.capstone.yafood.data.FakeData

class UserRepository(private val context: Context) {
    fun getUserDetail(): User {
        return FakeData.dummyUserData()
    }

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(context).also { INSTANCE = it }
            }
        }
    }
}