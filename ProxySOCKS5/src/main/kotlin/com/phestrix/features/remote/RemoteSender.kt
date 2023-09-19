package com.phestrix.features.remote

import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.internal.*


class RemoteSender{
	//private lateinit var remoteConnection: Socket

	suspend fun sendToRemote(client: Socket, remote: Socket){
		val input = client.openReadChannel()
		val output = remote.openWriteChannel()

		val packet = input.readRemaining()
		output.writePacket(packet)
	}

	suspend fun receiveFromRemote(client: Socket, remote: Socket){
		val input = remote.openReadChannel()
		val output = client.openWriteChannel()
		val packet = input.readRemaining()
		output.writePacket(packet)
	}
}