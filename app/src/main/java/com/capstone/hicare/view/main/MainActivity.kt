package com.capstone.hicare.view.main

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.capstone.hicare.R
import com.capstone.hicare.databinding.ActivityMainBinding
import com.capstone.hicare.view.analyze.AnalyzeActivity
import com.capstone.hicare.view.article.ArticleActivity
import com.capstone.hicare.view.chat.ChatActivity
import com.capstone.hicare.view.fragment.CameraFragment
import com.capstone.hicare.view.fragment.HistoryFragment
import com.capstone.hicare.view.fragment.HomeFragment
import com.capstone.hicare.view.setting.SettingActivity
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var fragment: Fragment = HomeFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        hideNavigationBar()

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
        fragment = HomeFragment()
        replaceFragment(fragment)
        bottomNavigation.show(1)
    }

    private fun replaceFragment(fragment: Fragment) {

        val myTextView = findViewById<FrameLayout>(R.id.Frame_container)

        val layoutParams = myTextView.layoutParams as ViewGroup.MarginLayoutParams

        if (fragment is CameraFragment) {
            window.statusBarColor = getColor(R.color.smoooth_black)

            layoutParams.bottomMargin = resources.getDimensionPixelSize(R.dimen.margin_zero)
            myTextView.layoutParams = layoutParams
            supportActionBar?.show()
            supportActionBar?.apply {
                title = ""
                setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this@MainActivity, R.color.smoooth_black)))
                elevation = 0f
            }

        } else {
            window.statusBarColor = getColor(R.color.white)
            layoutParams.bottomMargin = resources.getDimensionPixelSize(R.dimen.margin_61dp)
            myTextView.layoutParams = layoutParams

            binding.bottomNavigation.visibility = View.VISIBLE
            supportActionBar?.show()
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

    fun navigateToAnalyzeActivity() {
        val intent = Intent(this, AnalyzeActivity::class.java)
        startActivity(intent)
    }

    fun navigateToArticleActivity() {
        val intent = Intent(this, ArticleActivity::class.java)
        startActivity(intent)
    }

    fun openChatActivity(view: View) {
        val intent = Intent(this, ChatActivity::class.java)
        startActivity(intent)
    }

    private fun updateBottomNavigation(index: Int) {
        val bottomNavigation = findViewById<CurvedBottomNavigation>(R.id.bottomNavigation)
        bottomNavigation.show(index)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(fragment is CameraFragment){
            menuInflater.inflate(R.menu.option_menu, menu)
            val settingItem = menu?.findItem(R.id.btn_setting)
            settingItem?.isVisible = false
        }else {
            menuInflater.inflate(R.menu.option_menu, menu)
        }
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