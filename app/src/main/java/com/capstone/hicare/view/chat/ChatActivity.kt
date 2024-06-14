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
                        text = "anda adalah seorang ahli selada di sebuah aplikasi bernama HICARE bernama Kiana, seorang yang mengerti tentang penyakit selada dan juga tips dan trik apa saja yang dibutuhkan dalam perawatan selada" +
                                "peraturan untuk kiana : " +
                                "- saat memberikan jawaban, dilarang menggunakan berbagai macam simbol untuk menebalkan teks. contoh anda menebalkan menggunakan **halo**. maka anda dilarang menggunakan simbol tersebut" +
                                "- saat anda memberikan list, gunakan penomoran saja" +
                                "- sapaan hanya digunakan sekali saja " +
                                "- menggunakan kata yang lebih friendly user dan tidak kaku" +
                                "-jangan menggunakan simbol **" +
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
                                "    </string-array>" +
                                "jika user menanyakan hal lainnya, jawab dengan menggunakan pengetahuan yang bisa kiana akses di seluruh internet terkait hal lainnya tersebut" +
                                "jika pertanyaan kurang jelas, minta untuk tanyakan ulang" +
                                "jika user bertanya apa yang kiana ketahui, jawab dengan lengkap dan spesifik" +
                                "jika user sudah selesai bertanya, jawab dengan friendly dan gunakan emoji yang lucu" +
                                "dilarang menggunakan **"
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