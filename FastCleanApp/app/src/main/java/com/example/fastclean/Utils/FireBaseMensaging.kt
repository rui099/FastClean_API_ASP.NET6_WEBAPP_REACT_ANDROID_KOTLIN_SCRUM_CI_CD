package com.example.fastclean.Utils

import android.app.Notification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationManager

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import android.util.Log;


import android.app.NotificationChannel
import android.os.Build
import android.widget.RemoteViews


import androidx.core.app.NotificationCompat

import com.example.fastclean.MainActivity
import com.example.fastclean.R


class FireBaseMensaging : FirebaseMessagingService() {
    private val TAG = "FirebaseMessagingServic"
    var channelID = "100"
    val CHANNEL_NAME = "channel_name"

    fun FirebaseMessagingService() {}

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if (remoteMessage.data.size > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
        }

        if (remoteMessage.notification != null) {
            val title = remoteMessage.notification!!.title //get title
            val message = remoteMessage.notification!!.body //get message
            Log.d(TAG, "Message Notification Title: $title")
            Log.d(TAG, "Message Notification Body: $message")
            sendNotification(title, message)
        }
    }

    override fun onDeletedMessages() {}

    private fun sendNotification(title: String?, messageBody: String?) {
        val action = "com.example.fastclean_TARGET_NOTIFICATIONS"

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val intent2 =Intent(action)
        createNotificationChannel()

        val pendingIntent = PendingIntent.getActivity(
            this,
            121,
            intent2, PendingIntent.FLAG_UPDATE_CURRENT
        )



        val contentView = RemoteViews(packageName, R.layout.notification_layout)
        contentView.setTextViewText(R.id.fb_notification_title,title)
        contentView.setTextViewText(R.id.fb_notification_body,messageBody)
        
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder= NotificationCompat.Builder(this,channelID)
            .setCustomContentView(contentView)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)



        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())

    }

    fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelID,CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH)
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)

        }
    }
}