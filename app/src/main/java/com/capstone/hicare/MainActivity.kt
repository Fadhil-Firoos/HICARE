package com.capstone.hicare

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()

        supportActionBar?.apply {
            val colorDrawable = ColorDrawable(Color.parseColor("#FFFFFF"))
            title = ""
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(false)
            setBackgroundDrawable(colorDrawable)
            elevation = 0f
        }

        val bottomNavigation = findViewById<CurvedBottomNavigation>(R.id.bottomNavigation)
        bottomNavigation.add(
            CurvedBottomNavigation.Model(1, "Home", R.drawable.baseline_home_24)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(2, "Camera", R.drawable.baseline_camera_24)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(3, "History", R.drawable.baseline_history_24)
        )

        bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                1 -> { replaceFragment(HomeFragment())
                }
                2 -> {replaceFragment(CameraFragment())
                }
                3 -> {replaceFragment(HistoryFragment())
                }

            }


        }
        replaceFragment(HomeFragment())
        bottomNavigation.show(1)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.Frame_container, fragment)
            .commit()

    }
    fun navigateToCameraFragment() {
        replaceFragment(CameraFragment())
        updateBottomNavigation(2)
    }


    fun navigateToHistoryFragment() {
        replaceFragment(HistoryFragment())
        updateBottomNavigation(3)
    }



    private fun updateBottomNavigation(index: Int) {
        val bottomNavigation = findViewById<CurvedBottomNavigation>(R.id.bottomNavigation)
        bottomNavigation.show(index)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_setting -> {
                // Tindakan yang diambil ketika item menu setting dipilih
                // Contoh: Buka aktivitas pengaturan
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}