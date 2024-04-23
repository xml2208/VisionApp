package com.xml.visionapp.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xml.visionapp.R
import com.xml.visionapp.ui.theme.IconsBlue
import com.xml.visionapp.ui.theme.PURPLE_Button_Background

@Composable
fun VisionDeviceBatteryUi(
    modifier: Modifier,
    battery: Int,
) {
    Row(
        modifier = modifier
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SetBatteryIcon(battery, Modifier)
        Column(
            modifier = Modifier, verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = "BATTERY:",
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "$battery%",
                fontSize = 25.sp,
                color = PURPLE_Button_Background,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
private fun SetBatteryIcon(
    battery: Int,
    modifier: Modifier,
) {
    if (battery in 76..100) {
        Icon(
            modifier = modifier.size(65.dp),
            painter = painterResource(id = R.drawable.battery_100),
            tint = IconsBlue,
            contentDescription = "Battery is 100 percent"
        )
    } else
        if (battery in 50..75) {
            Icon(
                modifier = modifier.size(65.dp),
                tint = IconsBlue,
                painter = painterResource(id = R.drawable.battery_75),
                contentDescription = "Battery is 75 percent"
            )
        } else
            if (battery in 25..49) {
                Icon(
                    modifier = modifier.size(65.dp),
                    tint = IconsBlue,
                    painter = painterResource(id = R.drawable.battery_50),
                    contentDescription = "Battery is 50 percent"
                )
            } else
                if (battery in 0..24) {
                    Icon(
                        tint = IconsBlue,
                        modifier = modifier.size(65.dp),
                        painter = painterResource(id = R.drawable.battery_25),
                        contentDescription = "Battery is 25 percent"
                    )
                }
}