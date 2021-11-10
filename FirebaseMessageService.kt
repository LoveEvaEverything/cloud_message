package nz.co.adriley.meteror.comms

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import nz.co.adriley.meteror.ui.R

/**
 * Created by sgao on 10/11/2021 14:34
 **/
class FirebaseMessageService: FirebaseMessagingService() {
    companion object{
        private const val TAG = "MyFirebaseMsgService"
    }


    override fun onMessageReceived(p0: RemoteMessage) {
        Log.d(TAG, "Message Notification Body: " + p0.notification?.body)
        if(p0.notification != null) {
            sendNotification(p0.notification?.body)
        }
    }
    fun sendNewTokenToServer(newToken: String?) {
        Log.d("Test", "Send new token $newToken to server")
    }

    private fun sendNotification(message:String?) {
        val channelId = getString(R.string.firebase_notification_channel_id)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.message)
            .setContentTitle("New message")
            .setContentText(message)
            .setAutoCancel(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Test", IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onNewToken(p0: String) {
        Log.e("Test", "Refresh token $p0")
    }
}