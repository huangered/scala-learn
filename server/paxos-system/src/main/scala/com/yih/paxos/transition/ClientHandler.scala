package com.yih.paxos.transition

import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter, SimpleChannelInboundHandler}

class ClientHandler extends SimpleChannelInboundHandler[Frame] {
    override def channelRead0(ctx: ChannelHandlerContext, msg: Frame): Unit = {
        println("client...")
        println(msg.getClass)
        println(msg.h.version)
    }
}