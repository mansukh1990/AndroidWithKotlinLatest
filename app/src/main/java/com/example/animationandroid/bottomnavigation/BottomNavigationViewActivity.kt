package com.example.animationandroid.bottomnavigation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.animationandroid.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationViewActivity : AppCompatActivity() {

    var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bottom_navigation_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView!!.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())

                }

                R.id.nav_search -> {
                    loadFragment(SearchFragment())

                }

                R.id.nav_utilities -> {
                    loadFragment(UtilitiesFragment())

                }

                R.id.nav_contact_us -> {
                    loadFragment(ContactUsFragment())

                }

                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())

                }
            }

            true
        }
        bottomNavigationView!!.setSelectedItemId(R.id.nav_profile)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}