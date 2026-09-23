package com.example.animationandroid.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.createBitmap

object NotificationHelper {
    const val CHANNEL_ID: String = "message_channel"
    private var notificationId: Int = 100

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "New Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager =
                context.getSystemService(NotificationManager::class.java)
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    fun hasPermission(context: Context): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                        == PackageManager.PERMISSION_GRANTED)
    }

    private fun showNotification(
        context: Context,
        title: String?,
        message: String?,
        iconResId: Int,
        destinationIntent: Intent,
        useTaskStack: Boolean
    ): NotificationCompat.Builder {
        val largeIcon = drawableToBitmap(context, iconResId)
        val pendingIntent = buildPendingIntent(context, destinationIntent, useTaskStack)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(iconResId)
            .setLargeIcon(largeIcon)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
    }

    private fun sendNotification(context: Context, builder: NotificationCompat.Builder) {
        if (!hasPermission(context)) return
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(context).notify(getNextNotificationId(), builder.build())
    }

    fun showNotificationSimple(
        context: Context,
        title: String?,
        message: String?,
        iconResId: Int,
        destinationIntent: Intent,
        useTaskStack: Boolean
    ) {
        val builder = showNotification(
            context, title, message, iconResId, destinationIntent, useTaskStack
        )
        sendNotification(context, builder)
    }

    fun showNotificationBigText(
        context: Context,
        title: String?,
        message: String?,
        bigText: String?,
        iconResId: Int,
        destinationIntent: Intent,
        useTaskStack: Boolean
    ) {
        val builder = showNotification(
            context, title, message, iconResId, destinationIntent, useTaskStack
        )
        builder.setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(bigText)
                .setBigContentTitle(title)
                .setSummaryText(message)
        )

        sendNotification(context, builder)
    }

    fun showNotificationBigPicture(
        context: Context,
        title: String?,
        message: String?,
        bigPicture: Int,
        iconResId: Int,
        destinationIntent: Intent,
        useTaskStack: Boolean
    ) {
        val bigPictureIcon = drawableToBitmap(context, bigPicture)

        val builder = showNotification(
            context, title, message, iconResId, destinationIntent, useTaskStack
        )

        val bigPictureStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(bigPictureIcon)
            .setBigContentTitle(title)
            .setSummaryText(message)
            .bigLargeIcon(null as Bitmap?)

        builder.setStyle(bigPictureStyle)
        sendNotification(context, builder)
    }

    fun showNotificationBigPictureBitmap(
        context: Context,
        title: String?,
        message: String?,
        bigPictureBitmap: Bitmap,
        iconResId: Int,
        destinationIntent: Intent,
        useTaskStack: Boolean = false
    ) {
        val builder = showNotification(
            context, title, message, iconResId, destinationIntent, useTaskStack
        )

        val bigPictureStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(bigPictureBitmap)
            .setBigContentTitle(title)
            .setSummaryText(message)
            .bigLargeIcon(null as Bitmap?)

        builder.setStyle(bigPictureStyle)
        sendNotification(context, builder)
    }

    private fun buildPendingIntent(
        context: Context?,
        destinationIntent: Intent,
        useRaskStack: Boolean
    ): PendingIntent? {
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

        if (useRaskStack) {
            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addNextIntentWithParentStack(destinationIntent)
            val pendingIntent = stackBuilder.getPendingIntent(notificationId, flags)
            if (pendingIntent != null) {
                return pendingIntent
            }
        }
        destinationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return PendingIntent.getActivity(context, notificationId, destinationIntent, flags)
    }

    private fun drawableToBitmap(context: Context, drawableResId: Int): Bitmap? {
        val drawable =
            ResourcesCompat.getDrawable(context.resources, drawableResId, null) ?: return null

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val width = if (drawable.intrinsicWidth > 0) drawable.intrinsicWidth else 1
        val height = if (drawable.intrinsicHeight > 0) drawable.intrinsicHeight else 1

        val bitmap = createBitmap(width, height)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private fun getNextNotificationId(): Int {
        return notificationId++
    }
}