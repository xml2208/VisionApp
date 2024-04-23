package com.xml.visionapp.data.local

import android.content.Context

class PrefManager(val context: Context) {

    private val sharedPrefs = context.getSharedPreferences("LoginFile", Context.MODE_PRIVATE)

    fun saveLoginDetails(firstName: String, lastName: String, password: String) {
        val editor = sharedPrefs.edit()
        editor.apply {
            putString("firstName", firstName)
            putString("lastName", lastName)
            putString("password", password)
        }.apply()
    }
}