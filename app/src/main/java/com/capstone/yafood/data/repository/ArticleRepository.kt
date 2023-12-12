package com.capstone.yafood.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.yafood.data.FakeData
import com.capstone.yafood.data.api.ApiConfig
import com.capstone.yafood.data.api.response.ArticleResponse
import com.capstone.yafood.data.entity.Article
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepository {
    fun getNewstArticles(): LiveData<List<Article>> {
        val liveData = MutableLiveData<List<Article>>()
        liveData.value = FakeData.dummyListArticles()
        return liveData
    }

    fun getUserArticles(): LiveData<List<Article>> {
        val liveData = MutableLiveData<List<Article>>()
        liveData.value = FakeData.dummyListUserArtciles()
        return liveData
    }

    fun getDailyArticles(): LiveData<List<Article>> {
        val liveData = MutableLiveData<List<Article>>()
//        liveData.value = FakeData.dummyListArticles()
        val client = ApiConfig.getApiService().getDailyArticle()
        client.enqueue(object : Callback<ArticleResponse> {

            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.let {
                        Log.d("GetDailyArticles", response.toString())
                        liveData.value = data.data
                    }
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Log.e("GetDailyArticles", t.message.toString())
            }
        })
        return liveData
    }

    companion object {
        @Volatile
        private var INSTANCE: ArticleRepository? = null

        fun getInstance(): ArticleRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ArticleRepository().also { INSTANCE = it }
            }
        }
    }
}