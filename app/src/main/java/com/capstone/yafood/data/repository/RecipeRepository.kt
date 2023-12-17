package com.capstone.yafood.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.yafood.data.FakeData
import com.capstone.yafood.data.api.ApiConfig
import com.capstone.yafood.data.api.response.RandomRecipeResponse
import com.capstone.yafood.data.api.response.RecomendationResult
import com.capstone.yafood.data.entity.Recipe
import com.capstone.yafood.data.entity.RecipeIdea
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeRepository {
    fun getRecipeIdea(): LiveData<RecipeIdea> {
        val liveData = MutableLiveData<RecipeIdea>()
        val client = ApiConfig.getApiService().getRandomRecipe()
        client.enqueue(object : Callback<RandomRecipeResponse> {
            override fun onResponse(
                call: Call<RandomRecipeResponse>,
                response: Response<RandomRecipeResponse>
            ) {
                if (response.isSuccessful) {
                    Log.i(TAG, "Recipe Idea : ${response.body()}")
                    response.body()?.let {
                        liveData.value = it.data
                    }
                } else {
                    Log.e(TAG, "Recipe Idea Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<RandomRecipeResponse>, t: Throwable) {
                Log.e(TAG, "Recipe Idea Failure: ${t.message.toString()}")
            }
        })
        return liveData
    }

    fun getRecipeDetail(recipeId: Int): LiveData<Recipe> {
        val liveData = MutableLiveData<Recipe>()
        liveData.value = FakeData.dummyRecipe(recipeId)
        return liveData
    }

    companion object {
        const val TAG = "RecipeRepo"

        @Volatile
        private var INSTANCE: RecipeRepository? = null

        fun getInstance(): RecipeRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: RecipeRepository().also { INSTANCE = it }
            }
        }
    }
}