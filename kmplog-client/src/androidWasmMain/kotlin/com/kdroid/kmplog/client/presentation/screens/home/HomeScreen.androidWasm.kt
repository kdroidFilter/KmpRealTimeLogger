package com.kdroid.kmplog.client.presentation.screens.home

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composables.core.*
import com.kdroid.kmplog.client.data.network.WebSocketManager
import com.kdroid.kmplog.client.kmplog_client.generated.resources.*
import com.kdroid.kmplog.client.presentation.icons.*
import com.kdroid.kmplog.client.presentation.theme.iconColor
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration

@Composable
actual fun ScrollAreaScope.Scrollbar(
    modifier: Modifier,
    scrollbarState: LazyListState
) {
    VerticalScrollbar(
        modifier = Modifier.align(Alignment.TopEnd)
            .fillMaxHeight()
            .width(4.dp)
    ) {
        Thumb(
            Modifier.background(if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray),
            thumbVisibility = ThumbVisibility.HideWhileIdle(
                enter = EnterTransition.None,
                exit = ExitTransition.None,
                hideDelay = Duration.ZERO
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun ControlsRow(
    modifier: Modifier,
    onEvent: (HomeEvents) -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(Res.string.app_name)) },
        navigationIcon = {
            ConnexionStatusIcon(WebSocketManager.isConnected.collectAsStateWithLifecycle().value)
        },
        actions = {
            Row {
                IconButton({
                    onEvent(HomeEvents.clearLogs)
                }) {
                    Icon(Clear, stringResource(Res.string.clear_logs), tint = iconColor())
                }
                IconButton({
                    onEvent(HomeEvents.zoomOut)
                }) {
                    Icon(ZoomOut, stringResource(Res.string.zoom_out), tint = iconColor())
                }
                IconButton({
                    onEvent(HomeEvents.zoomIn)
                }) {
                    Icon(ZoomIn, stringResource(Res.string.zoom_in), tint = iconColor())
                }

                Spacer(Modifier.width(16.dp))
                IconButton({
                    onEvent(HomeEvents.onSettingsClick)
                }) {
                    Icon(SettingsGear, stringResource(Res.string.settings), tint = iconColor())
                }
            }

        }
    )
}