package com.capstone.hicare.view.analyze

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.capstone.hicare.R
import com.capstone.hicare.databinding.ActivityAnalyzeBinding
import com.capstone.hicare.utils.uriToFile
import com.capstone.hicare.view.result.ResultActivity
import com.capstone.hicare.view.setting.SettingActivity
import com.yalantis.ucrop.UCrop

class AnalyzeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnalyzeBinding
    private var currentImageUri: Uri? = null
    private var croppedImageUri: Uri? = null
    private var isImageSelected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.white)

        supportActionBar?.apply {
            title = ""
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.arrow_back_left)
            setBackgroundDrawable(ColorDrawable(Color.WHITE))
            elevation = 0f
        }

        val imageUriString = intent.getStringExtra("image_uri")
        imageUriString?.let {
            currentImageUri = Uri.parse(it)
            binding.imageView.setImageURI(currentImageUri)
            isImageSelected = true
        }

        binding.buttonGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        binding.buttonAnalyze.setOnClickListener {
            handleAnalyzeButtonClick()
        }
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            currentImageUri = it
            isImageSelected = true
            cropImage(it)
        }
    }

    private val cropImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val resultUri = UCrop.getOutput(result.data!!)
            resultUri?.let {
                binding.imageView.setImageURI(it)
                croppedImageUri = it
            }
        } else if (result.resultCode == UCrop.RESULT_ERROR) {
            val error = UCrop.getError(result.data!!)
            Log.e("AnalyzeActivity", error.toString())
        }
    }

    private fun cropImage(uri: Uri) {
        val destinationUri = Uri.fromFile(uriToFile(uri, this))
        val options = UCrop.Options().apply {
            setCompressionQuality(90)
            setFreeStyleCropEnabled(true)
        }
        val intent = UCrop.of(uri, destinationUri)
            .withOptions(options)
            .getIntent(this)

        cropImageLauncher.launch(intent)
    }

    private fun handleAnalyzeButtonClick() {
        if (isImageSelected) {
            analyzeImage()
        } else {
            showToast(getString(R.string.image_classifier_failed))
        }
    }

    private fun analyzeImage() {
        val intent = Intent(this, ResultActivity::class.java)
        croppedImageUri?.let { uri ->
            intent.putExtra(ResultActivity.IMAGE_URI, uri.toString())
            startActivity(intent)
        } ?: showToast(getString(R.string.image_classifier_failed))
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.btn_setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val TAG = "ImagePicker"
        const val IMAGE_URI = "image_uri"
    }
}
