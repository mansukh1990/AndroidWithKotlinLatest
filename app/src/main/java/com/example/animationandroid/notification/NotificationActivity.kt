package com.example.animationandroid.notification

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.animationandroid.MainActivity
import com.example.animationandroid.R
import com.example.animationandroid.utils.NotificationHelper


class NotificationActivity : AppCompatActivity() {

    var buttonBigText: Button? = null
    var buttonBigPicture: Button? = null
    var buttonInbox: Button? = null
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                showNotification()
            } else {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notification)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonBigText = findViewById<Button>(R.id.showNotificationBigText)
        buttonBigPicture = findViewById<Button>(R.id.showNotificationBigPicture)
        buttonInbox = findViewById<Button>(R.id.showInbox)

        val destination = Intent(this, MainActivity::class.java).apply {
            putExtra("navigate_to", "chat_fragment")
            putExtra("sender", "Steven Smith")
        }

        NotificationHelper.createChannel(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    showNotification()
                }

                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            showNotification()
        }

        buttonBigText?.setOnClickListener {
            NotificationHelper.showNotificationBigText(
                this@NotificationActivity,
                "NotificationApp",
                "Steven: Hey, are you free today?",
                "Steven: Hey, are you free today? I wanted to discuss the project timeline " +
                        "and see if we can meet sometime this week to go over the details.",
                R.drawable.ic_notification,
                destination,
                true
            )
        }

        buttonBigPicture?.setOnClickListener {
            NotificationHelper.showNotificationBigPicture(
                this@NotificationActivity,
                "NotificationApp",
                "Steven: Hey, are you free today?",
                R.drawable.ic_light,
                R.drawable.ic_notification,
                destination,
                true
            )
        }
    }

    private fun showNotification() {
        val destination = Intent(this, MainActivity::class.java).apply {
            putExtra("navigate_to", "chat_fragment")
            putExtra("sender", "Steven Smith")
        }

        NotificationHelper.showNotificationSimple(
            context = this,
            title = "Notification",
            message = "Receiver Notification",
            iconResId = R.drawable.ic_notification,
            destinationIntent = destination,
            useTaskStack = false
        )
    }
}