package com.kdroid.kmplog.client.domain

interface PreferencesRepository {
    fun saveFontSize(size: Int)
    fun getFontSize(defaultSize: Int = 14): Int
    fun clearFontSize()
}
