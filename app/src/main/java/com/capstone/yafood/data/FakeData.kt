package com.capstone.yafood.data

import com.capstone.yafood.data.entity.Article
import com.capstone.yafood.data.entity.User

object FakeData {

    fun dummyListUserArtciles(): List<Article> {
        val list = ArrayList<Article>()
        for (i in 1..4) {
            list.add(dummyArticle((i)))
        }

        return list
    }

    private fun dummyUserArticle(id: Int = 0): Article = Article(
        id,
        "Nasi Tumis Megalodon",
        "https://images.unsplash.com/photo-1603133872878-684f208fb84b?q=80&w=1925&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        dummyUserData()
    )

    fun dummyListArticles(): List<Article> {
        val list = ArrayList<Article>()
        for (i in 1..10) {
            list.add(dummyArticle(i))
        }
        return list
    }

    private fun dummyArticle(id: Int = 0): Article = Article(
        id,
        "Nasi Tumis Megalodon",
        "https://images.unsplash.com/photo-1603133872878-684f208fb84b?q=80&w=1925&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        dummyUserData(
            username = "bagusx123",
            photoUrl = "https://images.unsplash.com/photo-1552058544-f2b08422138a?q=80&w=1998&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        )
    )

    fun dummyUserData(username: String? = null, photoUrl: String? = null): User {
        return User(
            0,
            username ?: "antoniochef_",
            "Antonio De Caprio",
            3022,
            photoUrl
                ?: "https://images.unsplash.com/photo-1583394293214-28ded15ee548?q=80&w=2080&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        )
    }
}