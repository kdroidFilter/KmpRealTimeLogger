package com.kdroid.kmplog.client.features.screens.home

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.composables.core.Icon
import com.composables.core.ScrollAreaScope
import com.composables.core.Thumb
import com.composables.core.ThumbVisibility
import com.composables.core.VerticalScrollbar
import com.kdroid.kmplog.client.core.presentation.MainEvents
import com.kdroid.kmplog.client.core.presentation.icons.*
import com.kdroid.kmplog.client.core.presentation.theme.TOP_APP_BAR_HEIGHT
import com.kdroid.kmplog.client.core.presentation.theme.TOP_APP_BAR_TEXT_SIZE
import com.kdroid.kmplog.client.core.presentation.theme.iconColor
import com.kdroid.kmplog.client.kmplog_client.generated.resources.*
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

@Composable
fun appBarBackgroundColor(): Color {
    return if (isSystemInDarkTheme()) Color.DarkGray else Color(0xFFf6f8fa)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun ControlsRow(
    modifier: Modifier,
    onEvent: (HomeEvents) -> Unit,
    onMainEvents: (MainEvents) -> Unit,
    state: HomeState,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = appBarBackgroundColor()
        ),
        windowInsets = WindowInsets(0),
        expandedHeight = TOP_APP_BAR_HEIGHT,
        title = {
            Text(
                text = stringResource(Res.string.app_name),
                fontSize = TOP_APP_BAR_TEXT_SIZE,
                modifier = Modifier.padding(start = 16.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        navigationIcon = {
            ConnexionStatusIcon(state.isConnected)
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

                IconButton(onClick = { onMainEvents(MainEvents.openGithubPage) }) {
                    Icon(imageVector = Github, contentDescription = "GitHub", tint = iconColor())
                }
                IconButton({
                    onEvent(HomeEvents.onSettingsClick)
                }) {
                    Icon(Settings, stringResource(Res.string.settings), tint = iconColor())
                }
            }

        }
    )
}

@Composable
actual fun SettingsWindows(onHomeEvent: (HomeEvents) -> Unit, content: @Composable () -> Unit) {
}