package com.capstone.yafood.screen.settings

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.yafood.R
import com.capstone.yafood.data.api.ApiConfig
import com.capstone.yafood.data.api.requestbody.Profile
import com.capstone.yafood.data.api.response.UpdateProfileResponse
import com.capstone.yafood.data.entity.User
import com.capstone.yafood.data.repository.UserRepository
import com.capstone.yafood.utils.ImageUtils
import com.capstone.yafood.utils.UserState
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsViewModel(
    private val userRepository: UserRepository,
    private val application: Application
) : ViewModel() {
    val loading = MutableLiveData<Boolean>()

    private val _formProfile = MutableLiveData(
        Profile("", "")
    )
    val formProfile: LiveData<Profile> get() = _formProfile
    val imageUri = MutableLiveData<Uri?>(null)
    fun setFormProfile(name: String? = null, email: String? = null) {
        formProfile.value?.let {
            _formProfile.postValue(Profile(name ?: it.name, email ?: it.email))
        }
    }

    private val _userProfile = MutableLiveData<UserState<User>>()
    val userProfile: LiveData<UserState<User>> get() = _userProfile

    init {
        loading.value = false
        userRepository.getUserDetail(_userProfile)
    }

    fun logout() {
        userRepository.clearUserData()
        ApiConfig.getApiService().logout()
    }

    fun updateProfile() {
        loading.value = true
        formProfile.value?.let { profile ->
            ApiConfig.getApiService().updateProfile(profile.name, profile.email)
                .enqueue(object : Callback<UpdateProfileResponse> {
                    override fun onResponse(
                        call: Call<UpdateProfileResponse>,
                        response: Response<UpdateProfileResponse>
                    ) {
                        loading.value = false
                        if (response.isSuccessful) {
                            Log.d(TAG, "Response : ${response.body()?.message}")
                            Toast.makeText(
                                application,
                                application.getString(R.string.success_update_profile),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Log.e(TAG, "Error : ${response.errorBody()?.string()}")
                            Toast.makeText(
                                application,
                                application.getString(R.string.failed_update_profile),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                        loading.value = false
                        Log.e(TAG, "Failure : ${t.message}")
                        Toast.makeText(
                            application,
                            t.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }

    fun updatePhoto() {
        imageUri.value?.let { uri ->
            loading.value = true
            val imageFile = ImageUtils.uriToFile(uri, application)
            val reqFile = MultipartBody.Part.createFormData(
                "file",
                imageFile.name,
                imageFile.asRequestBody("image/jpg".toMediaType())
            )
            ApiConfig.getApiService().changePhotoProfile(reqFile)
                .enqueue(object : Callback<UpdateProfileResponse> {
                    override fun onResponse(
                        call: Call<UpdateProfileResponse>,
                        response: Response<UpdateProfileResponse>
                    ) {
                        loading.value = false

                        if (response.isSuccessful) {
                            Toast.makeText(
                                application,
                                application.getString(R.string.success_update_profile),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Log.e(TAG, "Error : ${response.errorBody()?.string()}")
                            Toast.makeText(
                                application,
                                "${response.errorBody()?.string()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                        Log.e(TAG, "Failure : ${t.message}")
                        loading.value = false
                        Toast.makeText(application, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    companion object {
        const val TAG = "SettingsViewModel"
    }
}