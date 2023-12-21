package com.capstone.yafood.data.local

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply()
    }

    fun clearUser() {
        sharedPreferences.edit().remove(KEY_TOKEN).commit()
        sharedPreferences.edit().remove(USER_NAME).commit()
        sharedPreferences.edit().remove(USER_ID).commit()
    }

    fun getUserId(): Int {
        return sharedPreferences.getInt(USER_ID, -1)
    }

    fun saveUserId(id: Int) {
        sharedPreferences.edit().putInt(USER_ID, id).apply()
    }

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val KEY_TOKEN = "token"
        private const val USER_NAME = "user_name"
        private const val USER_ID = "user_id"

        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(context: Context): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserPreferences(context).also { INSTANCE = it }
            }
        }
    }
}