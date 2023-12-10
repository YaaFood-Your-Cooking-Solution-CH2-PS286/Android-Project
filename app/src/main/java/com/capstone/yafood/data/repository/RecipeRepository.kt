package com.capstone.yafood.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.yafood.data.FakeData
import com.capstone.yafood.data.api.response.RecomendationResult
import com.capstone.yafood.data.entity.RecipeIdea

class RecipeRepository {
    fun getRecipeIdea(): LiveData<RecipeIdea> {
        val liveData = MutableLiveData<RecipeIdea>()
        liveData.value = FakeData.dummyRecipeIdea()
        return liveData
    }

//    fun getRecomendationRecipe():LiveData<RecomendationResult>{
//        val liveData = MutableLiveData<Rec>
//    }
    companion object {
        @Volatile
        private var INSTANCE: RecipeRepository? = null

        fun getInstance(): RecipeRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: RecipeRepository().also { INSTANCE = it }
            }
        }
    }
}