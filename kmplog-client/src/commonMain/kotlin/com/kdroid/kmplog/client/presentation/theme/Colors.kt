package com.kdroid.kmplog.client.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kdroid.kmplog.core.*

//Log Terminal

val ResetColor = Color(0xFF000000) //
val GrayColor = Color(0xFF888888) // Gris
val BlueColor = Color.Cyan // Bleu
val GreenColor = Color(0xFF00FF00) // Vert
val YellowColor = Color(0xFFFFFF00) // Jaune
val RedColor = Color(0xFFFF0000) // Rouge
val MagentaColor = Color(0xFFFF00FF) // Magenta

@Composable
fun iconColor() = if (isSystemInDarkTheme()) Color.White else Color.Black

@Composable
fun backgroundColor() = if (isSystemInDarkTheme()) Color.Black else Color.White

@Composable
fun getTerminalTextColor(priority: Int): Color {
    return when (priority) {
        VERBOSE -> if (isSystemInDarkTheme()) GrayColor else Color.Gray
        DEBUG -> if (isSystemInDarkTheme()) BlueColor else Color.Blue
        INFO -> if (isSystemInDarkTheme()) GreenColor else Color.Gray
        WARN -> if (isSystemInDarkTheme()) YellowColor else Color(0xFFDBBE1A)
        ERROR -> if (isSystemInDarkTheme()) RedColor else Color.Red
        ASSERT -> if (isSystemInDarkTheme()) MagentaColor else Color.Magenta
        else -> if (isSystemInDarkTheme()) ResetColor else Color.White
    }
}