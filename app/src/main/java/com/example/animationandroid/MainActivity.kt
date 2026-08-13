package com.example.animationandroid

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtAmin = findViewById<TextView>(R.id.textView)
        val btnTranslate = findViewById<Button>(R.id.btnTranslate)
        val btnAlpha = findViewById<Button>(R.id.btnAlpha)
        val btnRotate = findViewById<Button>(R.id.btnRotate)
        val btnScale = findViewById<Button>(R.id.btnScale)

        val move = AnimationUtils.loadAnimation(this, R.anim.move)

        btnTranslate.setOnClickListener {
            txtAmin.startAnimation(move)

        }
        btnAlpha.setOnClickListener {
            val alpha = AnimationUtils.loadAnimation(this, R.anim.alpha)
            txtAmin.startAnimation(alpha)
        }
        btnRotate.setOnClickListener {
            val rotate = AnimationUtils.loadAnimation(this, R.anim.rotation)
            txtAmin.startAnimation(rotate)
        }
        btnScale.setOnClickListener {
            val scale = AnimationUtils.loadAnimation(this, R.anim.scale)
            txtAmin.startAnimation(scale)
        }
    }
}