package com.yih.paxos.net

import com.yih.paxos.codec.Icodec
import com.yih.paxos.service.Verb
import io.netty.buffer.ByteBuf

object Echo {
    val codec = new Icodec[Echo] {
        override def codec(elem: Echo, buf: ByteBuf): Unit = {
            buf.writeByte(Verb.Echo.id)
            buf.writeLong(elem.timestamp)
            buf.writeInt(elem.counter)
        }

        override def decodec(buf: ByteBuf): Echo = {
            //            buf.readByte()
            val timestamp = buf.readLong()
            val counter = buf.readInt()
            new Echo(timestamp, counter)
        }
    }
}

class Echo(val timestamp: Long, val counter: Int) {

}