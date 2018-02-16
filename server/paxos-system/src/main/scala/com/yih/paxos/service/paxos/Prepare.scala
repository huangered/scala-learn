package com.yih.paxos.service.paxos

import com.yih.paxos.codec.Icodec
import io.netty.buffer.ByteBuf

object Prepare {
    val codec = new Icodec[Prepare] {
        override def codec(elem: Prepare, buf: ByteBuf): Unit = {
            // buf.writeByte(Verb.Prepare.id)
            buf.writeLong(elem.iid)
            buf.writeLong(elem.proposeId)
        }

        override def decodec(buf: ByteBuf): Prepare = {
            val iid = buf.readLong()
            val pId = buf.readLong()
            new Prepare(iid, pId)
        }
    }
}

class Prepare(override val iid: Long, val proposeId: Long) extends Commit(iid) {

}