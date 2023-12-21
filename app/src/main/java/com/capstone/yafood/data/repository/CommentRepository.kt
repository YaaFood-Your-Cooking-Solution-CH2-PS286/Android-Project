package com.capstone.yafood.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.capstone.yafood.UiState
import com.capstone.yafood.data.api.ApiConfig
import com.capstone.yafood.data.api.response.GetCommentResponse
import com.capstone.yafood.data.entity.Comment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentRepository {
    fun getArticleComment(state: MutableLiveData<UiState<ArrayList<Comment>>>, articleId: Int) {
        state.value = UiState.Loading
        ApiConfig.getApiService().getCommentByArticle(articleId)
            .enqueue(object : Callback<GetCommentResponse> {
                override fun onResponse(
                    call: Call<GetCommentResponse>,
                    response: Response<GetCommentResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val list = ArrayList<Comment>()
                            it.data.forEach { data -> list.add(data) }
                            state.value = UiState.Success(list)
                        }
                    } else {
                        if (response.code() == 404) {
                            state.value = UiState.Success(ArrayList())
                        } else {
                            state.value = UiState.Error("Error : ${response.errorBody()?.string()}")
                            Log.e(
                                ArticleRepository.TAG,
                                "Error : ${response.errorBody()?.string()}"
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<GetCommentResponse>, t: Throwable) {
                    state.value = UiState.Error("Tidak dapat mengambil komentar")
                    Log.e(ArticleRepository.TAG, "Failure : ${t.message}")
                }
            })
    }

    companion object {
        @Volatile
        private var INSTANCE: CommentRepository? = null

        fun getInstance(): CommentRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: CommentRepository().also { INSTANCE = it }
            }
        }

        const val TAG = "CommentRepo"
    }
}