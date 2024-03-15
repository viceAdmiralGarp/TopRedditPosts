package com.example.topredditposts.dto

data class PostPageDto(
    val posts: List<PostDto> = listOf(),
    val nextPage: String = ""
)

data class PostDto(
    val authorFullName: String,
    val created: Long,
    val numComments: Long,
    val url: String
)
