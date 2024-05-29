package com.capstone.hicare.view.analyze

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.capstone.hicare.databinding.ActivityAnalyzeBinding
import com.capstone.hicare.utils.uriToFile
import com.capstone.hicare.view.main.MainActivity
import com.yalantis.ucrop.UCrop

class AnalyzeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnalyzeBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUriString = intent.getStringExtra("image_uri")
        imageUriString?.let {
            currentImageUri = Uri.parse(it)
            binding.imageView.setImageURI(currentImageUri)
        }

        binding.buttonGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }



        binding.goHome.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }


    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            currentImageUri = it
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
                currentImageUri = it
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
}
