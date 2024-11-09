package com.kdroid.kmplog.client.core.data.network

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import com.kdroid.kmplog.client.core.domain.repository.SettingsPreferencesRepository
import com.kdroid.kmplog.core.ANDROID_SERVICE_TYPE
import com.kdroid.kmplog.core.DEFAULT_SERVICE_PORT
import com.kdroid.kmplog.core.SERVICE_NAME
import org.koin.java.KoinJavaComponent.inject
import java.net.NetworkInterface
import java.net.SocketException

actual fun publishMdnsService(repository: SettingsPreferencesRepository) {
    val context : Context by inject(Context::class.java)
    val nsdManager = context.getSystemService(Context.NSD_SERVICE) as NsdManager

    val serviceInfo = NsdServiceInfo().apply {
        serviceName = SERVICE_NAME
        serviceType = ANDROID_SERVICE_TYPE
        port = repository.getCustomPort().toIntOrNull() ?: DEFAULT_SERVICE_PORT

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

actual fun getLocalIpAddress(): String? {
    try {
        val networkInterfaces = NetworkInterface.getNetworkInterfaces()
        while (networkInterfaces.hasMoreElements()) {
            val networkInterface = networkInterfaces.nextElement()
            val addresses = networkInterface.inetAddresses
            while (addresses.hasMoreElements()) {
                val address = addresses.nextElement()
                val hostAddress = address.hostAddress
                if (!address.isLoopbackAddress && hostAddress != null && hostAddress.indexOf(':') < 0) {
                    return hostAddress
                }
            }
        }
    } catch (ex: SocketException) {
        ex.printStackTrace()
    }
    return null
}