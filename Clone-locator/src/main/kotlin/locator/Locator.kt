package locator

import java.net.DatagramPacket
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.MulticastSocket
import java.net.NetworkInterface

import java.util.*

class Locator {
    private val defaultPort: Int = 12345
    private var groupAddress: String = ""
    private var multicastPort: Int = defaultPort
    private val receiveBuffer = ByteArray(128)
    private val receivePacket = DatagramPacket(receiveBuffer, receiveBuffer.size)
    private val aliveClones = mutableSetOf<String>()

    //TODO enter q to leave
    //
    fun locate(multicastPort: Int, groupAddress: String) {
        this.groupAddress = groupAddress
        this.multicastPort = multicastPort
        val id = generateUniqueID()

        val multicastSocket = MulticastSocket(multicastPort)
        val multicastGroup = InetAddress.getByName(groupAddress)
        val networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost())
        val groupSocketAddress = InetSocketAddress(multicastGroup, multicastPort)
        multicastSocket.joinGroup(groupSocketAddress, networkInterface)
        searchingCycle(id, multicastSocket, generatePacketToSend(id, multicastGroup))
    }

    private fun generateUniqueID(): String {
        return UUID.randomUUID().toString()
    }

    private fun generatePacketToSend(uuid: String, groupAddress: InetAddress): DatagramPacket {
        val sendBuffer = uuid.toByteArray()
        return DatagramPacket(sendBuffer, sendBuffer.size, groupAddress, multicastPort)
    }

    private fun searchingCycle(uuid: String, multicastSocket: MulticastSocket, packetToSend: DatagramPacket) {
        while (true) {
            multicastSocket.send(packetToSend)
            multicastSocket.receive(receivePacket)
            val otherID = String(receivePacket.data, 0, receivePacket.length)
            if (otherID == uuid) continue
            checkClones(otherID)

        }
    }

    private fun checkClones(otherID: String) {
        if (otherID in aliveClones) {
            println("$otherID is still alive")
        } else {
            aliveClones.add(otherID)
            println("$otherID is alive")
        }
    }


}