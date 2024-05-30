package com.capstone.hicare.view.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import android.view.MenuItem
import com.bumptech.glide.Glide
import com.capstone.hicare.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar()
        selectedDisease()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onNavigateUp(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun onNavigateUp(itemId: Int) {
        if (itemId == android.R.id.home) onBackPressed()
    }

    private fun selectedDisease() {
        val diseaseName = intent.getStringExtra(EXTRA_DISEASE_NAME).toString()
        val diseaseImage = intent.getIntExtra(EXTRA_DISEASE_IMAGE, 0)
        val diseaseDetail = intent.getIntExtra(EXTRA_DISEASE_DETAIL, 0)

        Glide.with(this).load(diseaseImage).into(binding.rivPictureReceived)
        binding.tvNameReceived.text = diseaseName
        if (diseaseDetail != 0) {
            binding.apply {
                vsDetailReceived.layoutResource = diseaseDetail
                vsDetailReceived.inflate()
            }
        }
    }

    private fun actionBar() {
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = intent.getStringExtra(EXTRA_DISEASE_NAME).toString()
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
