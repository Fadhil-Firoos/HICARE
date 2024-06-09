package com.capstone.hicare.model


data class ArticleModel(
    val status: String,
    val data: Data
) {
    data class Data(
        val texts: List<Result>
    )

    data class Result(
        val name: String,
        val url_image: String,
        val url_artikel: String,
        val deskripsi: String,
        val timestamp : String
    )
}