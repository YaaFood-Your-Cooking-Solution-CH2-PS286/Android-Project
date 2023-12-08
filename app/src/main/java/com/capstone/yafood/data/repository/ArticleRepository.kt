package com.capstone.yafood.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.yafood.data.FakeData
import com.capstone.yafood.data.entity.Article

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