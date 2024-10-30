package com.kdroid.kmplog.client.presentation.screens.home

sealed class HomeEvents {
    data object onZoomIn : HomeEvents()
    data object onZoomOut : HomeEvents()
    data object onResetZoom : HomeEvents()
    data class onSearch(val searchText: String) : HomeEvents()
    data class onSearchClear(val searchText: String) : HomeEvents()
    data object onClearLogs : HomeEvents()

    data object onSettingsClick : HomeEvents()
}