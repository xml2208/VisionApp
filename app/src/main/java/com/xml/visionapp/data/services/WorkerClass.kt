package com.xml.visionapp.data.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.xml.visionapp.R
import net.gotev.speech.Speech

const val PHONE_NUM_NOTIFICATION = "phone_number"
const val BATTERY_DATA = "battery"

class NotificationWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {

        Log.d("xml22", "NotificationWorker: doWork")

        showNotification(
            context = context,
            content = inputData.getString(PHONE_NUM_NOTIFICATION).toString()
        )
        return Result.success()
    }

    private fun showNotification(context: Context, content: String) {
        val notificationId = "NotificationId"
        val notificationBuilder = NotificationCompat.Builder(context, notificationId)
            .setContentTitle("Reminder!")
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setDefaults(Notification.DEFAULT_SOUND)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "1"
            val channelName = "Reminder"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            notificationBuilder.setChannelId("1")
        }

        val notificationManagerCompat = NotificationManagerCompat.from(context.applicationContext)
        notificationManagerCompat.notify(1, notificationBuilder.build())
    }


}

class BatteryReaderWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d("xml22", "BatteryReaderWorker: doWork")
        val battery = inputData.getString(BATTERY_DATA)
        val speech = Speech.getInstance()

        speech.say("Device battery is $battery%")

        return Result.success()
    }


}