package com.kdroid.kmplog.client.features.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kdroid.kmplog.client.core.presentation.MainViewModel
import com.kdroid.kmplog.client.core.presentation.uimessagetoaster.UiMessageToasterState
import com.kdroid.kmplog.core.*

data class HomeState(
    val isConnected : Boolean,
    val logMessages: List<LogMessage>,
    val fontSize : Int,
    val uiMessageToasterState: UiMessageToasterState,
    val isSettingsVisible : Boolean,
    ) {
    companion object {
        val previewState = HomeState(
            isConnected = true,
            fontSize = 14,
            uiMessageToasterState = UiMessageToasterState.preview,
            isSettingsVisible = false,
            logMessages = listOf(
                LogMessage(
                    priority = INFO,
                    timestamp = "2024-10-30 10:05:12.123",
                    tag = "MainActivity",
                    message = "This is an INFO message"
                ),
                LogMessage(
                    priority = WARN,
                    timestamp = "2024-10-30 11:15:45.456",
                    tag = "NetworkClient",
                    message = "This is a WARN message about a slow network"
                ),
                LogMessage(
                    priority = ERROR,
                    timestamp = "2024-10-30 12:30:59.789",
                    tag = "DatabaseHelper",
                    message = "This is an ERROR message: Database connection failed"
                ),
                LogMessage(
                    priority = DEBUG,
                    timestamp = "2024-10-30 13:50:21.987",
                    tag = "SwingButton",
                    message = "This is a DEBUG message"
                ),
                LogMessage(
                    priority = INFO,
                    timestamp = "2024-10-30 14:22:33.654",
                    tag = "UserAuth",
                    message = "User login successful"
                ),
                LogMessage(
                    priority = VERBOSE,
                    timestamp = "2024-10-30 15:45:12.321",
                    tag = "FileManager",
                    message = "This is a VERBOSE message regarding file read operation"
                ),
                LogMessage(
                    priority = INFO,
                    timestamp = "2024-10-30 16:05:10.444",
                    tag = "NotificationService",
                    message = "Push notification sent successfully"
                ),
                LogMessage(
                    priority = WARN,
                    timestamp = "2024-10-30 17:15:55.555",
                    tag = "NetworkClient",
                    message = "This is a WARN message: Server response delayed"
                ),
                LogMessage(
                    priority = DEBUG,
                    timestamp = "2024-10-30 18:30:00.999",
                    tag = "AnalyticsTracker",
                    message = "This is a DEBUG message: Tracking user action"
                ),
                LogMessage(
                    priority = ERROR,
                    timestamp = "2024-10-30 19:00:12.000",
                    tag = "MainActivity",
                    message = "This is an ERROR message: Null pointer exception detected"
                ),
                LogMessage(
                    priority = INFO,
                    timestamp = "2024-10-30 20:45:45.111",
                    tag = "SettingsManager",
                    message = "Settings updated successfully"
                ),
                LogMessage(
                    priority = VERBOSE,
                    timestamp = "2024-10-30 21:15:12.222",
                    tag = "LocationService",
                    message = "This is a VERBOSE message: Location updated"
                ),
                LogMessage(
                    priority = DEBUG,
                    timestamp = "2024-10-30 22:10:38.527",
                    tag = "SwingButton",
                    message = "This is a DEBUG message"
                )
            )
        )

    }
}

@Composable
fun rememberHomeScreenState(
    homeViewModel: HomeViewModel,
    mainViewModel: MainViewModel
): HomeState {
    return HomeState(
        isConnected = mainViewModel.isConnected.collectAsStateWithLifecycle().value,
        fontSize = homeViewModel.fontSize.collectAsState().value,
        logMessages = homeViewModel.logMessages,
        isSettingsVisible = homeViewModel.isSettingsVisible.collectAsStateWithLifecycle().value,
        uiMessageToasterState = mainViewModel.uiMessageToasterState.collectAsStateWithLifecycle().value
    )
}

