package com.kdroid.kmplog.client.core.data.local.repository

import com.kdroid.kmplog.client.core.domain.repository.HomePreferencesRepository
import com.russhwolf.settings.Settings

class HomePreferencesRepositoryImpl(private val settings: Settings) : HomePreferencesRepository {

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