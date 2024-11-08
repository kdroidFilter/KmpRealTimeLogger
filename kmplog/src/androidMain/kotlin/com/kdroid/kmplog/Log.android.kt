package com.kdroid.kmplog

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import com.kdroid.androidcontextprovider.ContextProvider
import com.kdroid.kmplog.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

actual fun Log.v(tag: String, msg: String) {
    if (isLoggable(tag, VERBOSE)) {
        android.util.Log.v(tag, msg)
        sendLog(VERBOSE, tag, msg)
    }
}

actual fun Log.d(tag: String, msg: String) {
    if (isLoggable(tag, DEBUG)) {
        android.util.Log.d(tag, msg)
        sendLog(DEBUG, tag, msg)
    }
}

actual fun Log.i(tag: String, msg: String) {
    if (isLoggable(tag, INFO))  {
        android.util.Log.i(tag, msg)
        sendLog(INFO, tag, msg)
    }
}

actual fun Log.w(tag: String, msg: String) {
    if (isLoggable(tag, WARN)) {
        android.util.Log.w(tag, msg)
        sendLog(WARN, tag, msg)
    }
}

actual fun Log.e(tag: String, msg: String, throwable: Throwable?) {
    if (isLoggable(tag, ERROR)) {
        if (throwable != null) {
            android.util.Log.e(tag, msg, throwable)
            sendLog(ERROR, tag, msg + " " + throwable.localizedMessage)
        } else {
            android.util.Log.e(tag, msg)
            sendLog(ERROR, tag, msg)
        }
    }
}

actual fun Log.wtf(tag: String, msg: String) {
    if (isLoggable(tag, ASSERT)) {
        android.util.Log.wtf(tag, msg)
        sendLog(ASSERT, tag, msg)
    }
}

actual fun Log.println(priority: Int, tag: String, msg: String) {
    if (isLoggable(tag, priority)) android.util.Log.println(priority, tag, msg)
}

actual fun printAndSendLog(priority: Int, tag: String, msg: String) {

}

private fun sendLog(priority: Int, tag: String, msg: String, throwable: String? = null){
    CoroutineScope(Dispatchers.Main.immediate).launch {
        sendMessageToWebSocket(LogMessage(priority = priority, tag = tag, timestamp = getCurrentDateTime(), message = msg))
    }
}

actual fun publishMdnsService() {
    val context = ContextProvider.getContext()

    val nsdManager = context.getSystemService(Context.NSD_SERVICE) as NsdManager

    val serviceInfo = NsdServiceInfo().apply {
        serviceName = SERVICE_NAME
        serviceType = ANDROID_SERVICE_TYPE
        port = DEFAULT_SERVICE_PORT
    }

    val registrationListener = object : NsdManager.RegistrationListener {
        override fun onServiceRegistered(nsdServiceInfo: NsdServiceInfo) {
            println("Service mDNS enregistré : ${nsdServiceInfo.serviceName} sur le port ${nsdServiceInfo.port}")
        }

        override fun onRegistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            println("Échec de l'enregistrement du service : erreur $errorCode")
        }

        override fun onServiceUnregistered(nsdServiceInfo: NsdServiceInfo) {
            println("Service mDNS désenregistré : ${nsdServiceInfo.serviceName}")
        }

        override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            println("Échec du désenregistrement du service : erreur $errorCode")
        }
    }

    nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener)
}