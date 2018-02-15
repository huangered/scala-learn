package com.yih.paxos.transition

import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

class ServerHandler extends ChannelInboundHandlerAdapter {
    override def channelRead(ctx: ChannelHandlerContext, msg: scala.Any): Unit = {
        println("server...")
        ctx.channel().writeAndFlush(msg)
    }
}