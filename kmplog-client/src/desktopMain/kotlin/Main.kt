package com.kdroid.kmplog.client

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import com.kdroid.kmplog.client.kmplog_client.generated.resources.Res
import com.kdroid.kmplog.client.kmplog_client.generated.resources.app_name
import com.kdroid.kmplog.client.presentation.theme.SystemTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.jetbrains.jewel.intui.standalone.theme.darkThemeDefinition
import org.jetbrains.jewel.intui.standalone.theme.default
import org.jetbrains.jewel.intui.standalone.theme.lightThemeDefinition
import org.jetbrains.jewel.intui.window.decoratedWindow
import org.jetbrains.jewel.intui.window.styling.dark
import org.jetbrains.jewel.intui.window.styling.lightWithLightHeader
import org.jetbrains.jewel.ui.ComponentStyling
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.window.DecoratedWindow
import org.jetbrains.jewel.window.TitleBar
import org.jetbrains.jewel.window.styling.TitleBarStyle

@OptIn(ExperimentalResourceApi::class)
fun main() = application {

    var isDarkTheme by mutableStateOf(SystemTheme.isSystemeDarkTheme)
    SystemTheme.registerListener { isDarkTheme = it }

    IntUiTheme(
        theme = if (isDarkTheme) JewelTheme.darkThemeDefinition() else JewelTheme.lightThemeDefinition(),
        styling = ComponentStyling.default().decoratedWindow(titleBarStyle = if (isDarkTheme ) TitleBarStyle.dark() else TitleBarStyle.lightWithLightHeader()),
    ) {
        DecoratedWindow(
            onCloseRequest = { exitApplication() },
            title = stringResource(Res.string.app_name),
            content = {
                TitleBar() {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(modifier = Modifier.padding(start = 8.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(
                                        color = if (true) Color.Green else Color.Red,
                                        shape = CircleShape
                                    )
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(start = 64.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = title,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }

                }
                CompositionLocalProvider(LocalDensity provides Density(density = 0.75f, fontScale = 1.0f)) {
                    App()
                }
            },
        )
    }
}

