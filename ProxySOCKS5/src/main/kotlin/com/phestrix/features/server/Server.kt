package com.phestrix.features.server

import com.phestrix.features.handler.ClientHandler
import com.phestrix.features.remote.RemoteSender
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

class Server(private val port: Int = 8080) {
	private lateinit var server : ServerSocket
	private val clientHandler : ClientHandler = ClientHandler()
	private val remoteSender = RemoteSender()
	 fun runServer(){
		 server = aSocket(ActorSelectorManager(Executors.newCachedThreadPool().asCoroutineDispatcher())).tcp()
			.bind(InetSocketAddress("127.0.0.1", port))
	}
	suspend fun listen(){
		while(true){

		}
	}
}