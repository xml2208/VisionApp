package com.xml.visionapp.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.xml.visionapp.R
import com.xml.visionapp.databinding.ProgressViewBinding
import com.xml.visionapp.ui.theme.IconsBlue

@Composable
fun MainAppUi(
    modifier: Modifier,
    call: () -> Unit,
    text: () -> Unit,
    recognisedText: String,
    onMapClicked: () -> Unit,
    battery: Int,
    onDoctorIconsClick: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.main_bcg),
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
            IconsBlock(
                modifier = modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp, top = 26.dp),
                onClick = onDoctorIconsClick
            )
            VisionDeviceBatteryUi(
                modifier = Modifier
                    .padding(top = 80.dp)
                    .align(Alignment.CenterEnd), battery = battery
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(modifier = Modifier
                    .size(100.dp)
                    .weight(1f), onClick = { call() }) {
                    Icon(
                        modifier = Modifier.size(100.dp),
                        painter = painterResource(id = R.drawable.call),
                        contentDescription = "Call",
                        tint = IconsBlue,
                    )
                }
                IconButton(
                    onClick = { text() },
                    modifier = Modifier
                        .size(60.dp)
                        .weight(1f),
                ) {
                    Icon(
                        modifier = Modifier.size(60.dp),
                        painter = painterResource(id = R.drawable.ic_sms),
                        contentDescription = "SMS",
                        tint = IconsBlue,
                    )
                }
                IconButton(
                    onClick = { onMapClicked() },
                    modifier = Modifier
                        .size(60.dp)
                        .weight(1f),
                ) {
                    Icon(
                        modifier = Modifier.size(60.dp),
                        imageVector = Icons.Default.LocationOn,
                        tint = IconsBlue,
                        contentDescription = "Direction to your doctor",
                    )
                }
            }

            Box(modifier = modifier
                .padding(bottom = 100.dp)
                .align(Alignment.BottomCenter),
                content = {
                    if (recognisedText != "") {
                        Text(
                            modifier = modifier,
                            text = recognisedText,
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold
                        )
                    } else AndroidViewBinding(
                        factory = ProgressViewBinding::inflate,
                        modifier = modifier,
                    )
                })
        }
    }
}

@Composable
fun IconsBlock(
    modifier: Modifier, onClick: () -> Unit
) {
    Row(modifier = modifier.padding(30.dp).clickable { onClick() }) {
        Image(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.doctor_ic),
            contentScale = ContentScale.Crop,
            contentDescription = "Clinic Image",
        )
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .size(100.dp),
            painter = painterResource(id = R.drawable.ic_hospital),
            contentDescription = "Doctor Image"
        )
    }
}
