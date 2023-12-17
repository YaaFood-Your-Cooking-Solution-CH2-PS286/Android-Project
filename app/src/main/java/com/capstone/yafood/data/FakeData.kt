package com.capstone.yafood.data

import com.capstone.yafood.data.api.response.RecomendationResult
import com.capstone.yafood.data.entity.Article
import com.capstone.yafood.data.entity.Community
import com.capstone.yafood.data.entity.Ingredient
import com.capstone.yafood.data.entity.Recipe
import com.capstone.yafood.data.entity.RecipeIdea
import com.capstone.yafood.data.entity.User

object FakeData {

    fun dummyListUserArtciles(): List<Article> {
        val list = ArrayList<Article>()
        for (i in 1..4) {
            list.add(dummyArticle((i)))
        }

        return list
    }

    fun dummyRecipeIdea(): RecipeIdea {
        val list = ArrayList<Recipe>()
        for (i in 1..2) {
            list.add(dummyRecipe(i))
        }
        return RecipeIdea(
            Ingredient(
                "Telur",
                "https://images.unsplash.com/photo-1582722872445-44dc5f7e3c8f?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            ),
            list,
        )
    }

    fun dummyRecipe(id: Int): Recipe {
        return Recipe(
            id,
            "Telur Setengah Masak",
            listOf("Telur", "Garam"),
            listOf("Rebus saja 7 menit", "Kupas Telur"),
            "https://images.unsplash.com/photo-1610328466269-1f36faad83c1?q=80&w=2080&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        )
    }

    private fun dummyUserArticle(id: Int = 0): Article = Article(
        id,
        "Nasi Tumis Megalodon",
        "https://images.unsplash.com/photo-1603133872878-684f208fb84b?q=80&w=1925&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        dummyUserData(),
        0,
        0,
        "",
        listOf("Telur", "Garam"),
        listOf("Rebus saja 7 menit", "Kupas Telur"),
    )

    fun dummyListArticles(): List<Article> {
        val list = ArrayList<Article>()
        for (i in 1..10) {
            list.add(dummyArticle(i))
        }
        return list
    }

    fun dummyArticle(id: Int = 0): Article = Article(
        id,
        "Nasi Tumis Megalodon",
        "https://images.unsplash.com/photo-1603133872878-684f208fb84b?q=80&w=1925&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        dummyUserData(
            username = "bagusx123",
            photoUrl = "https://images.unsplash.com/photo-1552058544-f2b08422138a?q=80&w=1998&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        ),
        0, 0,
        "Nasi goreng yang sangat amat teramat mantap sekali",
        listOf("Telur", "Garam"),
        listOf("Rebus saja 7 menit", "Kupas Telur"),
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

    fun dummyRecomendationResult(): RecomendationResult {
        val recipes = ArrayList<Recipe>()
        for (i in 1..3) {
            recipes.add(
                Recipe(
                    i,
                    "Nasi Tumis Megalodon",
                    listOf("Telur, nasi, udang, daging ayam, bawang merah, bawang putih"),
                    listOf("Tumis bawang hingga wangi"),
                    "https://images.unsplash.com/photo-1603133872878-684f208fb84b?q=80&w=1925&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                )
            )
        }
        return RecomendationResult(
            listOf(
                "Telur",
                "Nasi"
            ),
            recipes
        )
    }

    fun dummyCommunities(): List<Community> {
        val list = ArrayList<Community>()
        list.add(
            Community(
                name = "Classic Food",
                photoUrl = "https://images.unsplash.com/photo-1603133872878-684f208fb84b?q=80&w=1925&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                users = listOf(
                    dummyUserData(photoUrl = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
                    dummyUserData(photoUrl = "https://images.unsplash.com/photo-1599566150163-29194dcaad36?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
                )
            )
        )
        list.add(
            Community(
                name = "Asian Food",
                photoUrl = "https://images.unsplash.com/photo-1569718212165-3a8278d5f624?q=80&w=2080&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                users = listOf(
                    dummyUserData(photoUrl = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
                    dummyUserData(photoUrl = "https://images.unsplash.com/photo-1599566150163-29194dcaad36?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
                )
            )
        )
        return list
    }
}