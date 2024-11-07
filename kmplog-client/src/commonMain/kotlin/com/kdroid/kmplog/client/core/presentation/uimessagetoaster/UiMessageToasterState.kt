package com.kdroid.kmplog.client.core.presentation.uimessagetoaster

import com.kdroid.kmplog.client.core.domain.UiMessageToaster

data class UiMessageToasterState(
    val isLoading: Boolean = false,
    val uiMessageToasters: List<UiMessageToaster> = emptyList(),
) {
    companion object {
        val preview = UiMessageToasterState(
            isLoading = false,
            uiMessageToasters = listOf(
                UiMessageToaster.Error(
                    id = 1,
                    message = "Error message"
                ),
                UiMessageToaster.Success(
                    id = 2,
                    message = "Success message"
                )
            )
        )
    }
}