package com.example.animationandroid.bottomnavigation

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.animationandroid.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class BottomNavigationViewActivity : AppCompatActivity() {

    private var bottomNavigationView: BottomNavigationView? = null
    var drawerLayout: DrawerLayout? = null
    var navigationView: NavigationView? = null
    var toolbar: Toolbar? = null

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
        drawerLayout = findViewById<DrawerLayout?>(R.id.drawerLayout)
        navigationView = findViewById<NavigationView?>(R.id.navigationView)
        toolbar = findViewById<Toolbar?>(R.id.toolbar)


        //step 1
        setSupportActionBar(toolbar)

        val drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.OpenDrawer,
            R.string.CloseDrawer
        )
        drawerLayout!!.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        navigationView?.setNavigationItemSelectedListener { item ->
            val menuItem = item.itemId

            if (menuItem == R.id.drawer_home) {

                loadFragment(HomeFragment())

            } else if (menuItem == R.id.drawer_search) {

                loadFragment(SearchFragment())

            } else if (menuItem == R.id.drawer_contact) {

                loadFragment(ContactUsFragment())

            } else if (menuItem == R.id.drawer_settings) {

                loadFragment(UtilitiesFragment())

            } else if (menuItem == R.id.drawer_profile) {

                loadFragment(ProfileFragment())

            } else if (menuItem == R.id.drawer_utilities) {

                loadFragment(UtilitiesFragment())

            } else if (menuItem == R.id.drawer_logout) {

                loadFragment(HomeFragment())
            }

            // Close drawer
            drawerLayout?.closeDrawer(GravityCompat.START)

            true
        }

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
        if (savedInstanceState == null) {
            bottomNavigationView!!.setSelectedItemId(R.id.nav_profile)
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {

                if (drawerLayout!!.isDrawerOpen(GravityCompat.START)
                ) {
                    drawerLayout!!.closeDrawer(GravityCompat.START)

                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
        )
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}