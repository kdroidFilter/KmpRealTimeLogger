package com.kdroid.kmplog.client.core.data.network

import com.kdroid.kmplog.client.core.domain.repository.SettingsPreferencesRepository
import com.kdroid.kmplog.core.DEFAULT_SERVICE_PORT
import com.kdroid.kmplog.core.SERICE_DESCRIPTION
import com.kdroid.kmplog.core.SERVICE_NAME
import com.kdroid.kmplog.core.SERVICE_TYPE
import java.net.InetAddress
import java.net.NetworkInterface
import javax.jmdns.JmDNS
import javax.jmdns.ServiceInfo

actual fun publishMdnsService(repository: SettingsPreferencesRepository) {
    try {
        val ipAddress = getLocalIpAddress()
        val jmdns = JmDNS.create(InetAddress.getByName(ipAddress))
        val port = repository.getCustomPort().toIntOrNull() ?: DEFAULT_SERVICE_PORT

        val serviceInfo = ServiceInfo.create(SERVICE_TYPE, SERVICE_NAME, port, "description=$SERICE_DESCRIPTION")

        jmdns.registerService(serviceInfo)
        println("Registered mDNS service: $SERVICE_NAME sur $ipAddress:$port")


    } catch (e: Exception) {
        e.printStackTrace()
    }
}

actual fun getLocalIpAddress(): String? {
    try {
        val networkInterfaces = NetworkInterface.getNetworkInterfaces()
        while (networkInterfaces.hasMoreElements()) {
            val networkInterface = networkInterfaces.nextElement()
            if (!networkInterface.isUp || networkInterface.isLoopback) {
                continue
            }
            val inetAddresses = networkInterface.inetAddresses
            while (inetAddresses.hasMoreElements()) {
                val inetAddress = inetAddresses.nextElement()
                if (!inetAddress.isLoopbackAddress && inetAddress is InetAddress && inetAddress.hostAddress.indexOf(':') < 0) {
                    return inetAddress.hostAddress
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}