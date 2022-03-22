package com.scoto.fodamy.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.scoto.fodamy.R
import kotlin.random.Random

/**
 * @author Sefa ÇOTOĞLU
 * Created 10.03.2022
 */
@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class PushNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        createNotificationChannel()
        createNotification(message)
        super.onMessageReceived(message)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                enableLights(true)
                lightColor = Color.RED
            }

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(message: RemoteMessage) {
        val title = message.data[KEY_TITLE]
        val content = message.data[KEY_BODY]
        val notificationId = Random.nextInt()
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.category_item)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, notification.build())
        }
    }

    companion object {
        private const val CHANNEL_ID = "FodamyID"
        private const val CHANNEL_NAME = "Fodamy"
        private const val KEY_TITLE = "title"
        private const val KEY_BODY = "body"
    }
}
