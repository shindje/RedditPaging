package com.example.redditpaging.api

import com.example.redditpaging.model.Post
import com.google.gson.annotations.SerializedName

data class PostsSearchResponseData (
        @SerializedName("after") val afterPost: String = "",
        @SerializedName("children") val children: List<Post> = emptyList(),
)