package com.phestrix.features.exceptions

import io.ktor.utils.io.errors.*

class SOCKSException(message: String, cause: Throwable? = null) : IOException(message, cause)