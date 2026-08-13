package com.example.animationandroid.alertdialog

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.animationandroid.R
import com.example.animationandroid.utils.AlertDialogUtils

class AlertDialogActivity : AppCompatActivity() {

    private var buttonOne: Button? = null
    private var buttonTwo: Button? = null
    private var buttonThree: Button? = null
    private var buttonFour: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alert_dialog)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonOne = findViewById<Button>(R.id.buttonOne)
        buttonTwo = findViewById<Button>(R.id.buttonTwo)
        buttonThree = findViewById<Button>(R.id.buttonThree)
        buttonFour = findViewById<Button>(R.id.buttonFour)

        buttonOne?.setOnClickListener {
            AlertDialogUtils.showAlertDialogOneButton(
                context = this,
                title = "Terms & Condition",
                icon = R.drawable.ic_baseline_info_24,
                message = "Have You Read All Terms & Conditions",
                positiveText = "YES, Proceed And Continue",
            ) { dialog, which ->
                Toast.makeText(this, "Yes, you can proceed to next", Toast.LENGTH_SHORT).show()

                dialog.dismiss()

            }
        }
        buttonTwo?.setOnClickListener {
            AlertDialogUtils.showAlertDialogTwoButton(
                context = this,
                title = "Terms & Condition",
                icon = R.drawable.ic_baseline_info_24,
                message = "Have You Read All Terms & Conditions?",
                positiveText = "YES, Proceed And Continue",
                negativeText = "NO",
                positiveListener = { dialog, which ->
                    Toast.makeText(this, "Yes, you can proceed to next", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                },
                negativeListener = { dialog, which ->
                    Toast.makeText(this, "Clicked NO", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()

                }
            )
        }

        buttonThree?.setOnClickListener {
            AlertDialogUtils.showAlertDialogThreeButton(
                context = this,
                title = "Terms & Condition",
                icon = R.drawable.ic_baseline_info_24,
                message = "Have You Read All Terms & Conditions?",
                positiveText = "YES",
                negativeText = "NO",
                neutralText = "Cancel",
                positiveListener = { dialog, int ->
                    Toast.makeText(this, "Yes, you can proceed to next", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                },
                negativeListener = { dialog, int ->
                    Toast.makeText(this, "Clicked NO", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                },
                neutralListener = { dialog, int ->
                    Toast.makeText(this, "Clicked Cancelled", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            )
        }

        buttonFour?.setOnClickListener {
            AlertDialogUtils.showCustomDialog(
                context = this,
                icon = R.drawable.ic_baseline_delete_forever_24,
                title = "Failed",
                message = "Are you sure Deleted?"

            )
        }

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {

                override fun handleOnBackPressed() {

                    AlertDialogUtils.showAlertDialogTwoButton(
                        context = this@AlertDialogActivity,
                        title = "Exit",
                        icon = R.drawable.ic_baseline_info_24,
                        message = "Are you sure you want to exit?",
                        positiveText = "YES",
                        negativeText = "NO",

                        positiveListener = { _, _ ->
                            finish()
                        },

                        negativeListener = { dialog, _ ->
                            dialog.dismiss()
                        }
                    )
                }
            }
        )
    }


}