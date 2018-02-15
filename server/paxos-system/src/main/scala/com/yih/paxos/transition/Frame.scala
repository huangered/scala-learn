package com.yih.paxos.transition

import com.yih.paxos.codec.Icodec
import io.netty.buffer.ByteBuf


class Frame(val h: Head, val b: Block, val m: Message) {
    val codec = new Icodec[Frame] {
        override def codec(frame: Frame, buf: ByteBuf): Unit = {
            buf.writeByte(frame.h.version)
            buf.writeLong(frame.b.traceId)
            buf.writeInt(frame.b.len)
            //)
        }

        override def decodec(buf: ByteBuf): Frame = {
            null
        }
    }
}

class Head(val version: Byte) {

}

class Block(val traceId: Long, val len: Int) {

}

class Message {

}