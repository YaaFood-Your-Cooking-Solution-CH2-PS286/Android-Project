package com.capstone.yafood.screen.recomendation

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
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
import com.capstone.yafood.utils.ImageUtils
import com.capstone.yafood.utils.ImageUtils.reduceFileImage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecomendationViewModel(
    private val application: Application
) : ViewModel() {
    private val _uiState = MutableLiveData<UiState<RecomendationResult>>()
    val uiState: LiveData<UiState<RecomendationResult>> get() = _uiState

    private val listIngredients: MutableList<String> = mutableListOf()

    private fun getRecomendation(ingredients: MutableList<String>) {
        _uiState.value = UiState.Loading
        val client =
            ApiConfig.getApiService().getRecomendationFromIngredient(ingredients.joinToString("--"))
        client.enqueue(object : Callback<RecomendationResponse> {
            override fun onResponse(
                call: Call<RecomendationResponse>,
                response: Response<RecomendationResponse>
            ) {
                val result = response.body()
                Log.d(TAG, "Response X: ${response.body()}")
                result?.let {
                    if (response.isSuccessful) {
                        _uiState.value = UiState.Success(it.result)
                        listIngredients.clear()
                        it.result.ingredientDetected.forEach { item ->
                            listIngredients.add(item)
                        }

                    } else {
                        _uiState.value =
                            UiState.Error(application.resources.getString(R.string.error_get_recomendation))
                        Log.e(TAG, it.message)
                    }
                }
            }

            override fun onFailure(call: Call<RecomendationResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                _uiState.value =
                    UiState.Error(application.resources.getString(R.string.error_get_recomendation))
            }

        })
    }

    fun deleteIngredient(ingredient: String) {
        if (listIngredients.size > 1) {
            listIngredients.remove(ingredient)
            getRecomendation(listIngredients)
        } else {
            Toast.makeText(
                application,
                application.getString(R.string.cant_remove_ingredient),
                Toast.LENGTH_SHORT
            ).show()
            _uiState.value?.let {
                _uiState.value = it
            }
        }
    }


    fun getRecomendationByImage(image: String) {
        _uiState.value = UiState.Loading
        val imageFile = ImageUtils.uriToFile(image.toUri(), application).reduceFileImage()
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())

        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )

        val client = ApiConfig.getMlService().getRecomendation(multipartBody)
        client.enqueue(object : Callback<RecomendationResponse> {
            override fun onResponse(
                call: Call<RecomendationResponse>,
                response: Response<RecomendationResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d(TAG, "Response : ${response.body()}")
                        _uiState.value = UiState.Success(it.result)
                        it.result.ingredientDetected.forEach { item ->
                            listIngredients.add(item)
                        }
                    }
                } else {
                    _uiState.value = UiState.Error(response.message())
                    Log.e(TAG, response.message())
                    Log.d(TAG, "Error : ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<RecomendationResponse>, t: Throwable) {
                _uiState.value = UiState.Error(t.message.toString())
                Log.e(TAG, "Failure : ${t.message}")
            }

        })
    }

    fun updateListIngredients(items: List<String>) {
        items.forEach {
            listIngredients.add(it)
        }
        getRecomendation(listIngredients)
    }

    companion object {
        const val TAG = "RecomendationModel"
    }
}