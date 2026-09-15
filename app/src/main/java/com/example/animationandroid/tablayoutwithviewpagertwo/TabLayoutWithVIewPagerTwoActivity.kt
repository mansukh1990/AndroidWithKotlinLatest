package com.example.animationandroid.tablayoutwithviewpagertwo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.animationandroid.databinding.ActivityTabLayoutWithViewPagerTwoBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TabLayoutWithVIewPagerTwoActivity : AppCompatActivity() {

    private var binding : ActivityTabLayoutWithViewPagerTwoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTabLayoutWithViewPagerTwoBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding!!.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewPagerTwoAdapter = ViewPagerTwoAdapter(this)
        binding?.viewPagerTwo?.setAdapter(viewPagerTwoAdapter)

        binding?.viewPagerTwo?.setUserInputEnabled(true)

        TabLayoutMediator(
            binding!!.tabLayout,
            binding!!.viewPagerTwo
        ) { tab: TabLayout.Tab?, position: Int ->
            when (position) {
                0 -> {
                    tab?.setText("Chat")
                }

                1 -> {
                    tab?.setText("Status")
                }

                else -> {
                    tab?.setText("Calls")
                }
            }
        }.attach()
    }
}