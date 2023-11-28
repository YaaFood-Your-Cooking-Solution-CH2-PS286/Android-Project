package com.capstone.yafood.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.yafood.data.entity.User
import com.capstone.yafood.data.repository.UserRepository

class HomeViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User> get() = _userData

    init {
        _userData.value = userRepository.getUserDetail()
    }

}