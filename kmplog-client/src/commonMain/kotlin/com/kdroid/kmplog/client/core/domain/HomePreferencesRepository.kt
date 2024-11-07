package com.kdroid.kmplog.client.core.domain

interface HomePreferencesRepository {
    fun saveFontSize(size: Int)
    fun getFontSize(defaultSize: Int = 14): Int
    fun clearFontSize()
}
