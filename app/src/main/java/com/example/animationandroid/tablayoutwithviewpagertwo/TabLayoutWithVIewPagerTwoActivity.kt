package com.example.animationandroid.tablayoutwithviewpagertwo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.animationandroid.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TabLayoutWithVIewPagerTwoActivity : AppCompatActivity() {

    private var tabLayout: TabLayout? = null
    private var viewPagerTwo: ViewPager2? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tab_layout_with_view_pager_two)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPagerTwo = findViewById<ViewPager2>(R.id.viewPagerTwo)

        val viewPagerTwoAdapter = ViewPagerTwoAdapter(this)
        viewPagerTwo?.setAdapter(viewPagerTwoAdapter)

        viewPagerTwo?.setUserInputEnabled(true)

        TabLayoutMediator(
            tabLayout!!, viewPagerTwo!!
        ) { tab: TabLayout.Tab?, position: Int ->
            when (position) {
                0 -> {
                    tab!!.setText("Chat")
                }

                1 -> {
                    tab!!.setText("Status")
                }

                else -> {
                    tab!!.setText("Calls")
                }
            }
        }.attach()
    }
}