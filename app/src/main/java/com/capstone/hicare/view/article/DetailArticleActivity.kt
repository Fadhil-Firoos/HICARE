package com.capstone.hicare.view.article

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.capstone.hicare.R

class DetailArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)
        val imagePlaceholder = findViewById<ImageView>(R.id.imageplaceholder)

        val intentTitle = intent.getStringExtra("intent_title")
        val intentImage = intent.getStringExtra("intent_image")
        supportActionBar!!.title = intentTitle
        Glide.with(this)
            .load(intentImage )
            .placeholder(R.drawable.sample)
            .error(R.drawable.sample)
            .into(imagePlaceholder)
    }
}
