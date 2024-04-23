package com.xml.visionapp.presentation

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.xml.visionapp.R
import com.xml.visionapp.data.services.NotificationListener
import com.xml.visionapp.data.services.NotificationWorker
import com.xml.visionapp.data.services.PHONE_NUM_NOTIFICATION
import com.xml.visionapp.presentation.composables.DoctorMainUi
import com.xml.visionapp.ui.theme.IconsBlue
import com.xml.visionapp.ui.theme.VisionAppTheme
import java.util.Calendar
import java.util.concurrent.TimeUnit

const val REQUEST_NOTIFICATION_PERMISSION = 1

class DoctorScreen : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            VisionAppTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.main_bcg),
                        contentScale = ContentScale.Crop,
                        contentDescription = ""
                    )
                    Icon(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(vertical = 10.dp, horizontal = 10.dp)
                            .clickable { onBackPressedDispatcher.onBackPressed() },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "BackToPrevious",
                        tint = IconsBlue
                    )
                    DoctorMainUi(
                        modifier = Modifier.align(Alignment.TopCenter),
                        connectDoctor = {
                            setNotificationForDoctor(
                                phoneNumber = it,
                                context = this@DoctorScreen
                            )
                        })
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setNotificationForDoctor(phoneNumber: String, context: Context) {

        if (!isNotificationListenerEnabled()) {
            checkPermissionForNotificationListener()
        }

        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()
        dueDate.set(Calendar.HOUR_OF_DAY, 8)
        dueDate.set(Calendar.MINUTE, 0)
        dueDate.set(Calendar.SECOND, 0)
        if (dueDate.before(currentDate)) dueDate.add(Calendar.HOUR_OF_DAY, 24)

        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiff)

        val periodicWorkerRequest =
            PeriodicWorkRequest.Builder(NotificationWorker::class.java, 24, TimeUnit.HOURS)
                .setInitialDelay(minutes, TimeUnit.MINUTES)
                .addTag("NotificationWorker")
                .setInputData(
                    workDataOf(
                        PHONE_NUM_NOTIFICATION to "Please contact with your doctor. \nPhone: $phoneNumber"
                    )
                )
                .build()

//        WorkManager.getInstance(context).enqueue(periodicWorkerRequest)
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "notificationWorker",
            ExistingPeriodicWorkPolicy.UPDATE,
            periodicWorkerRequest
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (!isNotificationListenerEnabled()) {
                checkPermissionForNotificationListener()
            } else {
                Toast.makeText(this, "Notification access permission allowed", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun isNotificationListenerEnabled(): Boolean {
        val componentName = ComponentName(this, NotificationListener::class.java)
        val enabledListeners =
            Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        return enabledListeners?.contains(componentName.flattenToString()) ?: false
    }

    private fun checkPermissionForNotificationListener() {
        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
        startActivityForResult(intent, REQUEST_NOTIFICATION_PERMISSION)
    }

}