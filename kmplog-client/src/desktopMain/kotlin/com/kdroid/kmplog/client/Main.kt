package com.kdroid.kmplog.client

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.window.application
import com.kdroid.kmplog.client.com.kdroid.kmplog.client.presentation.windows.titlebar.AppTitleBar
import com.kdroid.kmplog.client.framework.di.initKoin
import com.kdroid.kmplog.client.kmplog_client.generated.resources.Res
import com.kdroid.kmplog.client.kmplog_client.generated.resources.app_name
import com.kdroid.kmplog.client.presentation.theme.isSystemInDarkTheme
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
import org.jetbrains.jewel.window.DecoratedWindow
import org.jetbrains.jewel.window.styling.TitleBarStyle

fun main() =
    application {
        initKoin()
        IntUiTheme(
            theme = if (isSystemInDarkTheme()) JewelTheme.darkThemeDefinition() else JewelTheme.lightThemeDefinition(),
            styling = ComponentStyling.default()
                .decoratedWindow(titleBarStyle = if (isSystemInDarkTheme()) TitleBarStyle.dark() else TitleBarStyle.lightWithLightHeader()),
            swingCompatMode = true

        ) {

            DecoratedWindow(
                onCloseRequest = { exitApplication() },
                title = stringResource(Res.string.app_name),
                content = {
                    AppTitleBar()
                    CompositionLocalProvider(LocalDensity provides Density(density = 0.75f, fontScale = 1.0f)) {
                        App()
                    }
                },
            )
        }
    }


