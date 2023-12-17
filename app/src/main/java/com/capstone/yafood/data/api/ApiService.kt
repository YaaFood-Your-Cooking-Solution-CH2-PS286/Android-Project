package com.capstone.yafood.data.api

import com.capstone.yafood.data.api.requestbody.Login
import com.capstone.yafood.data.api.requestbody.Register
import com.capstone.yafood.data.api.response.ArticleResponse
import com.capstone.yafood.data.api.response.LoginResponse
import com.capstone.yafood.data.api.response.PostArticleResponse
import com.capstone.yafood.data.api.response.RandomRecipeResponse
import com.capstone.yafood.data.api.response.RecomendationResponse
import com.capstone.yafood.data.api.response.RegisterResponse
import com.capstone.yafood.data.api.response.UserDetailResponse
import com.capstone.yafood.data.entity.Article
import com.capstone.yafood.data.entity.Ingredient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @Multipart
    @POST("/prediction")
    fun getRecomendation(
        @Part file: MultipartBody.Part
    ): Call<RecomendationResponse>

    @FormUrlEncoded
    @POST("/searchResep")
    fun getRecomendationFromIngredient(
        @Field("query") query: String
    ): Call<RecomendationResponse>

    @Headers("No-Authentication: true")
    @GET("/randomArtikel")
    fun getDailyArticle(): Call<ArticleResponse>

    @GET("/artikel/byUserId/{id}")
    fun getUserArticle(
        @Path("id") id: Int
    ): Call<ArticleResponse>

    @Headers("No-Authentication: true")
    @GET("/randomResep")
    fun getRandomRecipe(): Call<RandomRecipeResponse>

//    @Headers("No-Authentication: true")
//    @GET("/Resep/{id}")
//    suspend fun getRecipeDetail():

    @Multipart
    @POST("/uploadArtikel")
    fun postArticle(
        @Part image: MultipartBody.Part,
        @Part("titleArtikel") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("ingredients") ingredients: RequestBody,
        @Part("steps") steps: RequestBody,
    ): Call<PostArticleResponse>

    @Headers("No-Authentication: true")
    @POST("/register")
    fun register(
        @Body payload: Register
    ): Call<RegisterResponse>

    @Headers("No-Authentication: true")
    @POST("/login")
    fun login(
        @Body payload: Login
    ): Call<LoginResponse>

    @GET("/userByAccessToken")
    fun getUserDetail(): Call<UserDetailResponse>
}