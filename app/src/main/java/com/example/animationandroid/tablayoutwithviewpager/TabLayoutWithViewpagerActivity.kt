package com.example.animationandroid.tablayoutwithviewpager

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.animationandroid.R
import com.example.animationandroid.databinding.ActivityTabLayoutWithViewpagerBinding

class TabLayoutWithViewpagerActivity : AppCompatActivity() {

    private var binding: ActivityTabLayoutWithViewpagerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTabLayoutWithViewpagerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        binding?.viewPager?.setAdapter(viewPagerAdapter)

        binding?.tabLayout?.setupWithViewPager(binding?.viewPager)
    }
}