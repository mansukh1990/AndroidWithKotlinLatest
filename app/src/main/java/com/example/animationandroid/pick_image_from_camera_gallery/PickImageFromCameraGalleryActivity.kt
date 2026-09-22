package com.example.animationandroid.pick_image_from_camera_gallery

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.animationandroid.databinding.ActivityPickImageFromCameraGalleryBinding
import com.example.animationandroid.databinding.DialogImagePickerBinding

class PickImageFromCameraGalleryActivity : AppCompatActivity() {

    private var binding: ActivityPickImageFromCameraGalleryBinding? = null

    private var cameraLauncher: ActivityResultLauncher<Intent>? = null
    private var galleryLauncher: ActivityResultLauncher<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPickImageFromCameraGalleryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding!!.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Camera Launcher
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val extras = result.data?.extras
                if (extras != null) {
                    val bitmap: Bitmap? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        extras.getParcelable("data", Bitmap::class.java)
                    } else {
                        @Suppress("DEPRECATION")
                        extras.getParcelable("data") as? Bitmap
                    }
                    if (bitmap != null) {
                        binding?.imgProfile?.setImageBitmap(bitmap)
                    }
                }
            }
        }

        // Initialize Gallery Launcher
        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                binding?.imgProfile?.setImageURI(uri)
            }
        }

        binding?.btnUploadImage?.setOnClickListener {
            showImagePickerDialog()
        }
    }

    private fun showImagePickerDialog() {
        val dialog = Dialog(this)
        val dialogBinding: DialogImagePickerBinding = DialogImagePickerBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(false)

        if (dialog.window != null) {
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        dialogBinding.layoutCamera.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher?.launch(intent)
        }

        dialogBinding.layoutGallery.setOnClickListener {
            dialog.dismiss()
            galleryLauncher?.launch("image/*")
        }

        dialogBinding.layoutCancel.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }
}
