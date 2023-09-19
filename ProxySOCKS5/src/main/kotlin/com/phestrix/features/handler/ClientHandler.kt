package com.phestrix.features.handler

import com.phestrix.features.exceptions.SOCKSException
import com.phestrix.inet.protocols.IPv4
import com.phestrix.inet.address.AddressType
import com.phestrix.inet.address.AddressType.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*


class ClientHandler {
	private val commandsArray: ByteArray = byteArrayOf(0x01, 0x02, 0x03)
	private val addressesArray: ByteArray = byteArrayOf(0x01, 0x03, 0x04)
	private val IPv4 = IPv4()


	private val SUCCESS: Byte = 0x00

	suspend fun handle(clientSocket: Socket) {
		val inputChannel = clientSocket.openReadChannel()
		if (!checkBytesFromGreeting(
				version = inputChannel.readByte(),
				nauth = inputChannel.readByte(),
				auth = inputChannel.readByte()
			)
		) {
			clientSocket.close()
			throw SOCKSException("not compatible args from client")
		}
		val outputChannel = clientSocket.openWriteChannel()
		sendGreetingFromServer(outputChannel)
		try {
			handleConnectionRequest(inputChannel)
		} catch (e: SOCKSException) {
			clientSocket.close()
		}
	}

	private suspend fun handleConnectionRequest(inputChannel: ByteReadChannel) {
		val version = inputChannel.readByte()
		val command = inputChannel.readByte()
		val rsv = inputChannel.readByte()
		if (!checkBytesFromConnectionRequest(version, command, rsv)) {
			throw SOCKSException("not compatible args from client")
		}


	}

	private fun resolveAddress(addressType: AddressType, inputChannel: ByteReadChannel){

	}

	private fun getAddressLength(code: Byte,): Int {
		return when(AddressType.getByCode(code)){
			IPV4 -> 4
			IPV6 -> 16
			DOMAIN -> code.toInt()
		}
	}

	private suspend fun sendGreetingFromServer(outChannel: ByteWriteChannel) {
		outChannel.writeByte(0x05)
		outChannel.writeByte(0x00)
	}

	private fun checkBytesFromGreeting(version: Byte, nauth: Byte, auth: Byte): Boolean {
		return version == VERSION && nauth == NAUTH && auth == NOAUTH
	}

	private fun checkBytesFromConnectionRequest(version: Byte, command: Byte, rsv: Byte): Boolean {
		return version == VERSION && commandsArray.contains(command) && rsv == RSV
	}


	companion object {
		private const val VERSION: Byte = 0x05
		private const val NAUTH: Byte = 0x01
		private const val NOAUTH: Byte = 0x00
		private const val RSV: Byte = 0x00
	}
}