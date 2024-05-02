package com.nabawi.gohub.data

import com.nabawi.gohub.model.SearchResponse
import com.nabawi.gohub.model.UserEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    fun searchUsers (
        @Query("q")
        query: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUser (
        @Path("username")
        username: String
    ): Call<UserEntity>

    @GET("users/{username}/followers")
    fun getUserFollowers (
        @Path("username")
        username: String
    ): Call<List<UserEntity>>

    @GET("users/{username}/following")
    fun getUserFollowing (
        @Path("username")
        username: String
    ): Call<List<UserEntity>>
}