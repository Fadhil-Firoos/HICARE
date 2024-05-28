package com.capstone.hicare

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.capstone.hicare.databinding.ActivityMainBinding
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var fragment: Fragment = HomeFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        window.statusBarColor = getColor(R.color.white)

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
                1 -> {
                    fragment = HomeFragment()
                    replaceFragment(
                        fragment
                    )
                }
                2 -> {
                    fragment = CameraFragment()
                    replaceFragment(
                        fragment
                    )
                }
                3 -> {
                    fragment = HistoryFragment()
                    replaceFragment(
                        fragment
                    )
                }

            }


        }
        replaceFragment(HomeFragment())
        bottomNavigation.show(1)

        binding.btnHomeCamera.setOnClickListener{
            updateBottomNavigation(1)
            fragment = HomeFragment()
            replaceFragment(fragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {

        if (fragment is CameraFragment) {
            binding.btnHomeCamera.visibility = View.VISIBLE
            binding.bottomNavigation.visibility = View.GONE
            supportActionBar?.hide()

        } else {
            binding.bottomNavigation.visibility = View.VISIBLE
            supportActionBar?.apply {
                title = ""
                setBackgroundDrawable(ColorDrawable(Color.WHITE))
                elevation = 0f
            }
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.Frame_container, fragment)
            .commit()

    }
    fun navigateToCameraFragment() {
        fragment = CameraFragment()
        replaceFragment(fragment)
        updateBottomNavigation(2)
    }


    fun navigateToHistoryFragment() {
        fragment = HistoryFragment()
        replaceFragment(fragment)
        updateBottomNavigation(3)
    }



    private fun updateBottomNavigation(index: Int) {
        val bottomNavigation = findViewById<CurvedBottomNavigation>(R.id.bottomNavigation)
        bottomNavigation.show(index)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}