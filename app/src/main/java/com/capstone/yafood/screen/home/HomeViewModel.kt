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

class HomeViewModel(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
    private val recipeRepository: RecipeRepository,
    private val communityRepository: CommunityRepository
) : ViewModel() {
    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User> get() = _userData
    fun getNewestArticles(): LiveData<List<Article>> = articleRepository.getDailyArticles()

    fun getRecipeIdea(): LiveData<RecipeIdea> = recipeRepository.getRecipeIdea()

    fun getCommunities(): LiveData<List<Community>> = communityRepository.getRandomCommunity()

    init {
        _userData.value = userRepository.getUserDetail()
    }

}