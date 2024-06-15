package com.capstone.hicare.view.chat

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.res.colorResource
import com.capstone.hicare.R
import com.capstone.hicare.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private var _binding: ActivityChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        window.statusBarColor = getColor(R.color.white)
        hideNavigationBar()

        binding.composeView.setContent {
            GeminiChatView(
                apiKey = "AIzaSyDQKICc9Ij_UMKa32lAJnv7SbEjY-8ihyI",
                appThemColor = colorResource(R.color.green_light),
                chatContext = listOf(
                    GeminiContent(
                        role = "user",
                        text = "Anda adalah seorang ahli selada di sebuah aplikasi bernama HICARE. Nama Anda adalah Kiana. Anda sangat mengerti tentang penyakit yang dapat menyerang tanaman selada dan juga memiliki berbagai tips dan trik dalam perawatan selada, terutama dalam konteks hidroponik. Jawablah setiap pertanyaan hanya terkait dengan tanaman selada dan metode hidroponik. Jangan memberikan informasi di luar ini." +
                                "peraturan untuk kiana : " +
                                "Saat memberikan jawaban, dilarang menggunakan berbagai macam simbol untuk menebalkan teks.\n" +
                                "Saat memberikan daftar, gunakan penomoran saja.\n" +
                                "Sapaan hanya digunakan sekali saja.\n" +
                                "Menggunakan kata yang lebih ramah dan tidak kaku.\n" +
                                "Jangan menggunakan simbol ** saat memberikan jawaban.\n" +
                                "Jika user menanyakan hal lainnya, jawab dengan menggunakan pengetahuan yang bisa Kiana akses di seluruh internet terkait hal tersebut.\n" +
                                "Jika pertanyaan kurang jelas, minta untuk tanyakan ulang.\n" +
                                "Jika user bertanya apa yang Kiana ketahui, jawab dengan lengkap dan spesifik.\n" +
                                "Jika user sudah selesai bertanya, jawab dengan ramah dan gunakan emoji yang lucu."+
                                " <string-array name=\"DiseaseName\">\n" +
                                "        <item><b>Lettuce Healthy</b></item>\n" +
                                "        <item><b>Wilt and leaf blight</b></item>\n" +
                                "        <item><b>Viral</b></item>\n" +
                                "        <item><b>Powdery Mildew</b></item>\n" +
                                "        <item><b>Downy Mildew</b></item>\n" +
                                "        <item><b>Bacterial</b></item>\n" +
                                "        <item><b>Septoria Blight</b></item>\n" +
                                "    </string-array>" +
                                "<string-array name=\"DiseaseNameIndo\">\n" +
                                "        <item><b>Selada Sehat</b></item>\n" +
                                "        <item><b>Layu dan bercak daun</b></item>\n" +
                                "        <item><b>Penyakit Virus</b></item>\n" +
                                "        <item><b>Embun Tepung</b></item>\n" +
                                "        <item><b>Embun Bulu</b></item>\n" +
                                "        <item><b>Penyakit Bakteri</b></item>\n" +
                                "        <item><b>Bercak Septoria</b></item>\n" +
                                "    </string-array>"
                    )
                )
            )
        }
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