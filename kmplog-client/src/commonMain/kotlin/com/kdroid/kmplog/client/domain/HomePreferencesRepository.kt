package com.kdroid.kmplog.client.domain

interface HomePreferencesRepository {
    fun saveFontSize(size: Int)
    fun getFontSize(defaultSize: Int = 14): Int
    fun clearFontSize()
}
