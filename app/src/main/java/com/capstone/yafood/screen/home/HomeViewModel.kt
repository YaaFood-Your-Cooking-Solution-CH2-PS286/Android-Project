package com.capstone.yafood.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.yafood.data.entity.Article
import com.capstone.yafood.data.entity.Community
import com.capstone.yafood.data.entity.RecipeIdea
import com.capstone.yafood.data.entity.User
import com.capstone.yafood.data.repository.ArticleRepository
import com.capstone.yafood.data.repository.CommunityRepository
import com.capstone.yafood.data.repository.RecipeRepository
import com.capstone.yafood.data.repository.UserRepository
import com.capstone.yafood.utils.UserState

class HomeViewModel(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
    private val recipeRepository: RecipeRepository,
    private val communityRepository: CommunityRepository
) : ViewModel() {
    private val _userState = MutableLiveData<UserState<User>>()
    val userState: LiveData<UserState<User>> get() = _userState
    fun getNewestArticles(): LiveData<List<Article>> = articleRepository.getDailyArticles()

    fun getRecipeIdea(): LiveData<RecipeIdea> = recipeRepository.getRecipeIdea()

    fun getCommunities(): LiveData<List<Community>> = communityRepository.getRandomCommunity()

    init {
        userRepository.getUserDetail(_userState)
    }

}