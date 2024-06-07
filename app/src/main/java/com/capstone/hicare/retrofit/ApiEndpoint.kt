package com.capstone.hicare.retrofit

import com.capstone.hicare.MainModel
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint {

    @GET("texts")
    fun data(): Call<MainModel>
}
