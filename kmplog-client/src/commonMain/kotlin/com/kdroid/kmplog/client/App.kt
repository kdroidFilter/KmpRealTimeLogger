@file:OptIn(ExperimentalResourceApi::class)

package com.kdroid.kmplog.client

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.kdroid.kmplog.client.data.network.getIpService
import org.jetbrains.compose.resources.ExperimentalResourceApi


@Composable
fun App(context : Any? = null) {
    var ipAddress by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        ipAddress = getIpService(context)
    }

    Scaffold {
        if (ipAddress != null) {
            Text(text = "Adresse IP du service trouvé : $ipAddress")
        } else {
            Text(text = "Recherche du service...")
        }
    }
}


