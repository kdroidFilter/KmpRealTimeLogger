package com.kdroid.kmplog.websocket

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Build
import com.kdroid.androidcontextprovider.ContextProvider
import com.kdroid.kmplog.core.ANDROID_SERVICE_TYPE
import com.kdroid.kmplog.core.SERVICE_NAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual suspend fun getIpOfWebSocketService(): String? {
    val context : Context = ContextProvider.getContext()
    return withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->

            val nsdManager = context.getSystemService(Context.NSD_SERVICE) as? NsdManager
            if (nsdManager == null) {
                continuation.resume(null)
                return@suspendCoroutine
            }

            // State management
            val stateMutex = Mutex()
            var isResolved = false
            var discoveryListener: NsdManager.DiscoveryListener? = null

            // Safe resume with state check
            suspend fun safeResume(value: String?) {
                stateMutex.withLock {
                    if (!isResolved) {
                        isResolved = true
                        discoveryListener?.let { listener ->
                            try {
                                nsdManager.stopServiceDiscovery(listener)
                            } catch (e: IllegalArgumentException) {
                                // Ignore if discovery was already stopped
                            }
                        }
                        continuation.resume(value)
                    }
                }
            }

            discoveryListener = object : NsdManager.DiscoveryListener {
                override fun onDiscoveryStarted(serviceType: String) {
                    // Discovery started
                }

                override fun onServiceFound(serviceInfo: NsdServiceInfo) {
                    if (serviceInfo.serviceName == SERVICE_NAME) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                            nsdManager.registerServiceInfoCallback(
                                serviceInfo,
                                Executors.newSingleThreadExecutor(),  // Using single thread executor for better control
                                object : NsdManager.ServiceInfoCallback {
                                    override fun onServiceUpdated(updatedServiceInfo: NsdServiceInfo) {
                                        val hostAddress = updatedServiceInfo.hostAddresses.firstOrNull()?.hostAddress
                                        kotlinx.coroutines.runBlocking {
                                            safeResume(hostAddress)
                                        }
                                    }

                                    override fun onServiceLost() {
                                        kotlinx.coroutines.runBlocking {
                                            safeResume(null)
                                        }
                                    }

                                    override fun onServiceInfoCallbackRegistrationFailed(errorCode: Int) {
                                        kotlinx.coroutines.runBlocking {
                                            safeResume(null)
                                        }
                                    }

                                    override fun onServiceInfoCallbackUnregistered() {
                                        // Cleanup if needed
                                    }
                                }
                            )
                        } else {
                            nsdManager.resolveService(serviceInfo, object : NsdManager.ResolveListener {
                                override fun onServiceResolved(resolvedServiceInfo: NsdServiceInfo) {
                                    val hostAddress = resolvedServiceInfo.host?.hostAddress
                                    kotlinx.coroutines.runBlocking {
                                        safeResume(hostAddress)
                                    }
                                }

                                override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
                                    kotlinx.coroutines.runBlocking {
                                        safeResume(null)
                                    }
                                }
                            })
                        }
                    }
                }

                override fun onServiceLost(serviceInfo: NsdServiceInfo) {
                    if (serviceInfo.serviceName == SERVICE_NAME) {
                        kotlinx.coroutines.runBlocking {
                            safeResume(null)
                        }
                    }
                }

                override fun onDiscoveryStopped(serviceType: String) {
                    // Discovery stopped
                }

                override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
                    kotlinx.coroutines.runBlocking {
                        safeResume(null)
                    }
                }

                override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
                    kotlinx.coroutines.runBlocking {
                        safeResume(null)
                    }
                }
            }

            try {
                nsdManager.discoverServices(ANDROID_SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener)
            } catch (e: Exception) {
                kotlinx.coroutines.runBlocking {
                    safeResume(null)
                }
            }
        }
    }
}
