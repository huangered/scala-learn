package com.yih.paxos.transition

import com.yih.paxos.config.PaxosContext
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.{ChannelInitializer, ChannelOption}

class Server(endpoints: List[PaxosContext], port: Int) {

    def run(): Unit = {
        val bossGroup = new NioEventLoopGroup
        val workerGroup = new NioEventLoopGroup
        try {
            val b = new ServerBootstrap
            b.group(bossGroup, workerGroup)
                .channel(classOf[NioServerSocketChannel])
                .childHandler(
                    new ChannelInitializer[SocketChannel]() {
                        override def initChannel(ch: SocketChannel): Unit = {
                            ch.pipeline.addLast(new ServerHandler)
                        }
                    }).option(ChannelOption.SO_BACKLOG, Int.box(128))
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.box(true))

            // Bind and start to accept incoming connections.
            val f = b.bind(port).sync
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel.closeFuture.sync
        } finally {
            workerGroup.shutdownGracefully()
            bossGroup.shutdownGracefully()
        }
    }
}