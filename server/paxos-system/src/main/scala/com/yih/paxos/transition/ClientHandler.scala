package com.yih.paxos.transition

import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

class ClientHandler extends ChannelInboundHandlerAdapter {
    override def channelRead(ctx: ChannelHandlerContext, msg: scala.Any): Unit = {
        println("client...")
    }
}