package com.capstone.hicare.view.result

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.hicare.R
import com.capstone.hicare.databinding.ActivityResultBinding
import com.capstone.hicare.history.AppDatabase
import com.capstone.hicare.history.PredictionHistory
import com.capstone.hicare.view.fragment.HistoryFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nama = intent.getStringExtra("nama")
        val imageByteArray = intent.getByteArrayExtra("image")
        val bitmap = imageByteArray?.let { BitmapFactory.decodeByteArray(imageByteArray, 0, it.size) }



        binding.apply {
            penyakit.text = "Nama : " + intent.getStringExtra("penyakit")
            foto.setImageBitmap(bitmap)

            btkembali.setOnClickListener {
                onBackPressed()
            }

            if (nama == "Healthy") {
                txtpenyebab.text =
                    "1. Menurunkan tekanan darah\n" +
                            "2. Menurunkan resiko kanker\n" +
                            "3. Meredakan flu dan batuk\n" +
                            "4. Melancarkan saluran pencernaan\n"

                txtObat.text =
                            "1. Penyiraman bisa dilakukan sebanyak 2 kali untuk tanaman yang baru ditanam. Penyiraman dilakukan pada pagi dan sore hari. Setelah tanaman sudah cukup dewasa, frekuensi penyiraman dikurangi menjadi 1 kali sehari pada pagi hari." + "\n" +
                            "2. Pengaturan kelembaban kebun bisa dilakukan dengan memperbaiki drainase, memangkas tanaman dan pohon pelindung dengan teratur, dan dengan mengendalikan gulma." +"\n" +
                            "3. Pupuk baru diberikan setelah tanaman sudah berumur 3—5 bulan. Pupuk yang diberikan adalah NPK (15–15-15) yang bisa Anda dapati di toko pertanian. Pupuk diberikan sebanyak 50—300 gram/pohon. Pemupukan selanjutnya bisa dilakukan setelah tanaman berumur 4 bulan hingga tanaman berbunga. Setelah pohon berusia 1—2 tahun, berikan NPK setahun sekali setelah proses panen raya berakhir.\n" +
                                    "4. Tunas liar atau tunas air pada pohon harus dipangkas karena bisa mengganggu pertumbuhan tanaman. Pemangkasan juga dilakukan ketika pohon sudah mencapai ketinggian 1,5—2 m dengan memilih tiga atau empat batang utama agar tajuk tanaman rimbun. Pemangkasan cabang yang lain dilakukan 1—2 cm dari batang utama sehingga luka pemangkasan tidak akan melukai batang utama. Pemangkasan juga dilakukan pada cabang yang tua, kering, dan tidak produktif.\n" +
                                    "Memangkas daun dan buah yang tidak sehat"
                textView.text = "Manfaat Selada"
                textView2.text = "Tips Perawatan"
            }
            else if (nama == "Wilt and leaf blight"){
                txtpenyebab.text = "Infeksi Wilt and Leaf Blight pada tanaman disebabkan oleh berbagai patogen, seperti jamur Fusarium sp. dan jamur Cercospora sp.. Penyakit ini dapat menyebar melalui tanah, air, angin, serta melalui kontak dengan tanaman yang terinfeksi atau alat-alat pertanian yang tidak steril. Wilt atau layu adalah kondisi di mana tanaman kehilangan turgor atau kekakuan sel-sel, sehingga daun dan batang tampak layu dan mengendur. Penyebab utama layu adalah infeksi oleh patogen yang menyerang sistem vaskular tanaman, seperti jamur Verticillium atau Fusarium, yang menghalangi aliran air dan nutrisi di dalam tanaman. Gejala awal termasuk layu pada daun muda dan pucuk tanaman, yang kemudian menyebar ke seluruh tanaman, menyebabkan defoliasi dan kematian tanaman jika tidak segera ditangani."

                txtObat.text =
                    "1. Pangkas daun yang terinfeksi untuk membantu tanaman tumbuh lebih baik.\n" +
                            "2. Bersihkan secara rutin area tanam hidroponik, seperti wadah dan pipa tempat pengairan.\n" +
                            "3. Gunakan fungisida untuk mengendalikan infeksi jamur."

            }
            else if (nama == "Viral"){
                txtpenyebab.text = "Viral infection pada tanaman disebabkan oleh berbagai jenis virus yang menyebar melalui serangga vektor, seperti kutu daun atau thrips, atau melalui kontak langsung antara tanaman yang terinfeksi dan yang sehat. Virus dapat menyebabkan berbagai gejala pada tanaman, tergantung pada jenis virus dan tanaman yang terinfeksi. Infeksi virus sering mengakibatkan daun tanaman memiliki bintik-bintik berwarna kuning atau belang-belang, yang dikenal sebagai mosaik. Selain itu, daun bisa menjadi keriting, mengerut, atau mengalami perubahan bentuk. Jika infeksi virus berkembang, virus dapat menyebar ke seluruh bagian tanaman, termasuk batang, yang dapat menyebabkan pertumbuhan terhambat dan daun yang cacat."

                txtObat.text =
                    "1. Tidak ada pengobatan langsung untuk tanaman yang sudah terserang virus, tetapi penggunaan insektisida dapat membantu mengurangi penyebaran hama pembawa virus.\n"+
                            "2. Pisahkan tanaman yang terinfeksi untuk mencegah penyebaran lebih lanjut."
            }
            else if (nama == "Powdery Mildew"){
                txtpenyebab.text = "Disebabkan oleh berbagai jenis jamur dalam famili Erysiphaceae. Penyebaran jamur ini terutama melalui spora yang terbawa angin. bunga, dan buah. Gejala awal ditandai dengan bercak-bercak putih seperti bedak di permukaan bawah dan atas daun. Daun yang terinfeksi bisa menguning dan mati. Powdery mildew tumbuh subur pada iklim/cuaca hangat dan kering."

                txtObat.text =
                    "1. Singkirkan tanaman yang terinfeksi dari lingkungan tanam.\n"+
                            "2. Buang bagian daun yang terinfeksi untuk mengurangi penyebaran penyakit.\n" +
                            "3. Berikan jarak minimal 30-45 cm antar tanaman untuk meningkatkan sirkulasi udara pada tanaman\n" +
                            "4. Semprotkan Fungisida seperti sulfur dalam interval 7-14 hari untuk memberikan proteksi berkelanjutan pada tanaman.\n"

            }
            else if (nama == "Downy Mildew"){
                txtpenyebab.text = "1. Disebabkan oleh infeksi jamur bremia lactucae, spora jamur ini menyebar melalui hujan atau udara. Hal ini menyebabkan daun memiliki bintik hijau kekuning yang terang pada bagian atas daun, sementara pada bagian bawah tumbuh bulu-bulu halus seperti kapas berwarna putih kebiruan. Jika dibiarkan berkembang, jamur akan menembus ke batang yang dapat menyebabkan batang membusuk."

                txtObat.text =
                    "1. Singkirkan tanaman yang terinfeksi dari lingkungan tanam." +"\n"+
                            "2. Hindari Penyiraman dari atas yang dapat menyebabkan permukaan daun menjadi basah.\n" +
                            "3. Tingkatkan sirkulasi udara untuk mengurangi kelembaban tanaman serta berikan pencahayaan yang cukup.\n" +
                            "4. Hindari penanaman yang padat dan kendalikan gulma, agar sirkulasi udara di sekitar tanaman tetap baik"

            }
            else if (nama == "Bacterial"){
                txtpenyebab.text = "Bacterial disebabkan oleh infeksi bakteri seperti  bakteri Erwinia Carotovora, Felicularia F, Rhizoctonia S atau Xanthomonas spp. Bakteri ini dapat menyebar melalui hujan, percikan air, serangga, atau kontak langsung antara tanaman yang terinfeksi dan yang sehat.  Suhu optimal untuk infeksi adalah 73°F (23°C). Namun, penyakit ini terjadi pada suhu yang lebih hangat pada selada yang ditanam di rumah kaca. Bacterial sering mempengaruhi daun tanaman dengan munculnya bintik-bintik coklat kecil yang dikelilingi oleh halo kuning, yang seiring waktu dapat berkembang menjadi bercak-bercak besar yang tidak teratur. "

                txtObat.text =
                    "1. Atur jarak tanam sehingga tidak bersentuhan antar daun luar." +"\n"+
                            "2. Berikan pencahayaan yang cukup,  dengan sinar matahari langsung setidaknya 4-6 jam per hari.\n" +
                            "3. Buang bagian daun yang terinfeksi untuk mengurangi penyebaran penyakit\n" +
                            "4. Semprot dengan pestisida yang mengandung tembaga hidroksida atau campuran tembaga dan mankozeb dengan interval 3-5 hari"



            }
            else if (nama == "Septoria Blight") {
                txtpenyebab.text =
                    "Disebabkan oleh patogen Septoria Laktucae, penyakit ini menyebar lagi melalui air, angin, penularan serangga, gulma disekitar tanaman, atau bahkan melalui tangan atau peralatan manusia. Daun yang terinfeksi memiliki bintik-bintik berwarna coklat muda yang muncul di antara urat daun. Daun yang terserang dapat mengering dan menjadi tipis."

                txtObat.text =
                    "1. Buang bibit yang menunjukkan bercak daun sebelum dipindahkan ke media hidroponik \n" +
                            "2. Hindari membasahi daun dengan melakukan penyiraman dari atas\n" +
                            "3. Bersihkan gulma yang berada disekitar tanaman \n" +
                            "3. Tingkatkan sirkulasi udara yang memadai \n" +
                            "3. Semprot tanaman dengan fungisida yang mengandung bahan dasar tembaga seperti tembaga diammonia diasetat."

            }
            else if (nama == "Undefine"){
                txtpenyebab.visibility = TextView.INVISIBLE
                txtObat.visibility = TextView.INVISIBLE
                foto.setImageResource(R.drawable.nahida)
                penyakit.visibility = TextView.INVISIBLE
                myRectangleView5.visibility = Button.INVISIBLE
                scrollView2.visibility = ScrollView.INVISIBLE
                tvTidakTerdefinisi.visibility = TextView.VISIBLE
                save.visibility = Button.INVISIBLE
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


    companion object {
        const val IMAGE_URI = "image"
        const val TAG = "imagePicker"
        const val RESULT_TEXT = "result_text"
        const val REQUEST_HISTORY_UPDATE = 1

    }

}


