package com.example.topredditposts.model

class PostApiImpl(private val postApi: RedditApi) : RedditApi {
    override suspend fun getPost(after: String?, time: String): TopRedditPosts {
        return postApi.getPost(after)
    }
}