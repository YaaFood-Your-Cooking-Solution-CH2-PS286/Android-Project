package com.capstone.yafood.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        private const val BASE_URL = ""

        private var token = ""
        fun setToken(newtoken:String){
            token = newtoken
        }

        private fun getHeaderInterceptor(): Interceptor {
            return Interceptor { chain ->
                var request = chain.request()

                if (request.header("No-Authentication") == null) {
                    if (token.isNotEmpty()) {
                        request = request.newBuilder()
                            .addHeader("Authorization", "Bearer $token")
                            .build()
                    }
                }
                chain.proceed(request)
            }
        }

        fun getApiService(): ApiService {

            val client = OkHttpClient.Builder()
                .addInterceptor(getHeaderInterceptor())
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}