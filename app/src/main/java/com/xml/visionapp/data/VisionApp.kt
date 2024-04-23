package com.xml.visionapp.data

import android.app.Application
import com.google.firebase.FirebaseApp
import com.xml.visionapp.data.local.VisionAppRepository

class VisionApp: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        VisionAppRepository.initialize(this)
    }

}