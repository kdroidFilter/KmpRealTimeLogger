package com.kdroid.kmplog.client.com.kdroid.kmplog.client.presentation.windows.titlebar

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kdroid.kmplog.client.presentation.icons.Clear
import com.kdroid.kmplog.client.presentation.icons.ZoomIn
import com.kdroid.kmplog.client.presentation.icons.ZoomOut
import com.kdroid.kmplog.client.presentation.screens.home.HomeEvents
import com.kdroid.kmplog.client.presentation.screens.home.HomeState
import com.kdroid.kmplog.client.presentation.screens.home.HomeViewModel
import com.kdroid.kmplog.client.presentation.screens.home.rememberHomeScreenState
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.IconButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.window.DecoratedWindowScope
import org.jetbrains.jewel.window.TitleBar
import org.koin.compose.koinInject


@Composable
fun DecoratedWindowScope.AppTitleBar() {
    val vm = koinInject<HomeViewModel>()
    AppTitleBarComponent(
        state = rememberHomeScreenState(vm = vm), onEvent = { event -> vm.onEvent(event) },
    )
}

@Composable
fun DecoratedWindowScope.AppTitleBarComponent(
    state: HomeState,
    onEvent: (HomeEvents) -> Unit
) {
    TitleBar() {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.padding(start = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(
                            color = if (state.connectionStatus) Color.Green else Color.Red,
                            shape = CircleShape
                        )
                )
                IconButton(modifier = Modifier.padding(start = 8.dp), onClick = {
                        onEvent(HomeEvents.onZoomIn)
                }) {
                    Icon(ZoomIn, "", tint = if (isSystemInDarkTheme() )Color.White else Color.Black)
                }
                IconButton({
                    onEvent(HomeEvents.onZoomOut)
                }) {
                    Icon(ZoomOut, "")
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 64.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = title,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                IconButton({
                    onEvent(HomeEvents.onClearLogs)
                }) {
                    Icon(Clear, "")
                }

            }
        }

    }
}


