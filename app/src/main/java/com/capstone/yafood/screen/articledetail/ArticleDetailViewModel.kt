package com.capstone.yafood.screen.articledetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.capstone.yafood.UiState
import com.capstone.yafood.data.api.ApiConfig
import com.capstone.yafood.data.api.response.ArticleDetailResponse
import com.capstone.yafood.data.entity.Article
import com.capstone.yafood.data.entity.Recipe
import com.capstone.yafood.data.entity.User
import com.capstone.yafood.data.repository.ArticleRepository
import com.capstone.yafood.data.repository.UserRepository
import com.capstone.yafood.utils.UserState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleDetailViewModel(
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableLiveData<UiState<Article>>()
    val uiState: LiveData<UiState<Article>> get() = _uiState
    fun setArticleId(id: Int) {
        ApiConfig.getApiService().getArticleDetail(id)
            .enqueue(object : Callback<ArticleDetailResponse> {
                override fun onResponse(
                    call: Call<ArticleDetailResponse>,
                    response: Response<ArticleDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.i(TAG, "Response : ${response.body()}")
                        response.body()?.let {
                            _uiState.value = UiState.Success(it.data)
                        }
                    } else {
                        _uiState.value = UiState.Error(
                            response.errorBody()?.string() ?: "Tidak dapat mengambbil detail"
                        )
                        Log.e(TAG, "Error $TAG : ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<ArticleDetailResponse>, t: Throwable) {
                    _uiState.value = UiState.Error("Failure: Tidak dapat mengambbil detail")
                    Log.e(TAG, "Failure $TAG : ${t.message}")
                }
            })
    }


    val user = MutableLiveData<UserState<User>>()

    init {
        userRepository.getUserDetail(user)
    }


    fun deleteArticle(articleId: Int, callback: (Boolean) -> Unit) {
        articleRepository.deleteArticle(articleId) { success ->
            callback(success)
        }
    }

    companion object {
        const val TAG = "ArticleDetail"
    }
}