package com.kdroid.kmplog

import com.kdroid.kmplog.core.SERICE_DESCRIPTION
import com.kdroid.kmplog.core.SERVICE_NAME
import com.kdroid.kmplog.core.SERVICE_PORT
import com.kdroid.kmplog.core.SERVICE_TYPE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.InetAddress
import javax.jmdns.JmDNS
import javax.jmdns.ServiceInfo


actual fun printAndSendLog(priority: Int, tag: String, msg: String) {
   val logMessage = printAndGetLocalLog(tag = tag, message = msg, priority = priority)
    CoroutineScope(Dispatchers.IO).launch {
        sendMessageToWebSocket(logMessage)
    }
}

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