package com.kdroid.kmplog.client.presentation.uimessagetoaster

import com.dokar.sonner.TextToastAction
import com.dokar.sonner.Toast

actual val showCloseButton: Boolean
    get() = false

actual fun errorOnDissmissAction(onDismiss: (toast: Toast) -> Unit): Any? = TextToastAction(text = "Dismiss", onClick = onDismiss)