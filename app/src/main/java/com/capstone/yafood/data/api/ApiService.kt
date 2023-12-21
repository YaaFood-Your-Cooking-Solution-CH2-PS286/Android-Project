package com.capstone.yafood.data.api

import com.capstone.yafood.adapter.LogoutResponse
import com.capstone.yafood.data.api.requestbody.Login
import com.capstone.yafood.data.api.requestbody.Register
import com.capstone.yafood.data.api.response.ArticleDetailResponse
import com.capstone.yafood.data.api.response.ArticleResponse
import com.capstone.yafood.data.api.response.GetCommentResponse
import com.capstone.yafood.data.api.response.LoginResponse
import com.capstone.yafood.data.api.response.PostArticleResponse
import com.capstone.yafood.data.api.response.PostCommentResponse
import com.capstone.yafood.data.api.response.RandomRecipeResponse
import com.capstone.yafood.data.api.response.RecomendationResponse
import com.capstone.yafood.data.api.response.RegisterResponse
import com.capstone.yafood.data.api.response.UpdateProfileResponse
import com.capstone.yafood.data.api.response.UserDetailResponse
import com.capstone.yafood.data.entity.Article
import com.capstone.yafood.data.entity.Ingredient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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
    @GET("/allArtikel")
    fun getAllArticle(): Call<ArticleResponse>

    @Headers("No-Authentication: true")
    @GET("/randomArtikel")
    fun getDailyArticle(
        @Query("limit") limit: Int? = null
    ): Call<ArticleResponse>

    @GET("/artikel/byUserId/{id}")
    fun getUserArticle(
        @Path("id") id: Int
    ): Call<ArticleResponse>

    @FormUrlEncoded
    @POST("/searchArticle")
    fun findArticles(
        @Field("query") query: String
    ): Call<ArticleResponse>

    @GET("/artikel/{id}")
    fun getArticleDetail(
        @Path("id") id: Int
    ): Call<ArticleDetailResponse>

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

    @DELETE("deleteArticles/{articleId}")
    fun deleteArticle(@Path("articleId") articleId: Int): Call<ArticleResponse>

    @Multipart
    @POST("/uploadFotoProfile")
    fun changePhotoProfile(
        @Part image: MultipartBody.Part
    ): Call<UpdateProfileResponse>

    @FormUrlEncoded
    @POST("/updateprofile")
    fun updateProfile(
        @Field("name") name: String,
        @Field("email") email: String,
    ): Call<UpdateProfileResponse>

    @DELETE("logout")
    fun logout(): Call<LogoutResponse>

    @FormUrlEncoded
    @POST("/artikel/comment/{articleId}")
    fun postComment(
        @Path("articleId")
        articleId: Int,
        @Field("contentComment")
        comment: String
    ): Call<PostCommentResponse>

    @GET("/articles/{articleId}/comments")
    fun getCommentByArticle(
        @Path("articleId")
        articleId: Int
    ): Call<GetCommentResponse>
}