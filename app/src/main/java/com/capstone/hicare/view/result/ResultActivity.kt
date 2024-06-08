package com.capstone.hicare.view.result

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.hicare.R
import com.capstone.hicare.databinding.ActivityResultBinding
import org.tensorflow.lite.task.vision.classifier.Classifications

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val byteArray = intent.getByteArrayExtra("image")
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
        val nama = intent.getStringExtra("nama")

        val penyakit: TextView = findViewById(R.id.penyakit)
        val gambar: ImageView = findViewById(R.id.foto)
        val penyebab: TextView = findViewById(R.id.txtpenyebab)
        val obat: TextView = findViewById(R.id.txtObat)
        val btnKembali: Button = findViewById(R.id.btkembali)


        penyakit.text = "Nama Penyakit: " + intent.getStringExtra("penyakit")

        gambar.setImageBitmap(bitmap)

        btnKembali.setOnClickListener {
            onBackPressed()
        }

        if (nama == "Lettuce Healthy") {
            penyebab.text =
                    "1. Perubahan pada kulit: Infeksi jamur pada kucing umumnya mempengaruhi kulit, dan gejalanya dapat berupa daerah kulit yang mengalami kebotakan, kering, bersisik, atau mengelupas. Anda mungkin melihat adanya perubahan warna pada kulit, seperti bercak merah atau kecoklatan.\n" +
                    "\n" +
                    "2. Jamur yang menginfeksi buah dapat bersumber dari tanah, batang yang sakit kanker batang, buah yang sakit, dan tumbuhan inang lain. Phytophthora palmivora akan bertahan dalam tanah, dari sini jamur dapat terbawa oleh percikan air hujan ke buah-buah yang dekat tanah. Setelah mengadakan infeksi, dalam waktu beberapa hari jamur pada buah sudah dapat menghasilkan banyak sporangium (bagian tubuh jamur yang berfungsi sebagai tempat pembentukan spora). Sporangium ini dapat terbawa oleh percikan air, atau oleh angin, dan mencapai buah yang lebih tinggi."

            obat.text = "Cara Mengobati :" + "\n"+
                    "Ada beberpa produk yang dapat digunakan untuk menghilangkan jamur pada kucing, antara lain: " +"\n" +
                    "1. Memetik semua buah busuk yang dilakukan bersamaan dengan pemangkasan ataupun saat panen, kemudian dibenamkan ke dalam tanah sedalam 30 cm." + "\n" +
                    "2. Pengaturan kelembaban kebun bisa dilakukan dengan memperbaiki drainase, memangkas tanaman dan pohon pelindung dengan teratur, dan dengan mengendalikan gulma." +"\n" +
                    "3. Pemberian Dithane efektif dalam mengendalikan penyakit"

        }
        else if (nama == "Wilt and leaf blight"){
            penyebab.text = "Infeksi Wilt and Leaf Blight pada tanaman disebabkan oleh berbagai patogen, seperti jamur Fusarium sp. Dan jamur Cercospora sp.. "

            obat.text = "Cara Mengobati :" + "\n"+
                    "Ada beberpa produk yang dapat digunakan untuk menghilangkan jamur pada kucing, antara lain: " +"\n" +
                    "1. Memetik semua buah busuk yang dilakukan bersamaan dengan pemangkasan ataupun saat panen, kemudian dibenamkan ke dalam tanah sedalam 30 cm." + "\n" +
                    "2. Pengaturan kelembaban kebun bisa dilakukan dengan memperbaiki drainase, memangkas tanaman dan pohon pelindung dengan teratur, dan dengan mengendalikan gulma." +"\n" +
                    "3. Pemberian Dithane efektif dalam mengendalikan penyakit"

        }
        else if (nama == "Viral"){
            penyebab.text = "Viral infection pada tanaman disebabkan oleh berbagai jenis virus yang menyebar melalui serangga vektor, seperti kutu daun atau thrips, atau melalui kontak langsung antara tanaman yang terinfeksi dan yang sehat. Virus dapat menyebabkan berbagai gejala pada tanaman, tergantung pada jenis virus dan tanaman yang terinfeksi. Infeksi virus sering mengakibatkan daun tanaman memiliki bintik-bintik berwarna kuning atau belang-belang, yang dikenal sebagai mosaik. Selain itu, daun bisa menjadi keriting, mengerut, atau mengalami perubahan bentuk. Jika infeksi virus berkembang, virus dapat menyebar ke seluruh bagian tanaman, termasuk batang, yang dapat menyebabkan pertumbuhan terhambat dan daun yang cacat."

            obat.text = "Cara Mengobati : "+"\n"+
                    "1. Pertahankan jarak tanam yang direkomendasikan (5–6m × 5–6m)." +"\n"+
                    "2. Buang cabang yang berlebih dan rusak setelah panen\n" +
                    "3. Tutupi buah-buahan dengan kertas atau kantong plastik dengan satu sisi terbuka di panggung kecil.\n" +
                    "4. Kendalikan serangga kepik Helopeltis Spp untuk mengurangi inisiasi penyakit"

        }
        else if (nama == "Powdery Mildew"){
            penyebab.text = "Disebabkan oleh berbagai jenis jamur dalam famili Erysiphaceae. Penyebaran jamur ini terutama melalui spora yang terbawa angin. bunga, dan buah. Gejala awal ditandai dengan bercak-bercak putih seperti bedak di permukaan bawah dan atas daun. Daun yang terinfeksi bisa menguning dan mati. Powdery mildew tumbuh subur pada iklim/cuaca hangat dan kering."

            obat.text = "Cara Mengobati Jerawat Kucing: "+"\n"+
                    "1. Dengan membersihkan area sekitarnya dengan chlorhexidine antibacterial. Bersihkan bagian jerawat kucing beberapa kali sehari hingga breakout-nya memudar." +"\n"+
                    "2. Selain dibersihkan, cara menghilangkan jerawat kucing selanjutnya yang bisa dilakukan adalah dengan memberikan kompres air hangat untuk mengurangi pembengkakan.\n" +
                    "3. Pemberian makanan mengandung asam lemak seperti omega-3 juga bisa membantu menjaga kesehatan kulit.\n" +
                    "4. Menggunakan shampoo antibakterial atau anti fungal.\n" +
                    "5. Memberikan gel pereda jerawat kucing.\n" +
                    "6. Untuk beberapa kasus, dokter hewan juga merekomendasikan untuk memberikan antibiotik atau antiradang untuk mengurangi pembengkakan."

        }
        else if (nama == "Downy Mildew"){
            penyebab.text = "1. Disebabkan oleh infeksi jamur bremia lactucae, spora jamur ini menyebar melalui hujan atau udara. Hal ini menyebabkan daun memiliki bintik hijau kekuning yang terang pada bagian atas daun, sementara pada bagian bawah tumbuh bulu-bulu halus seperti kapas berwarna putih kebiruan. Jika dibiarkan berkembang, jamur akan menembus ke batang yang dapat menyebabkan batang membusuk."

            obat.text = "Cara Mengobati Jerawat Kucing: "+"\n"+
                    "1. Dengan membersihkan area sekitarnya dengan chlorhexidine antibacterial. Bersihkan bagian jerawat kucing beberapa kali sehari hingga breakout-nya memudar." +"\n"+
                    "2. Selain dibersihkan, cara menghilangkan jerawat kucing selanjutnya yang bisa dilakukan adalah dengan memberikan kompres air hangat untuk mengurangi pembengkakan.\n" +
                    "3. Pemberian makanan mengandung asam lemak seperti omega-3 juga bisa membantu menjaga kesehatan kulit.\n" +
                    "4. Menggunakan shampoo antibakterial atau anti fungal.\n" +
                    "5. Memberikan gel pereda jerawat kucing.\n" +
                    "6. Untuk beberapa kasus, dokter hewan juga merekomendasikan untuk memberikan antibiotik atau antiradang untuk mengurangi pembengkakan."

        }
        else if (nama == "Bacterial"){
            penyebab.text = "Bacterial disebabkan oleh infeksi bakteri seperti  bakteri Erwinia Carotovora, Felicularia F, Rhizoctonia S atau Xanthomonas spp. Bakteri ini dapat menyebar melalui hujan, percikan air, serangga, atau kontak langsung antara tanaman yang terinfeksi dan yang sehat.  Suhu optimal untuk infeksi adalah 73°F (23°C). Namun, penyakit ini terjadi pada suhu yang lebih hangat pada selada yang ditanam di rumah kaca. Bacterial sering mempengaruhi daun tanaman dengan munculnya bintik-bintik coklat kecil yang dikelilingi oleh halo kuning, yang seiring waktu dapat berkembang menjadi bercak-bercak besar yang tidak teratur. "

            obat.text = "Cara Mengobati Scabies Kucing: "+"\n"+
                    "Pengobatan scabies pada kucing yang sudah terbukti aman dan efektif adalah dengan memberikan obat tetes kutu yang mengandung Selamectin, Fluralaner, Sarolaner, atau Moxadectin ke kucing yang terinfeksi."

        }
        else if (nama == "Septoria Blight") {
            penyebab.text = "Disebabkan oleh patogen Septoria Laktucae, penyakit ini menyebar lagi melalui air, angin, penularan serangga, gulma disekitar tanaman, atau bahkan melalui tangan atau peralatan manusia. Daun yang terinfeksi memiliki bintik-bintik berwarna coklat muda yang muncul di antara urat daun. Daun yang terserang dapat mengering dan menjadi tipis."

            obat.text = "Cara Mengobati Scabies Kucing: " + "\n" +
                    "Pengobatan scabies pada kucing yang sudah terbukti aman dan efektif adalah dengan memberikan obat tetes kutu yang mengandung Selamectin, Fluralaner, Sarolaner, atau Moxadectin ke kucing yang terinfeksi."
        }
            else {
            penyebab.text =
                ""
            obat.text =""
        }

    }

}


