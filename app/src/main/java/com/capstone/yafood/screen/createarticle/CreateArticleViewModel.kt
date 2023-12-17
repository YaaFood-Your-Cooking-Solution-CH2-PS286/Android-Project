package com.capstone.yafood.screen.createarticle

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.yafood.R
import com.capstone.yafood.UiState
import com.capstone.yafood.data.api.ApiConfig
import com.capstone.yafood.data.api.response.PostArticleResponse
import com.capstone.yafood.utils.ImageUtils
import com.capstone.yafood.utils.asRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class CreateArticleViewModel(
    private val application: Application
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState<String>>()
    val uiState: LiveData<UiState<String>> get() = _uiState
    private val _imageUri = MutableLiveData<String?>()
    val imageUri: LiveData<String?> get() = _imageUri
    fun setImageUri(uri: String?) {
        _imageUri.value = uri
    }

    private val listIngredientValue = arrayListOf("", "")
    private val _listIngredientInput = MutableLiveData(listIngredientValue)
    val listIngredientInput: LiveData<ArrayList<String>> get() = _listIngredientInput

    private val listStepValue = arrayListOf("", "")
    private val _listStepInput = MutableLiveData(listStepValue)
    val listStepInput: LiveData<ArrayList<String>> get() = _listStepInput

    fun deleteIngredientInput(position: Int) {
        listIngredientValue.removeAt(position)
        _listIngredientInput.value = ArrayList(listIngredientValue)
    }

    fun addIngredientInput() {
        listIngredientValue.add("")
        _listIngredientInput.value = ArrayList(listIngredientValue)
    }

    fun updateIngredientValue(newValue: String, position: Int) {
        listIngredientValue[position] = newValue
    }

    fun deleteStepInput(position: Int) {
        listStepValue.removeAt(position)
        _listStepInput.value = ArrayList(listStepValue)
    }

    fun addStepInput() {
        listStepValue.add("")
        _listStepInput.value = ArrayList(listStepValue)
    }

    fun updateStepValue(newValue: String, position: Int) {
        listStepValue[position] = newValue
    }

    fun postArticle(title: String, description: String) {
        val steps = listStepValue.filter { value -> value.isNotEmpty() }.joinToString("--")
        val ingredients =
            listIngredientValue.filter { value -> value.isNotEmpty() }.joinToString("--")
        if (imageUri.value == null) {
            _uiState.value = UiState.Error(application.getString(R.string.input_image_article))
        }
        imageUri.value?.let {
            _uiState.value = UiState.Loading
            val imageFile = ImageUtils.uriToFile(it.toUri(), application)

            val reqFile = MultipartBody.Part.createFormData(
                "file",
                imageFile.name,
                imageFile.asRequestBody("image/jpg".toMediaType())
            )

            val client = ApiConfig.getApiService().postArticle(
                image = reqFile,
                title = title.asRequestBody(),
                description = description.asRequestBody(),
                ingredients = ingredients.asRequestBody(),
                steps = steps.asRequestBody()
            )

            client.enqueue(object : Callback<PostArticleResponse> {
                override fun onResponse(
                    call: Call<PostArticleResponse>,
                    response: Response<PostArticleResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.i(TAG, "Success : ${response.body()}")
                        _uiState.value =
                            UiState.Success(application.getString(R.string.success_create_article))
                    } else {
                        Log.e(TAG, "Error ${response.code()}: ${response.errorBody()?.string()}")
                        _uiState.value =
                            UiState.Error(application.getString(R.string.failed_create_article))
                    }
                }

                override fun onFailure(call: Call<PostArticleResponse>, t: Throwable) {
                    Log.e(TAG, t.message.toString())
                    _uiState.value =
                        UiState.Error(application.getString(R.string.failed_create_article))
                }

            })
        }
    }

    companion object {
        const val TAG = "CreateArticle"
    }
}