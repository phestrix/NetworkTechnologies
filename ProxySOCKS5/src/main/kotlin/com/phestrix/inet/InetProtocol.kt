package com.phestrix.inet

import io.ktor.utils.io.core.*

abstract class InetProtocol {
	abstract fun getAddressAsString(): String
	abstract fun setAddress(packet: ByteReadPacket)
}