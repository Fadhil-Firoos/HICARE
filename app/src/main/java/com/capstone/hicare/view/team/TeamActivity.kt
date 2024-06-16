package com.capstone.hicare.view.team

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.capstone.hicare.R
import com.capstone.hicare.databinding.ActivityTeamBinding

class TeamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()
        supportActionBar?.apply {
            val titleString = "About Us"
            val spannableString = SpannableString(titleString)
            spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(this@TeamActivity, R.color.black)), 0, titleString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            title = spannableString
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.arrow_back_left)
            setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this@TeamActivity, R.color.green_light)))
            elevation = 0f
        }

        binding.apply {
            fadhil.setOnClickListener {
                val linkedinFadhil = "https://www.linkedin.com/in/fadhil-firoos"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(linkedinFadhil)
                startActivity(intent)
            }
            nanda.setOnClickListener {
                val linkedinNanda = "https://www.linkedin.com/in/nanda-satria-putra-1096332bb"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(linkedinNanda)
                startActivity(intent)
            }
            ilham.setOnClickListener {
                val linkedinNanda = "https://www.linkedin.com/in/ilham-yoga-pratama-69775a255"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(linkedinNanda)
                startActivity(intent)
            }
            radot.setOnClickListener {
                val linkedinNanda = "https://www.linkedin.com/in/radot-nababan-a51247195"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(linkedinNanda)
                startActivity(intent)
            }
            yani.setOnClickListener {
                val linkedinNanda = "https://www.linkedin.com/in/yani-siti-nurpazrin-67b2b1260"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(linkedinNanda)
                startActivity(intent)
            }
            sultan.setOnClickListener {
                val linkedinNanda = "https://www.linkedin.com/in/sultanrafi"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(linkedinNanda)
                startActivity(intent)
            }
            zulza.setOnClickListener {
                val linkedinNanda = "https://www.linkedin.com/in/zulza-laddera-aripin"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(linkedinNanda)
                startActivity(intent)
            }
        }
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
}