package com.example.animationandroid.implicit_intent

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.animationandroid.R
import com.example.animationandroid.utils.IntentUtils
import com.example.animationandroid.utils.IntentUtils.shareText


class ImplicitIntentActivity : AppCompatActivity() {

    var btnDial: Button? = null
    var btnMessage: Button? = null
    var btnEmail: Button? = null
    var btnShare: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_implicit_intent)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnDial = findViewById<Button>(R.id.btnDial)
        btnMessage = findViewById<Button>(R.id.btnMsg)
        btnEmail = findViewById<Button>(R.id.btnEMail)
        btnShare = findViewById<Button>(R.id.btnShare)

        val phoneNumber = "+919737582727"

        btnDial?.setOnClickListener {
            IntentUtils.dialNumber(
                context = this@ImplicitIntentActivity,
                phoneNumber = phoneNumber
            )
        }
        btnMessage?.setOnClickListener {
            IntentUtils.sendMessage(
                context = this@ImplicitIntentActivity,
                phoneNumber = phoneNumber,
                message = "Hello from AndroidWithJava!"
            )
        }

        btnEmail?.setOnClickListener {
            IntentUtils.sendEmail(
                this,
                arrayOf("mrm.ec08osec@gmail.com"),
                "Subject line",
                "Email body text"
            )
        }
        btnShare!!.setOnClickListener {
            shareText(
                this,
                "Check out this app!"
            )
        }
    }
}