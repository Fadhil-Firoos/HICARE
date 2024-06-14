package com.capstone.hicare.retrofit

import com.capstone.hicare.model.ArticleModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint {

    @GET("texts")
    fun data(): Call<ArticleModel>
}
