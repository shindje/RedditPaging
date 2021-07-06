package com.example.redditpaging.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {
    @GET("hot.json")
    suspend fun searchPosts(
        @Query("after") after: String,
        @Query("before") before: String,
    ): PostsSearchResponse

    companion object {
        private const val BASE_URL = "https://www.reddit.com/r/aww/"

        fun create(): RedditService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RedditService::class.java)
        }
    }
}
