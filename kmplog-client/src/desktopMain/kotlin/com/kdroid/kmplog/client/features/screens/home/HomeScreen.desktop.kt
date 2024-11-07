package com.kdroid.kmplog.client.features.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.DialogWindow
import com.composables.core.ScrollAreaScope
import com.kdroid.kmplog.client.core.presentation.MainEvents
import com.kdroid.kmplog.client.core.presentation.icons.Clear
import com.kdroid.kmplog.client.core.presentation.icons.Settings
import com.kdroid.kmplog.client.core.presentation.icons.ZoomIn
import com.kdroid.kmplog.client.core.presentation.icons.ZoomOut
import com.kdroid.kmplog.client.core.presentation.theme.iconColor
import com.kdroid.kmplog.client.kmplog_client.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.*
import org.jetbrains.jewel.ui.theme.scrollbarStyle

@androidx.compose.desktop.ui.tooling.preview.Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(homeState = HomeState.previewState, onEvent = {}, {})
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
actual fun ControlsRow(
    modifier: Modifier,
    onEvent: (HomeEvents) -> Unit,
    onMainEvents: (MainEvents) -> Unit,
    state: HomeState,
    ) {
    Row(
        modifier = modifier
            .background(JewelTheme.globalColors.panelBackground)
            .padding(8.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Tooltip({ Text(stringResource(Res.string.zoom_in)) }) {
                IconButton({
                    onEvent(HomeEvents.zoomIn)
                }) {
                    Icon(ZoomIn, stringResource(Res.string.zoom_in), tint = iconColor())
                }
            }
            Tooltip({ Text(stringResource(Res.string.zoom_out)) }) {
                IconButton({
                    onEvent(HomeEvents.zoomOut)
                }) {
                    Icon(ZoomOut, stringResource(Res.string.zoom_out), tint = iconColor())
                }
            }
        }
        Row {
            Tooltip({ Text(stringResource(Res.string.clear_logs)) }) {
                IconButton({
                    onEvent(HomeEvents.clearLogs)
                }) {
                    Icon(Clear, stringResource(Res.string.clear_logs), tint = iconColor())
                }
            }

            Spacer(Modifier.width(16.dp))

            Tooltip({ Text(stringResource(Res.string.settings)) }) {
                IconButton({
                    onEvent(HomeEvents.onSettingsClick)
                }) {
                    Icon(Settings, stringResource(Res.string.settings), tint = iconColor())
                }
            }
        }
    }
    Divider(orientation = Orientation.Horizontal)
}


@Composable
actual fun ScrollAreaScope.Scrollbar(
    modifier: Modifier,
    scrollbarState: LazyListState
) {
    VerticalScrollbar(
        modifier = modifier,
        scrollState = scrollbarState,
        style = JewelTheme.scrollbarStyle
    )
}

@Composable
actual fun SettingsWindows(
    onHomeEvent: (HomeEvents) -> Unit,
    content: @Composable () -> Unit
) {
    DialogWindow(
        onCloseRequest = {
            onHomeEvent(HomeEvents.OnCloseSettings)
        },
        title = stringResource(Res.string.settings),
        state = DialogState(width = 400.dp, height = 400.dp),
        content = {
            content()
        },
    )
}