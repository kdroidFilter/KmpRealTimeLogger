package com.kdroid.kmplog.client.core.presentation

sealed class MainEvents {
    data class removeUiMessageById(val id: Long) : MainEvents()
    data object openGithubPage : MainEvents()
}

const val GITHUB_PAGE = "https://github.com/kdroidFilter/kmplog"
expect fun openGithubPage()