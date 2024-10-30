package com.kdroid.kmplog.client.presentation.theme

import androidx.compose.ui.graphics.Color
import com.kdroid.kmplog.core.*

//Compose Colors

val ResetColor = Color(0xFF000000) //
val GrayColor = Color(0xFF888888) // Gris
val BlueColor = Color.Cyan // Bleu
val GreenColor = Color(0xFF00FF00) // Vert
val YellowColor = Color(0xFFFFFF00) // Jaune
val RedColor = Color(0xFFFF0000) // Rouge
val MagentaColor = Color(0xFFFF00FF) // Magenta

fun getLightColor(priority: Int): Color {
    return when (priority) {
        VERBOSE -> Color.Gray
        DEBUG -> Color.Blue
        INFO -> Color.Gray
        WARN -> Color.Yellow
        ERROR -> Color.Red
        ASSERT -> Color.Magenta
        else -> Color.White
    }
}

fun getDarkColor(priority: Int): Color {
    return when (priority) {
        VERBOSE -> GrayColor
        DEBUG -> BlueColor
        INFO -> GreenColor
        WARN -> YellowColor
        ERROR -> RedColor
        ASSERT -> MagentaColor
        else -> ResetColor
    }
}