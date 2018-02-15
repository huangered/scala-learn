package com.yih.paxos.transition

import java.net.InetSocketAddress

import com.typesafe.scalalogging.Logger
import com.yih.paxos.net.{Echo, EchoVerbHandler}
import com.yih.paxos.service.paxos.{Prepare, PrepareVerbHandler, Propose, ProposeVerbHandler}
import com.yih.paxos.service.{ConnectionService, MessageIn, Verb}
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}

@Sharable
class ServerHandler extends SimpleChannelInboundHandler[Frame] {
    val logger = Logger(classOf[ServerHandler])

    override def channelActive(ctx: ChannelHandlerContext): Unit = {
        super.channelActive(ctx)
        val addr = ctx.channel().remoteAddress().asInstanceOf[InetSocketAddress]
        val ip = addr.getHostString
        val port = addr.getPort
        val key = s"$ip:$port"
        ConnectionService.connections.put(key, ctx.channel())
    }

    override def channelRead0(ctx: ChannelHandlerContext, frame: Frame): Unit = {
        val addr = ctx.channel().remoteAddress().asInstanceOf[InetSocketAddress]
        logger.info("server...")
        logger.info("frame type {}", frame.m.verb)
        frame.m.verb match {
            case Verb.Echo => {
                val handler = new EchoVerbHandler
                handler.doVerb(new MessageIn[Echo](12345, addr, frame.m.body.asInstanceOf[Echo]))
            }
            case Verb.Prepare => {
                val handler = new PrepareVerbHandler
                handler.doVerb(new MessageIn[Prepare](12345, addr, frame.m.body.asInstanceOf[Prepare]))
            }
            case Verb.Propose => {
                val handler = new ProposeVerbHandler
                handler.doVerb(new MessageIn[Propose](12345, addr, frame.m.body.asInstanceOf[Propose]))
            }
        }
    }
}