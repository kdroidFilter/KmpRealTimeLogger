package com.kdroid.kmplog

import java.net.InetAddress
import javax.jmdns.JmDNS
import javax.jmdns.ServiceInfo

fun publishMdnsService(serviceName : String, serviceType : String, servicePort : Int, description : String) {
    try {
        val ipAddress = getLocalIpAddress()
        val jmdns = JmDNS.create(InetAddress.getByName(ipAddress))

        val serviceInfo = ServiceInfo.create(serviceType, serviceName, servicePort, "description=$description")

        jmdns.registerService(serviceInfo)
        println("Service mDNS enregistré : $serviceName sur $ipAddress:$servicePort")


    } catch (e: Exception) {
        e.printStackTrace()
    }
}