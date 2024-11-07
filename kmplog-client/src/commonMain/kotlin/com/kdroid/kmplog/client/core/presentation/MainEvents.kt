package com.kdroid.kmplog.client.core.presentation

sealed class MainEvents {
    data class removeUiMessageById(val id: Long) : MainEvents()

}