package com.kdroid.kmplog.client.presentation.uimessagetoaster

import com.dokar.sonner.Toast

actual val showCloseButton: Boolean
    get() = true

actual fun errorOnDissmissAction(onDismiss: (toast: Toast) -> Unit): Any? = null