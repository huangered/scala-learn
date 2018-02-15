package com.yih.paxos.transition

import io.netty.bootstrap.Bootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.channel.{ChannelFuture, ChannelInitializer, ChannelOption}


class Client(host: String, port: Int) {

    val workerGroup = new NioEventLoopGroup

    var f: ChannelFuture = null

    def run(): Unit = {
        val workerGroup = new NioEventLoopGroup

        val b = new Bootstrap // (1)
        b.group(workerGroup) // (2)
        b.channel(classOf[NioSocketChannel]) // (3)
        b.option(ChannelOption.SO_KEEPALIVE, Boolean.box(true)) // (4)
        b.handler(new ChannelInitializer[SocketChannel]() {
            @throws[Exception]
            override def initChannel(ch: SocketChannel): Unit = {
                ch.pipeline.addLast(new ClientHandler)
            }
        })
        // Start the client.
        f = b.connect(host, port)
        // Wait until the connection is closed.
    }

    def close(): Unit = {
        try {
            f.channel.closeFuture.sync
        } finally {
            workerGroup.shutdownGracefully()
        }
    }
}
