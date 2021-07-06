package com.example.redditpaging.model

import com.google.gson.annotations.SerializedName

data class Post (
        @SerializedName("data") val data: PostData = PostData(),
)