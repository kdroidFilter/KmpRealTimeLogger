package com.kdroid.kmplog.client.features.screens.windowstitlebar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kdroid.kmplog.client.core.presentation.MainEvents
import com.kdroid.kmplog.client.core.presentation.MainViewModel
import com.kdroid.kmplog.client.core.presentation.icons.ConnexionStatusIcon
import com.kdroid.kmplog.client.core.presentation.icons.Github
import com.kdroid.kmplog.client.core.presentation.theme.iconColor
import com.kdroid.kmplog.client.kmplog_client.generated.resources.Res
import com.kdroid.kmplog.client.kmplog_client.generated.resources.open_github_page
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.IconButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.Tooltip
import org.jetbrains.jewel.window.DecoratedWindowScope
import org.jetbrains.jewel.window.TitleBar
import org.jetbrains.jewel.window.newFullscreenControls
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DecoratedWindowScope.WindowTitleBar() {
    val mainViewModel: MainViewModel = koinViewModel()
    val state = rememberWindowTitleBarState(mainViewModel)
    WindowTitleBarComponent(state, onEvent = mainViewModel::onEvent)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DecoratedWindowScope.WindowTitleBarComponent(state: WindowTitleBarState, onEvent : (MainEvents) -> Unit) {
    TitleBar() {
        Row(
            modifier = Modifier.newFullscreenControls()
                .padding(start = 8.dp)
                .align(Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(Modifier.align(Alignment.Start)) {
                ConnexionStatusIcon(state.isConnected)

                Text(text = title, modifier = Modifier.padding(start = 8.dp))
            }
            Row(Modifier.padding(start = 32.dp).align(Alignment.End)) {

                Tooltip({ Text(stringResource(Res.string.open_github_page)) }) {
                    IconButton(onClick = { onEvent(MainEvents.openGithubPage) }) {
                        Icon(imageVector = Github, contentDescription = "GitHub", tint = iconColor())
                    }
                }
            }

        }

    }
}



