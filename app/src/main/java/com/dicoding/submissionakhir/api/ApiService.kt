package com.dicoding.submissionakhir.api

import com.dicoding.submissionakhir.response.DetailUserResponse
import com.dicoding.submissionakhir.response.GithubResponse
import com.dicoding.submissionakhir.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("search/users")
    fun getListUsers(
        @Query("q") id: String
    ): Call<GithubResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>
}