package com.octopi.recyclerviewdiffutil.network

import com.octopi.recyclerviewdiffutil.model.PostResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("/posts")
    fun getPosts() : Call<PostResponse>

}