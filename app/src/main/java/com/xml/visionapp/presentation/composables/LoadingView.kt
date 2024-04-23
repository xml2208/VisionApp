package com.xml.visionapp.presentation.composables

import android.view.View
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun LoadingView(view: View) {
    AndroidView(
        modifier = Modifier
            .size(10.dp, 100.dp)
            .padding(horizontal = 36.dp),
        factory = { view }
    )
}