package com.xml.visionapp.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.xml.visionapp.databinding.LogInScreenBinding
import com.xml.visionapp.presentation.MainActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LogInScreenActivity : ComponentActivity() {

    private lateinit var viewBinding: LogInScreenBinding
    private val viewModel: LoginViewModel by viewModels()
    private val shp by lazy { getSharedPreferences("LoginFile", Context.MODE_PRIVATE) }

    override fun onStart() {
        super.onStart()
        if (shp.contains("firstName") && shp.contains("lastName") && shp.contains("password")) {
            startActivity(Intent(this, MainActivity::class.java))
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = LogInScreenBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.loginButton.setOnClickListener {

            if (viewBinding.checkBox.isChecked) {
                logInUser()
            } else {
                Toast.makeText(this, "You must agree Terms & Conditions!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun logInUser() {
        val firstName = viewBinding.firstName.text.toString()
        val lastName = viewBinding.lastName.text.toString()
        val password = viewBinding.password.text.toString()

        viewModel.logInUser(firstName, lastName, password)

        lifecycleScope.launch {
            viewModel.result.collectLatest { isSuccessful ->
                if (isSuccessful) {
                    startActivity(Intent(this@LogInScreenActivity, MainActivity::class.java))
                }
            }
        }
    }
}