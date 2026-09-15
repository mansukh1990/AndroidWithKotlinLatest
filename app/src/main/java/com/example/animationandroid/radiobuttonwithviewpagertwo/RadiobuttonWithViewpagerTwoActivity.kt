package com.example.animationandroid.radiobuttonwithviewpagertwo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.animationandroid.R
import com.example.animationandroid.databinding.ActivityRadiobuttonWithViewpagerTwoBinding
import com.example.animationandroid.tablayoutwithviewpagertwo.ViewPagerTwoAdapter

class RadiobuttonWithViewpagerTwoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRadiobuttonWithViewpagerTwoBinding
    private lateinit var viewPagerTwoAdapter: ViewPagerTwoAdapter
    private var isRadiobutton = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRadiobuttonWithViewpagerTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        viewPagerTwoAdapter = ViewPagerTwoAdapter(this)
        binding.viewPagerRadio.setAdapter(viewPagerTwoAdapter)

        binding.viewPagerRadio.setUserInputEnabled(true)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->

            if (isRadiobutton) return@setOnCheckedChangeListener

            when (checkedId) {
                binding.radioChat.id -> {
                    binding.viewPagerRadio.currentItem = 0
                }

                binding.radioStatus.id -> {
                    binding.viewPagerRadio.currentItem = 1
                }

                binding.radioCall.id -> {
                    binding.viewPagerRadio.currentItem = 2
                }
            }
        }
        binding.viewPagerRadio.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                isRadiobutton = true

                when (position) {
                    0 -> binding.radioChat.setChecked(true)
                    1 -> binding.radioStatus.setChecked(true)
                    2 -> binding.radioCall.setChecked(true)
                }
                isRadiobutton = false
            }
        })

    }
}