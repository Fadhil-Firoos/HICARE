package com.capstone.hicare

import java.util.Date

data class MainModel(
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
        val timestamp : String
    )
}