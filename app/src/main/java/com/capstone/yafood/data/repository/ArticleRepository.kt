package com.capstone.yafood.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.yafood.UiState
import com.capstone.yafood.data.FakeData
import com.capstone.yafood.data.api.ApiConfig
import com.capstone.yafood.data.api.response.ArticleResponse
import com.capstone.yafood.data.entity.Article
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepository {

    fun getAllArticles(state: MutableLiveData<UiState<List<Article>>>) {
        state.value = UiState.Loading
        ApiConfig.getApiService().getAllArticle().enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        state.value = UiState.Success(it.data)
                    }
                } else {
                    Log.e(TAG, "Failure : ${response.errorBody()?.string()}")
                    state.value = UiState.Error(response.errorBody()?.string() ?: "Error")
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                state.value = UiState.Error(t.message.toString())
                Log.e(TAG, "Failure : ${t.message}")
            }
        })
    }

    fun getUserArticles(
        state: MutableLiveData<UiState<List<Article>>>,
        userId: Int
    ) {
        state.value = UiState.Loading
        val client = ApiConfig.getApiService().getUserArticle(userId)
        client.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "${response.body()}")
                    response.body()?.let {
                        state.value = UiState.Success(it.data)
                        Log.d(TAG, it.toString())
                    }
                } else {
                    Log.d(TAG, "Error : ${response.errorBody()?.string()}")
                    UiState.Error("Error ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Log.d(TAG, "Failure: ${t.message.toString()}")
                UiState.Error("Error ${t.message.toString()}")
            }
        })

    }

    fun getDailyArticles(): LiveData<List<Article>> {
        val liveData = MutableLiveData<List<Article>>()
        val client = ApiConfig.getApiService().getDailyArticle()
        client.enqueue(object : Callback<ArticleResponse> {

            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.let {
                        Log.d(TAG, "Daily Articles : ${response.body()}")
                        liveData.value = data.data
                    }
                } else {
                    Log.e(TAG, "Daily Articles Error : ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Log.e(TAG, "Daily Articles Failure : ${t.message.toString()}")
            }
        })
        return liveData
    }

    fun getRandomArticles(state: MutableLiveData<UiState<List<Article>>>, count: Int? = null) {
        state.value = UiState.Loading
        ApiConfig.getApiService().getDailyArticle(count)
            .enqueue(object : Callback<ArticleResponse> {
                override fun onResponse(
                    call: Call<ArticleResponse>,
                    response: Response<ArticleResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        data?.let {
                            Log.d(TAG, "Daily Articles : ${response.body()}")
                            state.value = UiState.Success(data.data)
                        }
                    } else {
                        state.value =
                            UiState.Error(
                                response.errorBody()?.string() ?: "Error ${response.code()}"
                            )
                        Log.e(TAG, "Daily Articles Error : ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                    state.value = UiState.Error(t.message.toString())
                    Log.e(TAG, "Daily Articles Failure : ${t.message.toString()}")
                }
            })
    }

    fun findArticles(state: MutableLiveData<UiState<List<Article>>>, query: String) {
        state.value = UiState.Loading
        ApiConfig.getApiService().findArticles(query).enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        state.value = UiState.Success(it.data)
                    }
                } else {
                    Log.e(TAG, "Error : ${response.errorBody()?.string()}")
                    state.value =
                        UiState.Error(response.errorBody()?.string() ?: "Error ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Log.e(TAG, "Failure : ${t.message}")
                state.value = UiState.Error(t.message.toString())
            }
        })
    }

    fun getArticleDetail(articleId: Int): LiveData<Article> {
        val liveData = MutableLiveData<Article>()
        liveData.value = FakeData.dummyArticle(articleId)
        return liveData
    }

    fun deleteArticle(articleId: Int, callback: (Boolean) -> Unit) {
        val client = ApiConfig.getApiService().deleteArticle(articleId)
        client.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Article deleted successfully")
                    callback(true)
                } else {
                    Log.e(TAG, "Failed to delete article: ${response.errorBody()?.string()}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Log.e(TAG, "Failed to delete article: ${t.message}")
                callback(false)
            }
        })
    }


    companion object {
        @Volatile
        private var INSTANCE: ArticleRepository? = null

        fun getInstance(): ArticleRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ArticleRepository().also { INSTANCE = it }
            }
        }

        const val TAG = "ArticleRepo"
    }
}