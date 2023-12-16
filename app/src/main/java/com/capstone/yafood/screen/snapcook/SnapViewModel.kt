package com.capstone.yafood.screen.snapcook

import android.app.Application
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.yafood.UiState
import com.capstone.yafood.data.api.ApiConfig
import com.capstone.yafood.data.api.response.RecomendationResponse
import com.capstone.yafood.data.api.response.RecomendationResult
import com.capstone.yafood.utils.ImageUtils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SnapViewModel(
    private val application: Application
) : ViewModel() {
    private val _uiState = MutableLiveData<UiState<RecomendationResult>>()
    val uiState: LiveData<UiState<RecomendationResult>> get() = _uiState

    private val _imageUri = MutableLiveData<String>()
    val imageUri: LiveData<String> get() = _imageUri
    fun setImageUri(imageUri: String) {
        _imageUri.value = imageUri
    }
}