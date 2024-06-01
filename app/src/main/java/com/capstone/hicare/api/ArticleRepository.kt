package com.capstone.hicare.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepository {
fun getArticle(
    onSuccess: (List<ArticleItem>) -> Unit,
    onFailure: (String) -> Unit
) {
    ApiClient.articleApiService.GetAllArticle()
        .enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    val newsList = articles.mapNotNull { article ->
                        if (!article.name.isNullOrEmpty() && !article.article.isNullOrEmpty() && !article.url.isNullOrEmpty()) {
                            ArticleItem(article.name, article.article)
                        } else {
                            null
                        }
                    }
                    onSuccess(newsList)
                } else {
                    onFailure("Gagal mengunduh berita")
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                onFailure(t.message ?: "Error tidak diketahui")
            }
        })
}
}