package com.kdroid.kmplog

import java.net.NetworkInterface
import java.net.SocketException

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
