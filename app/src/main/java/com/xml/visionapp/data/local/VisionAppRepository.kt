package com.xml.visionapp.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.xml.visionapp.data.model.User
import kotlinx.coroutines.tasks.await

class VisionAppRepository(
    private val context: Context
) {
    private val usersCollection = FirebaseFirestore.getInstance().collection("Users")
    private val prefManager = PrefManager(context)

    suspend fun logInUser(firstName: String, lastName: String, password: String): Boolean {
        val user = User(firstName, lastName, password)
        try {
            val userCollection = usersCollection.get(Source.SERVER).await()
            userCollection.documents.forEach {
                if (it["firstName"] == user.firstName &&
                    it["lastName"] == user.lastName &&
                    it["password"] == user.password
                ) {
                    prefManager.saveLoginDetails(firstName, lastName, password)
                    return true
                } else {
                    Toast.makeText(context, "Incorrect data!", Toast.LENGTH_SHORT).show()
                }
            }
            return false
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Network error!!!", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: VisionAppRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = VisionAppRepository(context)
            }
        }

        fun get(): VisionAppRepository {
            return INSTANCE
                ?: throw IllegalStateException("SkladRepository must be initialized!")
        }
    }
}