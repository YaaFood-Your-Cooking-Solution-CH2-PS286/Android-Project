package com.capstone.yafood.screen.recipedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.capstone.yafood.data.entity.Recipe
import com.capstone.yafood.data.entity.User
import com.capstone.yafood.data.repository.RecipeRepository
import com.capstone.yafood.data.repository.UserRepository

class RecipeDetailViewModel(
    private val recipeRepository: RecipeRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val recipeId = MutableLiveData<Int>()
    val user = MutableLiveData<User>()
    init {
        user.value = userRepository.getUserDetail()
    }
    fun setRecipeId(id:Int){
        recipeId.value = id
    }
    fun getDetailRecipe(): LiveData<Recipe> = recipeId.switchMap {
        recipeRepository.getRecipeDetail(it)
    }
}