package com.example.animationandroid.pick_image_from_camera_gallery

import android.Manifest
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.animationandroid.R
import com.example.animationandroid.databinding.ActivityPickImageFromCameraGalleryBinding
import com.example.animationandroid.databinding.DialogFullScreenImageBinding
import com.example.animationandroid.databinding.DialogImagePickerBinding
import com.example.animationandroid.utils.NotificationHelper

class PickImageFromCameraGalleryActivity : AppCompatActivity() {

    private var binding: ActivityPickImageFromCameraGalleryBinding? = null

    private var cameraLauncher: ActivityResultLauncher<Intent>? = null
    private var galleryLauncher: ActivityResultLauncher<String>? = null
    private var permissionLauncher: ActivityResultLauncher<String>? = null
    private var notificationPermissionLauncher: ActivityResultLauncher<String>? = null

    private var pendingDownloadBitmap: Bitmap? = null

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

        NotificationHelper.createChannel(this)

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

        // Initialize Storage Permission Launcher for API < 29
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    pendingDownloadBitmap?.let { saveBitmapToGallery(it) }
                } else {
                    Toast.makeText(
                        this,
                        "Storage permission is required to save image",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        // Initialize Notification Permission Launcher for API >= 33
        notificationPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    pendingDownloadBitmap?.let { triggerNotification(it) }
            }
        }

        binding?.btnUploadImage?.setOnClickListener {
            showImagePickerDialog()
        }

        // Image preview icon click listener
        binding?.btnPreview?.setOnClickListener {
            showFullScreenImageDialog()
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

    private fun showFullScreenImageDialog() {
        val drawable = binding?.imgProfile?.drawable ?: return

        val dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        val dialogBinding = DialogFullScreenImageBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        if (dialog.window != null) {
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        dialogBinding.imgFullScreen.setImageDrawable(
            drawable.constantState?.newDrawable() ?: drawable
        )

        dialogBinding.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnDownload.setOnClickListener {
            val bitmap = drawableToBitmap(drawable)
            checkAndSaveBitmap(bitmap)
        }

        dialog.show()
    }

    private fun checkAndSaveBitmap(bitmap: Bitmap) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            pendingDownloadBitmap = bitmap
            permissionLauncher?.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            saveBitmapToGallery(bitmap)
        }
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        if ((drawable is BitmapDrawable) && drawable.bitmap != null) {
            return drawable.bitmap
        }
        val width = if (drawable.intrinsicWidth > 0) drawable.intrinsicWidth else 500
        val height = if (drawable.intrinsicHeight > 0) drawable.intrinsicHeight else 500
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private fun saveBitmapToGallery(bitmap: Bitmap) {
        val filename = "IMG_${System.currentTimeMillis()}.jpg"

        try {
            val resolver = contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    put(MediaStore.MediaColumns.IS_PENDING, 1)
                }
            }

            val imageUri =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            if (imageUri != null) {
                resolver.openOutputStream(imageUri)?.use { fos ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    contentValues.clear()
                    contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(imageUri, contentValues, null, null)
                }

                Toast.makeText(this, "Image downloaded successfully", Toast.LENGTH_SHORT).show()
                sendDownloadNotification(bitmap)
            } else {
                Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to download image: ${e.message}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun sendDownloadNotification(bitmap: Bitmap) {
        pendingDownloadBitmap = bitmap
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            notificationPermissionLauncher?.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            triggerNotification(bitmap)
        }
    }

    private fun triggerNotification(bitmap: Bitmap) {
        val destination = Intent(this, PickImageFromCameraGalleryActivity::class.java)
        NotificationHelper.showNotificationBigPictureBitmap(
            context = this,
            title = "Image Downloaded",
            message = "Image saved to gallery successfully.",
            bigPictureBitmap = bitmap,
            iconResId = R.drawable.ic_notification,
            destinationIntent = destination,
            useTaskStack = false
        )
    }
}
