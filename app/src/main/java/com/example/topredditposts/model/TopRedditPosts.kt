package com.example.topredditposts.model

import com.example.topredditposts.dto.PostDto
import com.example.topredditposts.dto.PostPageDto
import com.google.gson.annotations.SerializedName

data class TopRedditPosts(
    @SerializedName("data")
    val data: TopRedditPostsData
) {
    data class TopRedditPostsData(
        @SerializedName("after")
        val nextPage: String,
        @SerializedName("children")
        val children: List<Posts>
    ) {
        data class Posts(
            @SerializedName("data")
            val data: Post
        ) {
            data class Post(
                @SerializedName("author")
                val authorFullName: String,
                @SerializedName("created")
                val created: Long,
                @SerializedName("num_comments")
                val numComments: Long,
                @SerializedName("url")
                val url: String
            )
        }
    }
}

fun TopRedditPosts.toDto(): PostPageDto = PostPageDto(
    posts = data.children.map {
        PostDto(
            authorFullName = "Author ${it.data.authorFullName}",
            created = "${it.data.created / 1000 / 60 / 60} hour(s) ago",
            numComments = "Comments ${it.data.numComments}",
            url = it.data.url
        )
    }.toList(),
    nextPage = data.nextPage
)

