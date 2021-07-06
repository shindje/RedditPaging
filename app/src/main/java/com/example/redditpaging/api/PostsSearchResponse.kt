package com.example.redditpaging.api

import com.google.gson.annotations.SerializedName

data class PostsSearchResponse(
        @SerializedName("kind") val kind: String = "Listing",
        @SerializedName("data") val data: PostsSearchResponseData = PostsSearchResponseData(),
)
