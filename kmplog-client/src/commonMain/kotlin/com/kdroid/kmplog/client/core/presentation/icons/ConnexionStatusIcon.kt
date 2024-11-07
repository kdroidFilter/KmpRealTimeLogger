package com.kdroid.kmplog.client.core.presentation.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ConnexionStatusIcon(isConnected : Boolean) {
    Box(
        modifier = Modifier
            .size(14.dp)
            .background(
                color = if (isConnected) Color.Green else Color.Red,
                shape = CircleShape
            )
    )
}