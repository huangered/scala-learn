package com.yih.paxos.service.paxos

import com.yih.paxos.codec.Icodec
import io.netty.buffer.ByteBuf

object Propose {
    val codec = new Icodec[Propose] {
        override def codec(elem: Propose, buf: ByteBuf): Unit = {
            buf.writeLong(elem.iid)
            buf.writeLong(elem.proposeId)
            buf.writeInt(elem.value)
        }

        override def decodec(buf: ByteBuf): Propose = {
            val iid = buf.readLong()
            val pId = buf.readLong()
            val value = buf.readInt()
            new Propose(iid, pId, value)
        }
    }
}

class Propose(override val iid: Long, val proposeId: Long, val value: Int) extends Commit(iid) {

}