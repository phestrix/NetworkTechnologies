package com.phestrix

import com.phestrix.features.server.Server
import kotlinx.coroutines.runBlocking


fun main(args: Array<String>?) {
    //val port: Int = args?.get(0)?.toInt() ?: 8080
    val server = Server()
    server.runServer()
    runBlocking {
        server.listen()
    }
}


