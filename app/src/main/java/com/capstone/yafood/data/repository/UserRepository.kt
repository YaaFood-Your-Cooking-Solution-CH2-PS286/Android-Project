package com.capstone.yafood.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.yafood.data.entity.User
import com.capstone.yafood.data.FakeData
import com.capstone.yafood.data.api.ApiConfig
import com.capstone.yafood.data.api.response.UserDetailResponse
import com.capstone.yafood.data.local.UserPreferences
import com.capstone.yafood.utils.UserState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val context: Context) {
    val userPreferences = UserPreferences.getInstance(context)
    fun getUserDetail(state: MutableLiveData<UserState<User>>) {
        state.value = UserState.Loading
        val client = ApiConfig.getApiService().getUserDetail()
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val resBody = response.body()
                    resBody?.let {
                        Log.d(TAG, resBody.toString())
                        state.value = UserState.Success(it.user)
                    }
                } else {
                    state.value = UserState.Unauthorized
                }
                Log.e(TAG, "Error : ${response.errorBody()?.string()}")
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                state.value = UserState.Unauthorized
            }
        })
    }

    fun clearUserData() {
        userPreferences.clearUser()
    }

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(context).also { INSTANCE = it }
            }
        }

        const val TAG = "UserRepo"
    }
}