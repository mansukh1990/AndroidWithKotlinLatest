package com.example.animationandroid.radiobuttonwithviewpagertwo

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.animationandroid.R
import com.example.animationandroid.tablayoutwithviewpagertwo.ViewPagerTwoAdapter

class RadiobuttonWithViewpagerTwoActivity : AppCompatActivity() {

    var viewPager: ViewPager2? = null
    var radioGroup: RadioGroup? = null
    var radioButtonChat: RadioButton? = null
    var radioButtonStatus: RadioButton? = null
    var radioButtonCalls: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_radiobutton_with_viewpager_two)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewPager = findViewById(R.id.viewPagerRadio)
        radioGroup = findViewById(R.id.radioGroup)
        radioButtonChat = findViewById(R.id.radioChat)
        radioButtonStatus = findViewById(R.id.radioStatus)
        radioButtonCalls = findViewById(R.id.radioCall)

        val viewPagerTwoAdapter = ViewPagerTwoAdapter(this)
        viewPager!!.setAdapter(viewPagerTwoAdapter)

        viewPager!!.setUserInputEnabled(false)

        radioGroup!!.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioChat -> {
                    viewPager!!.currentItem = 0
                }
                R.id.radioStatus -> {
                    viewPager!!.currentItem = 1
                }
                else -> {
                    viewPager!!.currentItem = 2
                }
            }
        }

    }
}