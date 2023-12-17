package com.capstone.yafood.screen.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.yafood.R
import com.capstone.yafood.UiState
import com.capstone.yafood.data.api.ApiConfig
import com.capstone.yafood.data.api.requestbody.Login
import com.capstone.yafood.data.api.requestbody.Register
import com.capstone.yafood.data.api.response.LoginResponse
import com.capstone.yafood.data.api.response.RegisterResponse
import com.capstone.yafood.data.local.UserPreferences
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel(
    private val application: Application
) : ViewModel() {
    private val _uiState = MutableLiveData<UiState<String>>()
    val uiState: LiveData<UiState<String>> get() = _uiState

    private val _formRegister = MutableLiveData(
        Register(
            name = "",
            email = "",
            password = "",
            confPassword = "",
        )
    )
    val formRegister: LiveData<Register> get() = _formRegister

    private val _formLogin = MutableLiveData(
        Login(
            email = "",
            password = ""
        )
    )
    private val formLogin: LiveData<Login> get() = _formLogin

    private val userPreferences = UserPreferences.getInstance(application)

    fun submitRegister() {
        formRegister.value?.let {
            val (name, email, password, confPassword) = it
            if (password != confPassword) {
                _uiState.value =
                    UiState.Error(
                        errorBuilder(
                            "confPassword",
                            application.getString(R.string.password_and_confPassword_not_same)
                        )
                    )
                return
            }
            _uiState.value = UiState.Loading

            val client = ApiConfig.getApiService().register(it)
            client.enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    val body = response.body()

                    if (response.isSuccessful) {
//                        Log.e(TAG, "Error Response: ${body?.errors}")

                        _uiState.value =
                            UiState.Success(application.getString(R.string.success_register))
                    } else {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            val jsonArr = jObjError.getJSONArray("errors")
                            val msg = (0 until jsonArr.length())
                                .map { jsonArr.getJSONObject(it).optString("msg", "") }
                            val path = (0 until jsonArr.length())
                                .map { jsonArr.getJSONObject(it).optString("path", "") }

                            Log.e(TAG, "Error Message: $msg")
                            var errorMessageBuilder = ""
                            path.forEachIndexed { i, item ->
                                errorMessageBuilder += "$item;${msg[i]}${if (i + 1 < path.size) "|" else ""}"
                            }
                            Log.d(TAG, errorMessageBuilder)
                            _uiState.value =
                                UiState.Error(errorMessageBuilder)

                        } catch (e: Exception) {
                            Log.e(TAG, "Catch Message: $e")
                            _uiState.value =
                                UiState.Error(
                                    errorBuilder(
                                        "none",
                                        application.getString(R.string.failed_register)
                                    )
                                )
                        }
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Log.e(TAG, "Error : $t")
                    _uiState.value = UiState.Error(
                        errorBuilder(
                            "none",
                            application.getString(R.string.failed_register)
                        )
                    )
                }
            })
        }
    }

    fun setFormRegister(
        inputName: String? = null,
        inputEmail: String? = null,
        inputPassword: String? = null,
        inputConfPassword: String? = null
    ) {
        formRegister.value?.let {
            val (name, email, password, confPassword) = it
            _formRegister.value = Register(
                name = inputName ?: name,
                email = inputEmail ?: email,
                password = inputPassword ?: password,
                confPassword = inputConfPassword ?: confPassword,
            )
        }
    }

    fun submitLogin() {
        formLogin.value?.let {
            _uiState.value = UiState.Loading

            val client = ApiConfig.getApiService().login(it)
            client.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val resBody = response.body()
                        Log.i(TAG, "Success : $it")
                        resBody?.let {
                            viewModelScope.launch {
                                ApiConfig.setToken(it.accessToken)
                                userPreferences.saveToken(it.accessToken)
                                userPreferences.saveUserId(it.user.id)
                                _uiState.value =
                                    UiState.Success(
                                        application.getString(
                                            R.string.success_login,
                                            it.user.name
                                        )
                                    )
                            }
                        }
                    } else {
                        Log.e(TAG, "Error Login: ${response.errorBody()?.string()}")
                        _uiState.value = UiState.Error(application.getString(R.string.failed_login))
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e(TAG, "Failure : ${t.message.toString()}")
                    _uiState.value = UiState.Error(application.getString(R.string.failed_login))
                }
            })
        }
    }

    fun setFormLogin(
        inputEmail: String? = null,
        inputPassword: String? = null
    ) {
        formLogin.value?.let {
            val (email, password) = it
            _formLogin.value = Login(
                email = inputEmail ?: email,
                password = inputPassword ?: password,
            )
        }
    }

    companion object {
        const val TAG = "AuthAction"

        private fun errorBuilder(typed: String, message: String): String = "$typed;$message"
    }
}