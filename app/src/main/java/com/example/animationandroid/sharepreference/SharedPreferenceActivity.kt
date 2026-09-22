package com.example.animationandroid.sharepreference

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.animationandroid.databinding.ActivitySharedPreferenceBinding

class SharedPreferenceActivity : AppCompatActivity() {

    private var binding: ActivitySharedPreferenceBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySharedPreferenceBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding!!.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val preferenceManager = SharedPreferenceManager(this@SharedPreferenceActivity)
            val isLogin = preferenceManager.isLoggedIn()

            val intent = if (isLogin) {
                Intent(this@SharedPreferenceActivity, HomeActivity::class.java)
            } else {
                Intent(this@SharedPreferenceActivity, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 4000)
    }
}