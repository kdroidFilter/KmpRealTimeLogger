package com.kdroid.kmplog

import com.kdroid.kmplog.core.SERICE_DESCRIPTION
import com.kdroid.kmplog.core.SERVICE_NAME
import com.kdroid.kmplog.core.SERVICE_PORT
import com.kdroid.kmplog.core.SERVICE_TYPE
import java.net.InetAddress
import javax.jmdns.JmDNS
import javax.jmdns.ServiceInfo

actual fun publishMdnsService() {
    try {
        val ipAddress = getLocalIpAddress()
        val jmdns = JmDNS.create(InetAddress.getByName(ipAddress))

        val serviceInfo = ServiceInfo.create(SERVICE_TYPE, SERVICE_NAME, SERVICE_PORT, "description=$SERICE_DESCRIPTION")

        jmdns.registerService(serviceInfo)
        println("Service mDNS enregistré : $SERVICE_NAME sur $ipAddress:$SERVICE_PORT")


    } catch (e: Exception) {
        e.printStackTrace()
    }
}