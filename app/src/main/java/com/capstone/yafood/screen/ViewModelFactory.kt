package com.capstone.yafood.screen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.yafood.data.repository.ArticleRepository
import com.capstone.yafood.data.repository.RecipeRepository
import com.capstone.yafood.data.repository.UserRepository
import com.capstone.yafood.screen.home.HomeViewModel
import com.capstone.yafood.screen.profile.ProfileViewModel
import com.capstone.yafood.screen.recomendation.RecomendationViewModel
import com.capstone.yafood.screen.snapcook.SnapViewModel

class ViewModelFactory private constructor(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            (modelClass.isAssignableFrom(HomeViewModel::class.java)) -> {
                val userRepository = UserRepository.getInstance(application)
                val articleRepository = ArticleRepository.getInstance()
                val recipeRepository = RecipeRepository.getInstance()
                return HomeViewModel(userRepository, articleRepository, recipeRepository) as T
            }

            (modelClass.isAssignableFrom(ProfileViewModel::class.java)) -> {
                val userRepository = UserRepository.getInstance(application)
                val articleRepository = ArticleRepository.getInstance()
                return ProfileViewModel(userRepository, articleRepository) as T
            }

            (modelClass.isAssignableFrom(SnapViewModel::class.java)) -> {
                return SnapViewModel(application) as T
            }

            (modelClass.isAssignableFrom(RecomendationViewModel::class.java)) -> {
                return RecomendationViewModel(application) as T
            }
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}