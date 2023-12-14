package com.dicoding.submissionakhir.response

import com.google.gson.annotations.SerializedName

data class GithubResponse(

    @field:SerializedName("items")
    val items: List<ItemsItem>? = null,
)

data class ItemsItem(

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,
)