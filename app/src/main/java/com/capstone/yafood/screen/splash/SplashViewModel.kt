package com.capstone.yafood.screen.splash

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import com.capstone.yafood.data.api.ApiConfig
import com.capstone.yafood.data.local.UserPreferences

class SplashViewModel(
    application: Application
) : ViewModel() {

    init {
        val token = UserPreferences.getInstance(application).getToken()
        ApiConfig.setToken(token ?: "")
    }

    companion object {
        const val TAG = "SplashScreen"
    }
}