package com.capstone.yafood.screen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.yafood.data.repository.ArticleRepository
import com.capstone.yafood.data.repository.CommunityRepository
import com.capstone.yafood.data.repository.RecipeRepository
import com.capstone.yafood.data.repository.UserRepository
import com.capstone.yafood.screen.auth.AuthViewModel
import com.capstone.yafood.screen.createarticle.CreateArticleViewModel
import com.capstone.yafood.screen.articledetail.ArticleDetailViewModel
import com.capstone.yafood.screen.home.HomeViewModel
import com.capstone.yafood.screen.profile.ProfileViewModel
import com.capstone.yafood.screen.recipedetail.RecipeDetailViewModel
import com.capstone.yafood.screen.recomendation.AddIngredientsViewModel
import com.capstone.yafood.screen.recomendation.RecomendationViewModel
import com.capstone.yafood.screen.search.SearchViewModel
import com.capstone.yafood.screen.settings.SettingsViewModel
import com.capstone.yafood.screen.snapcook.SnapViewModel
import com.capstone.yafood.screen.splash.SplashViewModel

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
                val communityRepository = CommunityRepository.getInstance()
                return HomeViewModel(
                    userRepository,
                    articleRepository,
                    recipeRepository,
                    communityRepository
                ) as T
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

            (modelClass.isAssignableFrom(ArticleDetailViewModel::class.java)) -> {
                val ArticleRepository = ArticleRepository.getInstance()
                val userRepository = UserRepository.getInstance(application)

                return ArticleDetailViewModel(ArticleRepository, userRepository) as T
            }

            (modelClass.isAssignableFrom(RecipeDetailViewModel::class.java)) -> {
                val recipeRepository = RecipeRepository.getInstance()
                val userRepository = UserRepository.getInstance(application)

                return RecipeDetailViewModel(recipeRepository, userRepository) as T
            }

            (modelClass.isAssignableFrom(CreateArticleViewModel::class.java)) -> {
                return CreateArticleViewModel(application) as T
            }

            (modelClass.isAssignableFrom(AuthViewModel::class.java)) -> {
                return AuthViewModel(application) as T
            }

            (modelClass.isAssignableFrom(SplashViewModel::class.java)) -> {
                return SplashViewModel(application) as T
            }

            (modelClass.isAssignableFrom(AddIngredientsViewModel::class.java)) -> {
                return AddIngredientsViewModel() as T
            }

            (modelClass.isAssignableFrom(SearchViewModel::class.java)) -> {
                val articleRepository = ArticleRepository.getInstance()
                return SearchViewModel(articleRepository) as T
            }

            (modelClass.isAssignableFrom(SettingsViewModel::class.java)) -> {
                val userRepo = UserRepository.getInstance(application)
                return SettingsViewModel(userRepo, application) as T
            }
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}