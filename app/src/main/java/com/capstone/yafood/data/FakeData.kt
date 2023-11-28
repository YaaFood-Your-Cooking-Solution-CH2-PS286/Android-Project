package com.capstone.yafood.data

import com.capstone.yafood.data.entity.User

object FakeData {
    fun dummyNewestArticle() {

    }

    fun dummyUserData(): User {
        return User(
            0,
            "antoniochef_",
            "Antonio De Caprio",
            3022,
            "https://images.unsplash.com/photo-1583394293214-28ded15ee548?q=80&w=2080&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        )
    }
}