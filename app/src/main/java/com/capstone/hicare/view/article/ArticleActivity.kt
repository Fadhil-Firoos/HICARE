package com.capstone.hicare.view.article

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.hicare.R
import com.capstone.hicare.adapter.ArticleAdapter
import com.capstone.hicare.model.ArticleModel
import com.capstone.hicare.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleActivity : AppCompatActivity(), ArticleAdapter.OnAdapterListener {
    private val TAG: String = "ArticleActivity"
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        enableEdgeToEdge()
        window.statusBarColor = getColor(R.color.white)
        hideNavigationBar()

        supportActionBar?.show()
        supportActionBar?.apply {
            title = ""
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.arrow_back_left)
            setBackgroundDrawable(ColorDrawable(Color.WHITE))
            elevation = 0f
        }
        progressBar = findViewById(R.id.progressBar)

        setupRecyclerView()
        getDataFromApi()
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter(ArrayList(), this)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articleAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val settingItem = menu?.findItem(R.id.btn_setting)
        settingItem?.isVisible = false

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getDataFromApi() {
        showLoading(true)
        ApiService.endpoint.data().enqueue(object : Callback<ArticleModel> {
            override fun onFailure(call: Call<ArticleModel>, t: Throwable) {
                printLog(t.toString())
                showLoading(false)
                showError("Failed to load data")
            }

            override fun onResponse(call: Call<ArticleModel>, response: Response<ArticleModel>) {
                showLoading(false)
                if (response.isSuccessful) {
                    response.body()?.let {
                        showResult(it)
                    } ?: run {
                        printLog("Response body is null")
                        showError("Failed to load data")
                    }
                } else {
                    printLog("Response not successful: ${response.errorBody()?.string()}")
                    showError("Failed to load data")
                }
            }
        })
    }

    private fun printLog(message: String) {
        Log.d(TAG, message)
    }

    private fun showLoading(loading: Boolean) {
        progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun showResult(articleModel: ArticleModel) {
        articleAdapter.setData(articleModel.data.texts)
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(result: ArticleModel.Result) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(result.url_artikel)
        startActivity(intent)
    }

    private fun hideNavigationBar() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideNavigationBar()
        }
    }
}
