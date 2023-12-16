package com.capstone.yafood.screen.recipedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.capstone.yafood.data.entity.Recipe
import com.capstone.yafood.data.entity.User
import com.capstone.yafood.data.repository.RecipeRepository
import com.capstone.yafood.data.repository.UserRepository
import com.capstone.yafood.utils.UserState

class RecipeDetailViewModel(
    private val recipeRepository: RecipeRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val recipeId = MutableLiveData<Int>()
    val userState = MutableLiveData<UserState<User>>()

    init {
        userRepository.getUserDetail(userState)
    }

    fun setRecipeId(id: Int) {
        recipeId.value = id
    }

    fun getDetailRecipe(): LiveData<Recipe> = recipeId.switchMap {
        recipeRepository.getRecipeDetail(it)
    }
}