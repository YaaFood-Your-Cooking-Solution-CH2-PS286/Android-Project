package com.capstone.yafood.screen.recomendation

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.yafood.R
import com.capstone.yafood.UiState
import com.capstone.yafood.data.FakeData
import com.capstone.yafood.data.api.ApiConfig
import com.capstone.yafood.data.api.response.RecomendationResponse
import com.capstone.yafood.data.api.response.RecomendationResult
import com.capstone.yafood.data.entity.Ingredient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecomendationViewModel(
    private val application: Application
) : ViewModel() {
    private val _uiState = MutableLiveData<UiState<RecomendationResult>>()
    val uiState: LiveData<UiState<RecomendationResult>> get() = _uiState

    init {
        val result: RecomendationResult = FakeData.dummyRecomendationResult()
        _uiState.value = UiState.Success(result)
    }

    fun getRecomendation(ingredients: List<Ingredient>) {
        _uiState.value = UiState.Loading
        val client = ApiConfig.getApiService().getRecomendationFromIngredient(ingredients)
        client.enqueue(object : Callback<RecomendationResponse> {
            override fun onResponse(
                call: Call<RecomendationResponse>,
                response: Response<RecomendationResponse>
            ) {
                val result = response.body()
                result?.let {
                    if (response.isSuccessful) {
                        _uiState.value = UiState.Success(it.result)
                    } else {
                        _uiState.value =
                            UiState.Error(application.resources.getString(R.string.error_get_recomendation))
                        Log.e("GET_RECOMENDATION", it.message)
                    }
                }
            }

            override fun onFailure(call: Call<RecomendationResponse>, t: Throwable) {
                Log.e("GET_RECOMENDATION", t.message.toString())
                _uiState.value =
                    UiState.Error(application.resources.getString(R.string.error_get_recomendation))
            }

        })
    }
}