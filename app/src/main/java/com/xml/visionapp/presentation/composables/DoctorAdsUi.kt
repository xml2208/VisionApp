package com.xml.visionapp.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xml.visionapp.R
import com.xml.visionapp.ui.theme.IconsBlue
import com.xml.visionapp.ui.theme.NewBackground
import com.xml.visionapp.ui.theme.statusBarBlue

@Composable
fun DoctorMainUi(
    modifier: Modifier,
    connectDoctor: (name: String) -> Unit
) {
    Column(
        modifier = modifier.padding(vertical = 15.dp, horizontal = 65.dp),
        verticalArrangement = Arrangement.spacedBy(7.dp),
    ) {

        DoctorIcons(modifier = Modifier)

        Row(
            modifier = Modifier
                .background(shape = RoundedCornerShape(12.dp), color = NewBackground)
                .padding(5.dp)
                .fillMaxWidth()

        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Doctor Image",
                tint = IconsBlue
            )
            Text(
                text = "Raximova Matluba Eshbayevna",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
        }
        Row(
            modifier = Modifier
                .background(shape = RoundedCornerShape(12.dp), color = NewBackground)
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = IconsBlue
            )
            Text(
                text = "Candidate of Medical Sciences, Therapist",
                fontWeight = FontWeight.Bold,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp,
                color = Color.White
            )
        }
        Row(
            modifier = Modifier
                .background(shape = RoundedCornerShape(12.dp), color = NewBackground)
                .padding(5.dp)
                .fillMaxWidth(),
        ) {
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = IconsBlue
            )
            Text(
                text = "Regional multidisciplinary clinic",
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        }

        Row(
            modifier = Modifier
                .background(shape = RoundedCornerShape(12.dp), color = NewBackground)
                .padding(5.dp)
                .fillMaxWidth()

        ) {
            Icon(
                Icons.Default.LocationOn,
                contentDescription = null,
                tint = IconsBlue
            )
            Text(
                text = "Andijon, st. Lermontov, 51",
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        }
        Row(
            modifier = Modifier
                .background(shape = RoundedCornerShape(12.dp), color = NewBackground)
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Icon(
                Icons.Default.Phone,
                contentDescription = null,
                tint = IconsBlue
            )
            Text(
                text = "+998-74-500-22-22",
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        }
        Button(
            modifier = Modifier
                .align(CenterHorizontally)
                .clip(CircleShape),
            onClick = { connectDoctor("+998745002222") },
            border = BorderStroke(2.dp, IconsBlue),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            content = {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = "Set Reminder")
                Text(
                    modifier = Modifier,
                    text = "Set a reminder",
                    color = Color.White,
                )
            })
    }
}

@Composable
private fun DoctorIcons(modifier: Modifier) {
    Row(modifier = modifier.padding(top = 20.dp, start = 100.dp)) {
        Image(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(32.dp)),
            painter = painterResource(id = R.drawable.woman),
            contentDescription = "Doctor Image"
        )
        Image(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(32.dp)),
            painter = painterResource(id = R.drawable.clinic_img),
            contentScale = ContentScale.Crop,
            contentDescription = "Clinic Image",
        )

    }
}

@Preview
@Composable
fun DoctorMainUiPrev() {
    DoctorMainUi(modifier = Modifier.background(color = statusBarBlue), connectDoctor = {})
}