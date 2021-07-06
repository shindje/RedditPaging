package com.example.redditpaging.model

import com.google.gson.annotations.SerializedName

data class PostData (
        @SerializedName("title") val title: String = "",
        @SerializedName("name") val name: String = "",
        @SerializedName("total_awards_received") val totalAwardsReceived: Int = 0,
        @SerializedName("num_comments") val numComments: Int = 0,

)