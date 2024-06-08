package com.capstone.hicare.view.analyze

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.capstone.hicare.R
import com.capstone.hicare.databinding.ActivityAnalyzeBinding
import com.capstone.hicare.model.factory.Classifier
import com.capstone.hicare.utils.uriToFile
import com.capstone.hicare.view.result.ResultActivity
import com.capstone.hicare.view.setting.SettingActivity
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
    private val mSamplePath = "not image.jpg"
    private val mCameraRequestCode = 0
    private val mGalleryRequestCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.white)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        supportActionBar?.apply {
            title = ""
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.arrow_back_left)
            setBackgroundDrawable(ColorDrawable(Color.WHITE))
            elevation = 0f
        }
        mClassifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)

        resources.assets.open(mSamplePath).use {
            mBitmap = BitmapFactory.decodeStream(it)
            mBitmap = Bitmap.createScaledBitmap(mBitmap, mInputSize, mInputSize, true)
            binding.imageView.setImageBitmap(mBitmap)
        }

        binding.buttonGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }
        val imageUriString = intent.getStringExtra("image_uri")
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                mBitmap = scaleImage(mBitmap)
                binding.imageView.setImageBitmap(mBitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            // Handle placeholder or default image
            resources.assets.open(mSamplePath).use {
                mBitmap = BitmapFactory.decodeStream(it)
                mBitmap = Bitmap.createScaledBitmap(mBitmap, mInputSize, mInputSize, true)
                binding.imageView.setImageBitmap(mBitmap)
            }
        }
        binding.buttonAnalyze.setOnClickListener {
            val results = mClassifier.recognizeImage(mBitmap).firstOrNull()
            val intent = Intent(this, ResultActivity::class.java)

            intent.putExtra("penyakit", results?.title + "\nAkurasi: " + results?.confidence + "%")
            intent.putExtra("nama", results?.title)

            val stream = ByteArrayOutputStream()
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()

            intent.putExtra("image", byteArray)

            startActivity(intent)
        }


    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            binding.imageView.setImageResource(R.drawable.sample) // Set placeholder
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
                mBitmap = scaleImage(mBitmap)
                binding.imageView.setImageBitmap(mBitmap)
            }
        } else if (result.resultCode == UCrop.RESULT_ERROR) {
            val error = UCrop.getError(result.data!!)
            Log.e("AnalyzeActivity", error.toString())
        }
    }

    private fun cropImage(uri: Uri): Uri {
        val destinationUri = Uri.fromFile(uriToFile(uri, this))
        val options = UCrop.Options().apply {
            setCompressionQuality(100)
            setFreeStyleCropEnabled(true)
        }
        val intent = UCrop.of(uri, destinationUri)
            .withOptions(options)
            .getIntent(this)

        cropImageLauncher.launch(intent)
        return destinationUri
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == mCameraRequestCode) {
            if (resultCode == RESULT_OK && data != null) {
                binding.imageView.setImageResource(R.drawable.sample) // Set placeholder
                mBitmap = data.extras!!.get("data") as Bitmap
                mBitmap = scaleImage(mBitmap)
                binding.imageView.setImageBitmap(mBitmap)
            } else {
                Toast.makeText(this, "Camera cancel..", Toast.LENGTH_LONG).show()
            }
        } else if (requestCode == mGalleryRequestCode) {
            if (data != null) {
                binding.imageView.setImageResource(R.drawable.sample) // Set placeholder
                val uri = data.data
                try {
                    mBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                mBitmap = scaleImage(mBitmap)
                binding.imageView.setImageBitmap(mBitmap)
            }
        } else {
            Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_LONG).show()
        }
    }

    private fun scaleImage(bitmap: Bitmap?): Bitmap {
        val originalWidth = bitmap!!.width
        val originalHeight = bitmap.height
        val scaleWidth = mInputSize.toFloat() / originalWidth
        val scaleHeight = mInputSize.toFloat() / originalHeight
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originalHeight, matrix, true)
    }
}
