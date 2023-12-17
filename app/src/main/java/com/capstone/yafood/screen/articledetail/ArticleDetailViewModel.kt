package com.capstone.yafood.screen.articledetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.capstone.yafood.data.entity.Article
import com.capstone.yafood.data.entity.Recipe
import com.capstone.yafood.data.entity.User
import com.capstone.yafood.data.repository.ArticleRepository
import com.capstone.yafood.data.repository.UserRepository
import com.capstone.yafood.utils.UserState

class ArticleDetailViewModel(
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val articleId = MutableLiveData<Int>()
    val user = MutableLiveData<UserState<User>>()

    init {
        userRepository.getUserDetail(user)
    }

    fun setArticleId(id: Int) {
        articleId.value = id
    }

    fun getDetailArticle(): LiveData<Article> = articleId.switchMap {
        articleRepository.getArticleDetail(it)
    }

    fun deleteArticle(articleId: Int, callback: (Boolean) -> Unit) {
        articleRepository.deleteArticle(articleId) { success ->
            callback(success)
        }
    }
}