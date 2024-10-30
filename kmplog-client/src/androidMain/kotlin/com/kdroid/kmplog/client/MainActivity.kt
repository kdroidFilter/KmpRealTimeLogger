package com.kdroid.kmplog.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kdroid.kmplog.core.SERVICE_NAME


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            discoverServiceIp(this, SERVICE_NAME) { ip ->
            /* Gère l'IP ici */
            println(ip)
            }
            App(this.applicationContext)
        }
    }
}


