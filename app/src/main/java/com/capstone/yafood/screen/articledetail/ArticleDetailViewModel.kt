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

class ArticleDetailViewModel(
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val articleId = MutableLiveData<Int>()
    val user = MutableLiveData<User>()
    init {
        user.value = userRepository.getUserDetail()
    }
    fun setArticleId(id:Int){
        articleId.value = id
    }
    fun getDetailArticle(): LiveData<Article> = articleId.switchMap {
        articleRepository.getArticleDetail(it)
    }
}