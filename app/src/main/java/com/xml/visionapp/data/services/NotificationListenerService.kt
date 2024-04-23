package com.xml.visionapp.data.services

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class NotificationListener : NotificationListenerService(), TextToSpeech.OnInitListener {

    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate() {
        super.onCreate()
        textToSpeech = TextToSpeech(this, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        sbn?.let {
            val notificationContent =
                sbn.notification.extras?.getCharSequence("android.text")?.toString()
            notificationContent?.let { content ->
                Log.d("NotificationContent", content)
                speakOut(content)
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        // Handle notification removal if needed
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported")
            }
        } else {
            Log.e("TTS", "Initialization failed")
        }
    }

    private fun speakOut(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

}