package com.kdroid.kmplog.client.presentation.uimessagetoaster

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import com.dokar.sonner.*
import com.kdroid.kmplog.client.domain.UiMessageToaster
import com.kdroid.kmplog.client.presentation.theme.isSystemInDarkTheme


@Composable
fun UiMessageToaster(
    messages: List<UiMessageToaster>,
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
        richColors = false,
        showCloseButton = showCloseButton,
        darkTheme = isSystemInDarkTheme()
    )
}

expect val showCloseButton: Boolean

private fun UiMessageToaster.toToast(onDismiss: (toast: Toast) -> Unit): Toast = when (this) {
    is UiMessageToaster.Error -> Toast(
        id = id,
        message = message,
        type = ToastType.Error,
        action = errorOnDissmissAction { onDismiss(it) },
    )

    is UiMessageToaster.Success -> Toast(
        id = id,
        message = message,
        type = ToastType.Success,
    )
}

expect fun errorOnDissmissAction(onDismiss: (toast: Toast) -> Unit) : Any?
