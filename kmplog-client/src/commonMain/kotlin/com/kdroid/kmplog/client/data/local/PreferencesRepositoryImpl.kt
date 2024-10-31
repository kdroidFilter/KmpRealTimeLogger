package com.kdroid.kmplog.client.data.local

import com.kdroid.kmplog.client.domain.PreferencesRepository
import com.russhwolf.settings.Settings

class PreferencesRepositoryImpl(private val settings: Settings) : PreferencesRepository {

    // Clé pour la taille de police
    private val fontSizeKey = "fontSize"

    // Sauvegarder la taille de police
    override fun saveFontSize(size: Int) {
        settings.putInt(fontSizeKey, size)
    }

    // Récupérer la taille de police, avec une valeur par défaut si elle n'existe pas
    override fun getFontSize(defaultSize: Int): Int {
        return settings.getInt(fontSizeKey, defaultSize)
    }

    // Supprimer la taille de police si nécessaire
    override fun clearFontSize() {
        settings.remove(fontSizeKey)
    }
}