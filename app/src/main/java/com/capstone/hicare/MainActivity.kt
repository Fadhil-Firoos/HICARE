package com.capstone.hicare

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()

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
}