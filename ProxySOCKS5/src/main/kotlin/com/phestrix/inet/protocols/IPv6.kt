package com.phestrix.inet.protocols

import com.phestrix.inet.InetProtocol
import io.ktor.utils.io.core.*

class IPv6 : InetProtocol() {
	private var address: ByteArray = byteArrayOf()
	override fun getAddressAsString(): String {
		if (address.isEmpty()) return "0.0.0.0"
		else {
			var res: String = ""
			for (i in 0..14) {
				res += "$address."
			}
			res += address[15].toString()
			return res
		}
	}

	override fun setAddress(packet: ByteReadPacket) {
		for (i in 0..15) {
			address[i] = packet.readByte()
		}
	}
}