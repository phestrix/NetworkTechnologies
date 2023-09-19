package com.phestrix.inet.protocols

import com.phestrix.inet.InetProtocol
import io.ktor.utils.io.core.*

class Domain: InetProtocol() {
	private var address: ByteArray = byteArrayOf()
	private var lengthOfAddress: Byte = 0

	fun setLengthOfAddress(length: Byte) {
		lengthOfAddress = length
	}
	override fun getAddressAsString(): String {
		return address.toString()
	}

	override fun setAddress(packet: ByteReadPacket) {
		for(i in 0..<lengthOfAddress)
			address[i] = packet.readByte()
	}
}