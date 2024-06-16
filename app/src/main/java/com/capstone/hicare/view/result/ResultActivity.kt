package com.capstone.hicare.view.result

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.capstone.hicare.R
import com.capstone.hicare.databinding.ActivityResultBinding
import com.capstone.hicare.history.AppDatabase
import com.capstone.hicare.history.PredictionHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        window.statusBarColor = getColor(R.color.white)
        hideNavigationBar()

        val nama = intent.getStringExtra("nama")
        val imageByteArray = intent.getByteArrayExtra("image")
        val bitmap = imageByteArray?.let { BitmapFactory.decodeByteArray(imageByteArray, 0, it.size) }



        binding.apply {
            penyakit.text = getString(R.string.nama) + intent.getStringExtra("penyakit")
            foto.setImageBitmap(bitmap)

            btkembali.setOnClickListener {
                onBackPressed()
            }
            back.setOnClickListener {
                onBackPressed()
            }

            if (nama == "Healthy") {
                txtpenyebab.text = getString(R.string.manfaat_healthy)

                txtObat.text = getString(R.string.tips_perawatan_healthy)
                textView.text = getString(R.string.manfaat_selada)
                textView2.text = getString(R.string.tips_perawatan)
            }
            else if (nama == "Wilt and leaf blight"){
                txtpenyebab.text = getString(R.string.penyebab_wilt_and_leaf_blight)

                txtObat.text = getString(R.string.penanganan_wilt_and_leaf_blight)

            }
            else if (nama == "Viral"){
                txtpenyebab.text = getString(R.string.penyebab_viral)

                txtObat.text = getString(R.string.penanganan_viral)
            }
            else if (nama == "Powdery Mildew"){
                txtpenyebab.text = getString(R.string.penyebab_powdery_mildew)

                txtObat.text = getString(R.string.penanganan_powdery_mildew)

            }
            else if (nama == "Downy Mildew"){
                txtpenyebab.text = getString(R.string.penyebab_downy_mildew)

                txtObat.text = getString(R.string.penangan_downy_mildew)

            }
            else if (nama == "Bacterial"){
                txtpenyebab.text = getString(R.string.penyebab_bacterial)

                txtObat.text = getString(R.string.penenganan_bacterial)

            }
            else if (nama == "Septoria Blight") {
                txtpenyebab.text = getString(R.string.penyebab_septoria_blight)

                txtObat.text = getString(R.string.penenganan_septoria_blight)

            }
            else if (nama == "Undefine" || nama == "under treshold"){
                txtpenyebab.visibility = TextView.INVISIBLE
                txtObat.visibility = TextView.INVISIBLE
                foto.setImageResource(R.drawable.nahida)
                penyakit.visibility = TextView.INVISIBLE
                myRectangleView5.visibility = Button.INVISIBLE
                scrollView2.visibility = ScrollView.INVISIBLE
                tvTidakTerdefinisi.visibility = TextView.VISIBLE
                save.visibility = Button.INVISIBLE
                back.visibility = Button.VISIBLE
                btkembali.visibility = Button.INVISIBLE
            }
        }

        binding.save.setOnClickListener {
            val imageByteArray = intent.getByteArrayExtra("image")
            val result = binding.penyakit.text.toString()

            if (imageByteArray != null) {
                val imageUri = Uri.parse(imageByteArray.toString())
                showToast("Data Tersimpan")
                savePredictionToDatabase(imageUri, result)
            } else {
                showToast("No image URI provided")
                finish()
            }
        }

    }

    private fun savePredictionToDatabase(imageUri: Uri, result: String) {
        val imageByteArray = intent.getByteArrayExtra("image")
        val result = binding.penyakit.text.toString()

        if (imageByteArray != null) {
            val fileName = "cropped_image_${System.currentTimeMillis()}.jpg"
            val outputFile = File(cacheDir, fileName)
            FileOutputStream(outputFile).use { outputStream ->
                outputStream.write(imageByteArray)
            }
            val prediction = PredictionHistory(imagePath = outputFile.absolutePath, result = result)
            GlobalScope.launch(Dispatchers.IO) {
                val database = AppDatabase.getDatabase(applicationContext)
                try {
                    database.predictionHistoryDao().insertPrediction(prediction)
                    Log.d(TAG, "Prediction saved successfully: $prediction")
                    val predictions = database.predictionHistoryDao().getAllPredictions()
                    Log.d(TAG, "All predictions after save: $predictions")
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to save prediction: $prediction", e)
                }
            }
        } else {
            Log.e(TAG, "Image byte array is null, cannot save prediction to database.")
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
        const val IMAGE_URI = "image"
        const val TAG = "imagePicker"
        const val RESULT_TEXT = "result_text"
        const val REQUEST_HISTORY_UPDATE = 1

    }
}