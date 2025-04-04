package com.example.mobileyavts.workers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.example.mobileyavts.MainActivity
import com.example.mobileyavts.R
import java.util.concurrent.TimeUnit

private const val CHANNEL_ID = "notification_channel_id"
private const val CHANNEL_NAME = "Word Reminder Notifications"
private const val CHANNEL_DESC = "Channel for word reminder notifications"
private const val NOTIFICATION_ID = 2
private const val UNIQUE_WORK_NAME = "UniqueReminder"

@SuppressLint("MissingPermission")
fun makeStatusNotification(title: String, context: Context) {
    createNotificationChannel(context)
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        putExtra("reminder", "fromNotification")
    }
    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(title.ifBlank { "Word Reminder" })
        .setContentText("Time to learn a new word!")
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
}

private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = CHANNEL_DESC
        }
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun cancelScheduledNotification(context: Context) {
    WorkManager.getInstance(context).cancelUniqueWork(UNIQUE_WORK_NAME)
}

fun scheduleNotification(context: Context, title: String) {
    val constraints = Constraints.Builder()
        .setRequiresBatteryNotLow(true)
        .build()
    val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.HOURS)
        .setInitialDelay(10, TimeUnit.SECONDS)
        .setConstraints(constraints)
        .setInputData(workDataOf("Title" to title))
        .build()
    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        UNIQUE_WORK_NAME,
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}
