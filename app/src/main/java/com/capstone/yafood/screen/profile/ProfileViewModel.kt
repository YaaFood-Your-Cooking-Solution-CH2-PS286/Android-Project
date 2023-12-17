package com.capstone.yafood.screen.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.yafood.UiState
import com.capstone.yafood.data.entity.Article
import com.capstone.yafood.data.entity.User
import com.capstone.yafood.data.repository.ArticleRepository
import com.capstone.yafood.data.repository.UserRepository
import com.capstone.yafood.utils.UserState

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _userData = MutableLiveData<UserState<User>>()
    val userData: LiveData<UserState<User>> = _userData

    private val _userArticles = MutableLiveData<UiState<List<Article>>>()
    val userArticles: LiveData<UiState<List<Article>>> = _userArticles

    init {
        _userData.value = UserState.Loading
        val userId = userRepository.userPreferences.getUserId()
        if (userRepository.userPreferences.getToken() != null) {
            userRepository.getUserDetail(_userData)
            if (userId > -1) {
                articleRepository.getUserArticles(_userArticles, userId)
            }
        } else {
            _userData.value = UserState.Unauthorized
        }
    }

}