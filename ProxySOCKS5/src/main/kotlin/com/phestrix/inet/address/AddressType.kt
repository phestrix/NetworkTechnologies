package com.phestrix.inet.address

import com.phestrix.features.exceptions.SOCKSException

enum class AddressType(val code: Byte) {
	IPV4(1),
	IPV6(4),
	DOMAIN(3);

	companion object {
		fun getByCode(code: Byte): AddressType =
			values().find { it.code == code } ?: throw SOCKSException("invalid socks address type")
	}
}

