package com.yih.paxos.transition

import java.util

import io.netty.buffer.{ByteBuf, Unpooled}
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.{MessageToMessageDecoder, MessageToMessageEncoder}


class FrameDecoder extends MessageToMessageDecoder[ByteBuf] {
    override def decode(ctx: ChannelHandlerContext, msg: ByteBuf, out: util.List[AnyRef]): Unit = {

        var frame = new Frame(null, null, null)
        frame = Frame.codec.decodec(msg)
        out.add(frame)
    }
}

class FrameEncoder extends MessageToMessageEncoder[Frame] {
    override def encode(ctx: ChannelHandlerContext, msg: Frame, out: util.List[AnyRef]): Unit = {
        val buf = Unpooled.buffer()
        Frame.codec.codec(msg, buf)
        out.add(buf)
    }
}
