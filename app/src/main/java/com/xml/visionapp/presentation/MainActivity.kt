package com.xml.visionapp.presentation

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.xml.visionapp.databinding.ProgressViewBinding
import com.xml.visionapp.presentation.composables.MainAppUi
import com.xml.visionapp.ui.theme.VisionAppTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.gotev.speech.Speech
import java.util.Locale

private const val LOCATION_PERMISSION_REQUEST_CODE = 1
private const val MICROPHONE_PERMISSION_REQUEST_CODE = 2

class MainActivity : ComponentActivity() {

    private val viewModel: VisionAppViewModel by viewModels()
    private var phoneNumber by mutableStateOf("")
    private lateinit var smsVoiceCommandHandler: VoiceCommandHandler
    private lateinit var callCommandHandler: VoiceCommandHandler
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var pView: ProgressViewBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Speech.init(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        callCommandHandler = VoiceCommandHandler(
            doSomeThingWithRecognisedWord = { call(phoneNumber) }
        )
        smsVoiceCommandHandler = VoiceCommandHandler(
            doSomeThingWithRecognisedWord = { textSomeone(phoneNumber) }
        )

        pView = ProgressViewBinding.inflate(layoutInflater)

        lifecycleScope.launch {
            callCommandHandler.recognisedText.collectLatest {
                phoneNumber = it
            }
        }
        lifecycleScope.launch {
            smsVoiceCommandHandler.recognisedText.collectLatest {
                phoneNumber = it
            }
        }

        setContent {
            VisionAppTheme {

                when (val state = viewModel.state.collectAsState().value) {
                    is VisionState.BatteryLoadingState -> {
                        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                    }

                    is VisionState.BatterySuccessState -> {
                        MainAppUi(
                            modifier = Modifier,
                            call = ::startListeningPhoneForCall,
                            text = ::startListeningPhoneForSms,
                            recognisedText = phoneNumber,
                            onDoctorIconsClick = ::openDoctorPage,
                            onMapClicked = ::openMap,
                            battery = state.battery.toInt()
                        )
                        viewModel.setReadBatteryAloud(this, state.battery)

                    }

                    is VisionState.BatteryErrorState -> {
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }

            }
        }
    }

    private fun startListeningPhoneForCall() {
        Log.d("ddk9499", "startListeningPhoneForCall: ")

        checkPermissionForMicrophone() {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    MICROPHONE_PERMISSION_REQUEST_CODE
                )
            } else {
                Speech.getInstance().startListening(pView.progress, callCommandHandler)
            }
        }
    }

    private fun startListeningPhoneForSms() {
        checkPermissionForMicrophone {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.SEND_SMS),
                    MICROPHONE_PERMISSION_REQUEST_CODE
                )
            } else {
                Speech.getInstance().startListening(pView.progress, smsVoiceCommandHandler)
            }
        }
    }

    private fun call(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:+$phoneNumber")
        startActivity(callIntent)
    }

    private fun openDoctorPage() {
        val intent = Intent(this, DoctorScreen::class.java)
        startActivity(intent)
    }

    private fun textSomeone(phoneNumber: String) {
        try {
            val smsManager =
                if (Build.VERSION.SDK_INT >= 28) {
                    SmsManager.getDefault()
                } else this.getSystemService(
                    SmsManager::class.java
                )

            smsManager.sendTextMessage(
                phoneNumber,
                null,
                "Where are you?",
                null,
                null
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun openMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        val currentLatitude = it.latitude
                        val currentLongitude = it.longitude
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val addresses =
                            geocoder.getFromLocation(currentLatitude, currentLongitude, 1)
                        val currentAddress = addresses?.firstOrNull()?.getAddressLine(0)
                        val destination = "40.750099, 72.346944"
                        val uri =
                            Uri.parse("https://www.google.com/maps/dir/?api=1&origin=$currentAddress&destination=$destination")
                        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
                        mapIntent.setPackage("com.google.android.apps.maps")

                        if (mapIntent.resolveActivity(packageManager) != null) {
                            startActivity(mapIntent)
                        } else {

                        }
                    } ?: kotlin.run {
                        Toast.makeText(this, "unable to get location", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun checkPermissionForMicrophone(startListening: () -> Unit) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                MICROPHONE_PERMISSION_REQUEST_CODE
            )
        } else startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        Speech.getInstance().shutdown()
    }

}
