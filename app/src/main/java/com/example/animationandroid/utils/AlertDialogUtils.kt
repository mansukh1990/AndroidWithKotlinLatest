package com.example.animationandroid.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import com.example.animationandroid.R

object AlertDialogUtils {

    fun showAlertDialogOneButton(
        context: Context,
        title: String?,
        @DrawableRes icon: Int,
        message: String?,
        positiveText: String?,
        positiveListener: DialogInterface.OnClickListener?
    ) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(title)
        builder.setMessage(message)

        if (icon != 0) {
            builder.setIcon(icon)
        }

        if (!positiveText.isNullOrEmpty()) {
            builder.setPositiveButton(
                positiveText,
                positiveListener
            ).create()
        }
        builder.setCancelable(false)
        builder.show()
    }

    fun showAlertDialogTwoButton(
        context: Context,
        title: String?,
        @DrawableRes icon: Int,
        message: String?,
        positiveText: String?,
        negativeText: String?,
        positiveListener: DialogInterface.OnClickListener?,
        negativeListener: DialogInterface.OnClickListener?
    ) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(title)
        builder.setMessage(message)
        if (icon != 0) {
            builder.setIcon(icon)
        }
        if (!positiveText.isNullOrEmpty()) {
            builder.setPositiveButton(
                positiveText,
                positiveListener
            )
        }
        if (!negativeText.isNullOrEmpty()) {
            builder.setNegativeButton(
                negativeText,
                negativeListener
            )
        }
        builder.setCancelable(false)
        builder.show()
    }

    fun showAlertDialogThreeButton(
        context: Context,
        title: String?,
        @DrawableRes icon: Int,
        message: String?,
        positiveText: String?,
        negativeText: String?,
        neutralText: String?,
        positiveListener: DialogInterface.OnClickListener?,
        negativeListener: DialogInterface.OnClickListener?,
        neutralListener: DialogInterface.OnClickListener?
    ) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(title)
        builder.setMessage(message)
        if (icon != 0) {
            builder.setIcon(icon)
        }
        if (!positiveText.isNullOrEmpty()) {
            builder.setPositiveButton(
                positiveText,
                positiveListener
            )
        }
        if (!negativeText.isNullOrEmpty()) {
            builder.setNegativeButton(
                negativeText,
                negativeListener
            )
        }
        if (!negativeText.isNullOrEmpty()) {
            builder.setNeutralButton(
                neutralText,
                neutralListener
            )
        }
        builder.setCancelable(false)
        builder.show()
    }
    fun showCustomDialog(
        context: Context,
        @DrawableRes icon: Int,
        title: String?,
        message: String?
    ) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_alert_custom)

        val window = dialog.window

        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        val btnOkay = dialog.findViewById<Button?>(R.id.btnOkay)
        val titles = dialog.findViewById<TextView?>(R.id.txtTitle)
        val messages = dialog.findViewById<TextView?>(R.id.txtMessage)
        val imageSuccessFail = dialog.findViewById<ImageView?>(R.id.imgSuccessFail)

        titles?.text = title
        messages?.text = message
        imageSuccessFail?.setImageResource(icon)

        btnOkay?.setOnClickListener {
            Toast.makeText(context, "Dialog Closed", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.setCancelable(false)
        dialog.show()
    }
}