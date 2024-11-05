package com.kdroid.kmplog

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log

class ContextInitProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        // Obtenir le contexte
        val context = context ?: return false

        // Initialiser la bibliothèque KMPLog ici
        ContextProvider.initialize(context)

        Log.d("KMPLogInitProvider", "KMPLog initialized")
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}

object ContextProvider {
    private var isInitialized = false
    private lateinit var appContext: Context

    fun initialize(context: Context) {
        if (isInitialized) {
            return
        }

        // Stocker l'application context pour éviter les fuites de mémoire
        appContext = context.applicationContext

        // Effectuer des initialisations nécessaires ici
        Log.d("KMPLog", "KMPLog initialized with context: $appContext")

        // Marquer comme initialisé
        isInitialized = true
    }

    // Fournir un accès public au contexte
    fun getContext(): Context {
        if (!isInitialized) {
            throw IllegalStateException("KMPLog has not been initialized. Make sure it is initialized before calling getContext()")
        }
        return appContext
    }
}
