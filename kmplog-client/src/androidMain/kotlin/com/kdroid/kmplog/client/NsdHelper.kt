package com.kdroid.kmplog.client

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import com.kdroid.kmplog.core.ANDROID_SERVICE_TYPE
import com.kdroid.kmplog.core.SERVICE_NAME

fun discoverServiceIp(context: Context, serviceName: String = SERVICE_NAME, onResult: (String?) -> Unit) {
    val nsdManager = context.getSystemService(Context.NSD_SERVICE) as NsdManager
    val serviceType = ANDROID_SERVICE_TYPE

    val discoveryListener = object : NsdManager.DiscoveryListener {
        override fun onDiscoveryStarted(serviceType: String) {
            // La découverte a commencé
        }

        override fun onServiceFound(serviceInfo: NsdServiceInfo) {
            if (serviceInfo.serviceName == serviceName) {
                nsdManager.resolveService(serviceInfo, object : NsdManager.ResolveListener {
                    override fun onServiceResolved(resolvedServiceInfo: NsdServiceInfo) {
                        // Le service est résolu, on récupère l'adresse IP
                        val hostAddress = resolvedServiceInfo.host?.hostAddress
                        onResult(hostAddress)
                      //  nsdManager.stopServiceDiscovery()
                    }

                    override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
                        onResult(null)
                    }
                })
            }
        }

        override fun onServiceLost(serviceInfo: NsdServiceInfo) {
            if (serviceInfo.serviceName == serviceName) {
                onResult(null)
            }
        }

        override fun onDiscoveryStopped(serviceType: String) {
            // La découverte est arrêtée
        }

        override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
            nsdManager.stopServiceDiscovery(this)
            onResult(null)
        }

        override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
            nsdManager.stopServiceDiscovery(this)
            onResult(null)
        }
    }

    nsdManager.discoverServices(serviceType, NsdManager.PROTOCOL_DNS_SD, discoveryListener)
}