package com.xml.visionapp.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.xml.visionapp.data.services.BATTERY_DATA
import com.xml.visionapp.data.services.BatteryReaderWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class VisionAppViewModel : ViewModel() {

    private val _state = MutableStateFlow<VisionState>(VisionState.BatteryLoadingState)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch { getBattery() }
    }

    private suspend fun getBattery() {

        try {
            /*
            * Server for getting battery percentage our Vision Device may not work properly in your device(location) so
            * you can check it with hardcode "75%"(
            *
            */
//            val get = VisionApi.visionApiService.getBatteryPercentage()
//            _state.value = VisionState.BatterySuccessState(get.battery)
            _state.value = VisionState.BatterySuccessState("75")
        } catch (e: Exception) {
            e.printStackTrace()
//            _state.value = VisionState.BatteryErrorState
        }
    }

    fun setReadBatteryAloud(context: Context, battery: String) {
        val workerRequest = OneTimeWorkRequestBuilder<BatteryReaderWorker>()
            .setInitialDelay(1000L, TimeUnit.MILLISECONDS)
            .setInputData(workDataOf(BATTERY_DATA to battery))
            .build()

        WorkManager.getInstance(context).enqueue(workerRequest)
    }

}

sealed class VisionState {

    object BatteryLoadingState : VisionState()

    data class BatterySuccessState(val battery: String) : VisionState()

    object BatteryErrorState : VisionState()

}