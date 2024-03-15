package com.example.topredditposts.model

import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {
    @GET("top.json")
    suspend fun getPost(
        @Query("after") after: String?,
        @Query("t") time: String = "week"
    ): TopRedditPosts
}