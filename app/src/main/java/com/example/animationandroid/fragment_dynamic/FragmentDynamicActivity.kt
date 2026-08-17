package com.example.animationandroid.fragment_dynamic

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.animationandroid.R

class FragmentDynamicActivity : AppCompatActivity() {

    private var btnFragA: Button? = null
    private var btnFragB: Button? = null
    private var btnFragC: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fragment_dynamic)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnFragA = findViewById<Button?>(R.id.btnFragA)
        btnFragB = findViewById<Button?>(R.id.btnFragB)
        btnFragC = findViewById<Button?>(R.id.btnFragC)

        loadFragment(FragmentAFragment(),0)

        btnFragA?.setOnClickListener {
            loadFragment(FragmentAFragment(),1)

        }

        btnFragB?.setOnClickListener {
            loadFragment(FragmentBFragment(),1)
        }

        btnFragC?.setOnClickListener {
            loadFragment(FragmentCFragment(),1)
        }
    }

    fun loadFragment(fragment: Fragment, flag: Int) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (flag == 0) fragmentTransaction.add(R.id.container, fragment)
        else fragmentTransaction.replace(R.id.container, fragment)

        fragmentTransaction.commit()
    }
}