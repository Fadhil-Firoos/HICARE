package com.capstone.hicare.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val retrofitInstance = Retrofit.Builder()
        .baseUrl(ApiConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val articleApiService: ArticleApiService = retrofitInstance.create(ArticleApiService::class.java)
}