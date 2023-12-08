package com.capstone.yafood.screen.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.yafood.data.entity.Article
import com.capstone.yafood.data.entity.User
import com.capstone.yafood.data.repository.ArticleRepository
import com.capstone.yafood.data.repository.UserRepository

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository
) : ViewModel() {
    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User> = _userData
    fun getUserArticles(): LiveData<List<Article>> = articleRepository.getUserArticles()

    init {
        _userData.value = userRepository.getUserDetail()
    }
}