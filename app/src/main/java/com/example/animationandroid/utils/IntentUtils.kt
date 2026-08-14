package com.example.animationandroid.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.net.toUri

object IntentUtils {

    fun startActivityIntent(
        context: Context,
        intent: Intent,
        errorMessage: String
    ) {
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    fun dialNumber(
        context: Context,
        phoneNumber: String?
    ) {
        if (phoneNumber.isNullOrBlank()) return

        val intent = Intent(Intent.ACTION_DIAL)
        intent.setData(("tel:$phoneNumber").toUri())
        startActivityIntent(
            context = context,
            intent = intent,
            errorMessage = "No phone application found"
        )
    }

    fun sendMessage(
        context: Context,
        phoneNumber: String?,
        message: String?
    ) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData(("smsto:$phoneNumber").toUri())
        intent.putExtra("sms_body", message ?: "")
        startActivityIntent(
            context = context,
            intent = intent,
            errorMessage = "No message application found"
        )
    }

    fun sendEmail(
        context: Context,
        recipients: Array<String>,
        subject: String,
        body: String
    ) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData(Uri.parse("mailto:"))
        intent.putExtra(Intent.EXTRA_EMAIL, recipients)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, body)
        startActivityIntent(context, intent, "No email application found")
    }
    fun shareText(context: Context, text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivityIntent(
            context,
            Intent.createChooser(intent, "Share via"),
            "No share application found"
        )
    }
}