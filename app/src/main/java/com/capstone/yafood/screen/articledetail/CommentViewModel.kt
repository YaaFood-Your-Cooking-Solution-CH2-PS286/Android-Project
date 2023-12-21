package com.capstone.yafood.screen.articledetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.yafood.UiState
import com.capstone.yafood.data.api.ApiConfig
import com.capstone.yafood.data.api.response.PostCommentResponse
import com.capstone.yafood.data.entity.Comment
import com.capstone.yafood.data.repository.CommentRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentViewModel : ViewModel() {
    private val commentRepository = CommentRepository.getInstance()

    private val _uiState = MutableLiveData<UiState<ArrayList<Comment>>>()
    val uiState: LiveData<UiState<ArrayList<Comment>>> get() = _uiState

    private val _commentInput = MutableLiveData<String>()
    val commentInput: LiveData<String> get() = _commentInput

    val isLoadingSubmit = MutableLiveData(false)
    val isSubmitSuccess = MutableLiveData<Boolean>()
    fun setCommentInput(input: String) {
        _commentInput.postValue(input)
    }

    fun getComment(articleId: Int) {
        commentRepository.getArticleComment(_uiState, articleId)
    }

    fun submitComment(articleId: Int) {
        Log.d(TAG, commentInput.value.toString())
        commentInput.value?.let {
            if (it.isEmpty()) return
            isLoadingSubmit.value = true
            ApiConfig.getApiService().postComment(articleId, it)
                .enqueue(object : Callback<PostCommentResponse> {
                    override fun onResponse(
                        call: Call<PostCommentResponse>,
                        response: Response<PostCommentResponse>
                    ) {
                        isLoadingSubmit.value = false
                        if (response.isSuccessful) {
                            Log.d(TAG, "${response.body()}")
                            response.body()?.let { resBody ->
                                pushListComment(resBody.data)
                            }
                            isSubmitSuccess.value = true
                            _commentInput.value = ""
                        } else {
                            Log.e(TAG, "${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<PostCommentResponse>, t: Throwable) {
                        isLoadingSubmit.value = false
                        Log.e(TAG, "${t.message}")
                    }
                })
        }
    }

    private fun pushListComment(comment: Comment) {
        uiState.value?.let { state ->
            if (state is UiState.Success) {
                val currentList = state.data
                currentList.add(0, comment)
                _uiState.value = UiState.Success(currentList)
            }
        }
    }

    companion object {
        const val TAG = "CommentViewModel"
    }
}