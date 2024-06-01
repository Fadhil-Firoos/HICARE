package com.capstone.hicare.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArticleViewModel: ViewModel(){
    private val articleRepository = ArticleRepository()
    private val _articleList = MutableLiveData<List<ArticleItem>>()
    val articleList: LiveData<List<ArticleItem>> = _articleList

    fun fetchArticle() {
        articleRepository.getArticle(
            onSuccess = { articleList ->
                _articleList.postValue(articleList)
            },
            onFailure = {
            }
        )
    }
}