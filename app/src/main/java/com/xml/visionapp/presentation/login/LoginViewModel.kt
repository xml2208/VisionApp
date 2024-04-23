package com.xml.visionapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xml.visionapp.data.local.VisionAppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val visionAppRepository by lazy { VisionAppRepository.get() }

    private var _result = MutableStateFlow(false)
    val result = _result.asStateFlow()

    fun logInUser(firstName: String, lastName: String, password: String) {
        viewModelScope.launch {
            _result.value = visionAppRepository.logInUser(firstName, lastName, password)
        }
    }
}