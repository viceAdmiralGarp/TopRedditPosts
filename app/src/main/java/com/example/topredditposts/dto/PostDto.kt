package com.example.topredditposts.dto

data class PostPageDto(
    val posts: List<PostDto> = listOf(),
    val nextPage: String = ""
)

data class PostDto(
    val authorFullName: String,
    val created: String,
    val numComments: String,
    val url: String
)
