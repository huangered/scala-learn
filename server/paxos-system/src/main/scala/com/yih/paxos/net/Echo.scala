package com.yih.paxos.net

import com.yih.paxos.codec.Icodec
import com.yih.paxos.service.Verb
import io.netty.buffer.ByteBuf

class Echo(val timestamp: Long) {
    val codec = new Icodec[Echo] {
        override def codec(elem: Echo, buf: ByteBuf): Unit = {
            buf.writeByte(Verb.Echo.id)
            buf.writeLong(timestamp)
        }

        override def decodec(buf: ByteBuf): Echo = {
            buf.readByte()
            val timestamp = buf.readLong()
            new Echo(timestamp)
        }
    }
}