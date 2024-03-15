package com.example.topredditposts.retrofit

import com.example.topredditposts.model.RedditApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    val postApi: RedditApi = getRetrofit().create(RedditApi::class.java)
    private const val BASE_URL = "https://www.reddit.com/"

    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}