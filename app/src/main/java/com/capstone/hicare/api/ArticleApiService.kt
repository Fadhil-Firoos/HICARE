package com.capstone.hicare.api

import retrofit2.Call
import retrofit2.http.GET

interface ArticleApiService {
    @GET("texts")
    fun GetAllArticle(
    ): Call<ArticleResponse>
}
