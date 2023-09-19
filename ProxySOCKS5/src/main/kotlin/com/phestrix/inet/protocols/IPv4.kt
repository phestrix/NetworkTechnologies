package com.phestrix.inet.protocols

import com.phestrix.inet.InetProtocol
import io.ktor.utils.io.core.*

class IPv4 : InetProtocol() {
	private var address: ByteArray = byteArrayOf()

	override fun setAddress(packet: ByteReadPacket) {
		for (i in 0..3) {
			address[i] = packet.readByte()
		}
	}

	override fun getAddressAsString(): String {
		if (address.isEmpty()) return "0.0.0.0"
		else {
			var res: String = ""
			for (i in 0..2) {
				res += "$address."
			}
			res += address[3].toString()
			return res
		}
	}

}