package com.capstone.hicare.view.article

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.hicare.MainModel
import com.capstone.hicare.R
import com.capstone.hicare.adapter.ArticleAdapter
import com.capstone.hicare.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleActivity : AppCompatActivity() {
    private val TAG: String = "ArticleActivity"

    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var progressBar: ProgressBar // Pindahkan inisialisasi progressBar ke sini

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        supportActionBar?.title = "Avengers"

        progressBar = findViewById(R.id.progressBar) // Inisialisasi progressBar di sini

        setupRecyclerView()
        getDataFromApi()
    }

    private fun setupRecyclerView(){
        articleAdapter = ArticleAdapter(arrayListOf(), object : ArticleAdapter.OnAdapterListener {
            override fun onClick(result: MainModel.Result) {
                startActivity(
                    Intent(this@ArticleActivity, DetailArticleActivity::class.java)
                        .putExtra("intent_title", result.title)
                        .putExtra("intent_image", result.image)
                )
            }
        })

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articleAdapter
        }
    }

    private fun getDataFromApi(){
        showLoading(true)
        ApiService.endpoint.data()
            .enqueue(object : Callback<MainModel> {
                override fun onFailure(call: Call<MainModel>, t: Throwable) {
                    printLog( t.toString() )
                    showLoading(false)
                }
                override fun onResponse(
                    call: Call<MainModel>,
                    response: Response<MainModel>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        showResult( response.body()!! )
                    }
                }
            })
    }

    private fun printLog(message: String) {
        Log.d(TAG, message)
    }

    private fun showLoading(loading: Boolean) {
        when(loading) {
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

    private fun showResult(results: MainModel) {
        for (result in results.result) printLog( "title: ${result.title}" )
        articleAdapter.setData(results.result)
    }
}
