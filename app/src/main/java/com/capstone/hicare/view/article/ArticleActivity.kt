package com.capstone.hicare.view.article

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.hicare.R
import com.capstone.hicare.adapter.ArticleAdapter
import com.capstone.hicare.api.ArticleViewModel
import com.capstone.hicare.databinding.ActivityArticleBinding


class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var articleAdapter: ArticleAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initRecyclerView()
        progressBar = findViewById(R.id.progressBar)
        articleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        articleViewModel.fetchArticle()
        articleViewModel.articleList.observe(this, Observer { articleList ->
            articleAdapter.submitList(articleList)
            progressBar.visibility = View.GONE
        })
    }

    private fun initViews() {
        newsRecyclerView = findViewById(R.id.rvArticle)
    }



    private fun initRecyclerView() {
        articleAdapter = ArticleAdapter()
        binding.rvArticle.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(this@ArticleActivity)
        }
    }




}
