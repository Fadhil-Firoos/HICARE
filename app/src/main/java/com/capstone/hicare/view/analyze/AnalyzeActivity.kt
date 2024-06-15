package com.capstone.hicare.view.analyze

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.capstone.hicare.R
import com.capstone.hicare.databinding.ActivityAnalyzeBinding
import com.capstone.hicare.model.factory.Classifier
import com.capstone.hicare.utils.uriToFile
import com.capstone.hicare.view.result.ResultActivity
import com.yalantis.ucrop.UCrop
import java.io.ByteArrayOutputStream
import java.io.IOException

class AnalyzeActivity : AppCompatActivity() {
    private lateinit var mClassifier: Classifier
    private lateinit var mBitmap: Bitmap
    private lateinit var binding: ActivityAnalyzeBinding
    private var currentImageUri: Uri? = null
    private val mInputSize = 256
    private val mModelPath = "lettuce.tflite"
    private val mLabelPath = "label.txt"
    private val mSamplePath = R.drawable.sample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        window.statusBarColor = getColor(R.color.white)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        hideNavigationBar()

        supportActionBar?.apply {
            title = ""
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.arrow_back_left)
            setBackgroundDrawable(ColorDrawable(Color.parseColor("#B9F5D3")))
            elevation = 0f
        }
        mClassifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)

        binding.buttonGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }
        var imageUri: Uri?
        val imageUriString = intent.getStringExtra("image_uri")

        if (imageUriString != null) {
            imageUri = Uri.parse(imageUriString)
            currentImageUri = imageUri // Assigning currentImageUri
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                mBitmap = scaleImage(mBitmap)
                binding.imageView.setImageBitmap(mBitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            mBitmap = BitmapFactory.decodeResource(resources, mSamplePath)
            mBitmap = Bitmap.createScaledBitmap(mBitmap, mInputSize, mInputSize, true)
            binding.imageView.setImageBitmap(mBitmap)
        }

        binding.buttonAnalyze.setOnClickListener {
            val sampleBitmap = BitmapFactory.decodeResource(resources, mSamplePath)
            val scaledSampleBitmap = Bitmap.createScaledBitmap(sampleBitmap, mInputSize, mInputSize, true)

            if (mBitmap.sameAs(scaledSampleBitmap)) {
                Toast.makeText(this, "Tolong Masukan Foto Terlebih Dahulu", Toast.LENGTH_SHORT).show()
            } else {
                val results = mClassifier.recognizeImage(mBitmap).firstOrNull()
                val intent = Intent(this, ResultActivity::class.java)

                intent.putExtra(
                    "penyakit",
                    results?.title + "\n" + "\nAkurasi: " + results?.confidence + "%"
                )
                intent.putExtra("nama", results?.title)

                val stream = ByteArrayOutputStream()
                mBitmap.compress(
                    Bitmap.CompressFormat.PNG,
                    100,
                    stream
                ) // Set compression to 100 to avoid loss in quality
                val byteArray = stream.toByteArray()

                intent.putExtra("image", byteArray)

                startActivity(intent)
            }
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
                try {
                    mBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, it)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                binding.imageView.post {
                    mBitmap = scaleImage(mBitmap)
                    binding.imageView.setImageBitmap(mBitmap)
                }
            }
        } else if (result.resultCode == UCrop.RESULT_ERROR) {
            val error = UCrop.getError(result.data!!)
            Log.e("AnalyzeActivity", error.toString())
        }
    }

    private fun cropImage(uri: Uri) {
        val destinationUri = Uri.fromFile(uriToFile(uri, this))
        val options = UCrop.Options().apply {
            setFreeStyleCropEnabled(true)
        }
        val intent = UCrop.of(uri, destinationUri)
            .withOptions(options)
            .getIntent(this)

        cropImageLauncher.launch(intent)
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

    private fun scaleImage(bitmap: Bitmap?): Bitmap {
        val originalWidth = bitmap!!.width
        val originalHeight = bitmap.height
        val aspectRatio = originalWidth.toFloat() / originalHeight.toFloat()

        val targetWidth: Int
        val targetHeight: Int
        if (aspectRatio > 1) {
            targetWidth = mInputSize
            targetHeight = (mInputSize / aspectRatio).toInt()
        } else {
            targetWidth = (mInputSize * aspectRatio).toInt()
            targetHeight = mInputSize
        }

        return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)
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
