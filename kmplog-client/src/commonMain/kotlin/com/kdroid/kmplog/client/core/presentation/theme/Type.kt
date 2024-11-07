package com.kdroid.kmplog.client.core.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.kdroid.kmplog.client.kmplog_client.generated.resources.Res
import com.kdroid.kmplog.client.kmplog_client.generated.resources.jetbrains_mono_bold
import org.jetbrains.compose.resources.Font

@Composable
fun JetBrainsMonoFontFamily() = FontFamily(Font(Res.font.jetbrains_mono_bold))

@Composable
fun AppTypography() = Typography(
    displayLarge = TextStyle(fontFamily = JetBrainsMonoFontFamily(), fontSize = 57.sp),
    displayMedium = TextStyle(fontFamily = JetBrainsMonoFontFamily(), fontSize = 45.sp),
    displaySmall = TextStyle(fontFamily = JetBrainsMonoFontFamily(), fontSize = 36.sp),
    headlineLarge = TextStyle(fontFamily = JetBrainsMonoFontFamily(), fontSize = 32.sp),
    headlineMedium = TextStyle(fontFamily = JetBrainsMonoFontFamily(), fontSize = 28.sp),
    headlineSmall = TextStyle(fontFamily = JetBrainsMonoFontFamily(), fontSize = 24.sp),
    titleLarge = TextStyle(fontFamily = JetBrainsMonoFontFamily(), fontSize = 22.sp),
    titleMedium = TextStyle(fontFamily = JetBrainsMonoFontFamily(), fontSize = 16.sp),
    titleSmall = TextStyle(fontFamily = JetBrainsMonoFontFamily(), fontSize = 14.sp),
    bodyLarge = TextStyle(fontFamily = JetBrainsMonoFontFamily(), fontSize = 16.sp),
    bodyMedium = TextStyle(fontFamily = JetBrainsMonoFontFamily(), fontSize = 14.sp),
    bodySmall = TextStyle(fontFamily = JetBrainsMonoFontFamily(), fontSize = 12.sp),
    labelLarge = TextStyle(fontFamily = JetBrainsMonoFontFamily(), fontSize = 14.sp),
    labelMedium = TextStyle(fontFamily = JetBrainsMonoFontFamily(), fontSize = 12.sp),
    labelSmall = TextStyle(fontFamily = JetBrainsMonoFontFamily(), fontSize = 11.sp)
)
