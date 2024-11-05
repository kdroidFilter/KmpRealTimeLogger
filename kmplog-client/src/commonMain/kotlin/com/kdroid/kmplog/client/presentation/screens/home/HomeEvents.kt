package com.kdroid.kmplog.client.presentation.screens.home

sealed class HomeEvents {
    data object zoomIn : HomeEvents()
    data object zoomOut : HomeEvents()
    data object onResetZoom : HomeEvents()
    data class onSearch(val searchText: String) : HomeEvents()
    data class onSearchClear(val searchText: String) : HomeEvents()
    data object clearLogs : HomeEvents()

    data object onSettingsClick : HomeEvents()
    data object OnCloseSettings : HomeEvents()
    data class removeUiMessageById(val id: Long) : HomeEvents()
}