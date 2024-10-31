package com.kdroid.kmplog.client.presentation.toast

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import com.dokar.sonner.*
import com.kdroid.kmplog.client.domain.UiMessage
import com.kdroid.kmplog.client.presentation.theme.isSystemInDarkTheme
import kotlin.time.Duration

 data class UiState(
    val isLoading: Boolean = false,
    val uiMessages: List<UiMessage> = emptyList(),
) {
     companion object {
         val preview = UiState(
             isLoading = false,
             uiMessages = listOf(
                 UiMessage.Error(
                     id = 1,
                     message = "Error message"
                 ),
                 UiMessage.Success(
                     id = 2,
                     message = "Success message"
                 )
             )
         )
     }
 }

@Composable
fun UiMessageToaster(
    messages: List<UiMessage>,
    onRemoveMessage: (id: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val toaster = rememberToasterState(
        onToastDismissed = { onRemoveMessage(it.id as Long) },
    )

    val currentMessages by rememberUpdatedState(messages)

    LaunchedEffect(toaster) {
        // Listen to State<List<UiMessage>> changes and map to toasts
        toaster.listenMany {
            currentMessages.map { message ->
                message.toToast(onDismiss = { onRemoveMessage(message.id) })
            }
        }
    }

    Toaster(
        state = toaster,
        modifier = modifier,
        richColors = true,
        showCloseButton = true,
        darkTheme = isSystemInDarkTheme()
    )
}

private fun UiMessage.toToast(onDismiss: (toast: Toast) -> Unit): Toast = when (this) {
    is UiMessage.Error -> Toast(
        id = id,
        message = message,
        type = ToastType.Error,
        duration = Duration.INFINITE,
        action = TextToastAction(text = "Dismiss", onClick = onDismiss),
    )

    is UiMessage.Success -> Toast(
        id = id,
        message = message,
        type = ToastType.Success,
        action = TextToastAction(text = "Dismiss", onClick = onDismiss),
    )
}

