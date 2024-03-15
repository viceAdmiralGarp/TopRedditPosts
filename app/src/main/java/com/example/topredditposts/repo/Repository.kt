package com.example.topredditposts.repo

import com.example.topredditposts.model.RedditApi
import com.example.topredditposts.dto.PostPageDto
import com.example.topredditposts.model.toDto

class Repository(private val redditApi: RedditApi) {
    suspend fun getPosts(nextPage: String?): PostPageDto {
        return redditApi.getPost(nextPage).toDto()
    }
}