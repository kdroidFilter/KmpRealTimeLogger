package com.kdroid.kmplog.websocket

import com.kdroid.kmplog.core.SERVICE_TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.InetAddress
import javax.jmdns.JmDNS
import javax.jmdns.ServiceEvent
import javax.jmdns.ServiceListener

actual suspend fun getIpService(): String? {
    var ipAddress: String? = null
    withContext(Dispatchers.IO) {

        try {
            // Initialiser JmDNS
            val jmdns = JmDNS.create(InetAddress.getLocalHost())

            // Créer un listener pour la découverte du service
            val listener = object : ServiceListener {
                override fun serviceAdded(event: ServiceEvent) {
                    // Demander les détails complets du service après qu'il ait été ajouté
                    jmdns.requestServiceInfo(event.type, event.name)
                }

                override fun serviceRemoved(event: ServiceEvent) {
                    println("Service retiré : ${event.name}")
                }

                override fun serviceResolved(event: ServiceEvent) {
                    // Le service a été résolu, récupérer l'IP et le port
                    val serviceInfo = event.info
                    val addresses = serviceInfo.inetAddresses
                    if (addresses.isNotEmpty()) {
                        ipAddress = addresses[0].hostAddress
                    }
                }
            }

            // Ajouter un listener pour le type de service spécifique
            jmdns.addServiceListener(SERVICE_TYPE, listener)

            // Réessayer jusqu'à ce que le service soit trouvé
            while (ipAddress == null) {
                Thread.sleep(1000) // Attendre 1 seconde avant de réessayer
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return ipAddress
}