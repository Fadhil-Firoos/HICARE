package com.capstone.hicare.view.detail

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.capstone.hicare.R
import com.capstone.hicare.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        window.statusBarColor = getColor(R.color.white)
        hideNavigationBar()

        val diseaseName = intent.getStringExtra(EXTRA_DISEASE_NAME)
        val diseaseImage = intent.getIntExtra(EXTRA_DISEASE_IMAGE, -1)
        val diseaseDetail = intent.getIntExtra(EXTRA_DISEASE_DETAIL, -1)

        actionBar(diseaseName)
        selectedDisease(diseaseName, diseaseImage, diseaseDetail)
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

    private fun selectedDisease(diseaseName: String?, diseaseImage: Int, diseaseDetail: Int) {
        Glide.with(this).load(diseaseImage).into(binding.rivPictureReceived)
        binding.tvNameReceived.text = diseaseName
        if (diseaseDetail != 0) {
            binding.apply {
                vsDetailReceived.layoutResource = diseaseDetail
                vsDetailReceived.inflate()
            }
        }
    }

    private fun actionBar(diseaseName: String?) {
        val colorGreenLight = ContextCompat.getColor(this, R.color.green_light)
        supportActionBar?.show()
        supportActionBar?.apply {
            title = diseaseName
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.arrow_back_left)
            setBackgroundDrawable(ColorDrawable(colorGreenLight))
            elevation = 0f
        }
    }

    override fun onStart() {
        super.onStart()
        supportActionBar?.show()
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

    companion object {
        const val EXTRA_DISEASE_NAME = "extra_disease_name"
        const val EXTRA_DISEASE_IMAGE = "extra_disease_image"
        const val EXTRA_DISEASE_DETAIL = "extra_disease_detail"
        const val EXTRA_DISEASE_SUB_NAME = "extra_disease_sub_name"
    }
}
