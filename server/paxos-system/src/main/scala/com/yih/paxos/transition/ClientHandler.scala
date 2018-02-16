package com.yih.paxos.transition

import java.net.InetSocketAddress

import com.typesafe.scalalogging.Logger
import com.yih.paxos.service.MessageService
import com.yih.paxos.service.paxos.Commit
import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}


class ClientHandler extends SimpleChannelInboundHandler[Frame] {
    val logger = Logger(classOf[ClientHandler])

    override def channelRead0(ctx: ChannelHandlerContext, msg: Frame): Unit = {
        val addr = ctx.channel().remoteAddress().asInstanceOf[InetSocketAddress]
        logger.info("Client handler...")
        // check callback
        val traceId = msg.b.traceId

        val commit = msg.m.body.asInstanceOf[Commit]
        logger.info("Commit iid {}", commit.iid)
        val callback = MessageService.callbacks.get(commit.iid)

        callback match {
            case Some(c) =>
                c.doCallback(msg.m.body)
            case None =>
                logger.info("No callback find")
        }
    }


}