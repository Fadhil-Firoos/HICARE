package com.capstone.hicare.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.capstone.hicare.R
import com.capstone.hicare.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val diseaseName = intent.getStringExtra(EXTRA_DISEASE_NAME)
        val diseaseImage = intent.getIntExtra(EXTRA_DISEASE_IMAGE, -1)
        val diseaseDetail = intent.getIntExtra(EXTRA_DISEASE_DETAIL, -1)

        actionBar(diseaseName)
        selectedDisease(diseaseName, diseaseImage, diseaseDetail)
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
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = diseaseName
        }
    }

    override fun onStart() {
        super.onStart()
        supportActionBar?.show()
    }

    companion object {
        const val EXTRA_DISEASE_NAME = "extra_disease_name"
        const val EXTRA_DISEASE_IMAGE = "extra_disease_image"
        const val EXTRA_DISEASE_DETAIL = "extra_disease_detail"
        const val EXTRA_DISEASE_SUB_NAME = "extra_disease_sub_name"
    }
}
