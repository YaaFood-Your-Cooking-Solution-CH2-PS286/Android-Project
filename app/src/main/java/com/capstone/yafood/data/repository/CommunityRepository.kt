package com.capstone.yafood.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.yafood.data.FakeData
import com.capstone.yafood.data.entity.Community

class CommunityRepository {

    fun getRandomCommunity(): LiveData<List<Community>> {
        val liveData = MutableLiveData<List<Community>>()
        liveData.value = FakeData.dummyCommunities()
        return liveData
    }

    companion object {
        @Volatile
        private var INSTANCE: CommunityRepository? = null

        fun getInstance(): CommunityRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: CommunityRepository().also { INSTANCE = it }
            }
        }
    }
}