package com.capstone.yafood.screen.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.yafood.UiState
import com.capstone.yafood.data.entity.Article
import com.capstone.yafood.data.repository.ArticleRepository

class SearchViewModel(
    private val articleRepository: ArticleRepository
) : ViewModel() {
    private val _articles = MutableLiveData<UiState<List<Article>>>()
    private val _query = MutableLiveData<String>()
    val query: LiveData<String> get() = _query
    val article: LiveData<UiState<List<Article>>> get() = _articles

    init {
        articleRepository.getRandomArticles(_articles, 10)
    }

    fun searchArticles(q: String) {
        if (q.isNotEmpty()) {
            articleRepository.findArticles(_articles, q)
        }
    }

    fun postQuery(q: String) {
        _query.postValue(q)
    }
}