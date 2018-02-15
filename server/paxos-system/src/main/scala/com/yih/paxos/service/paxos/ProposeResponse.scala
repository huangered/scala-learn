package com.yih.paxos.service.paxos

import com.yih.paxos.codec.Icodec
import io.netty.buffer.ByteBuf


object ProposeResponse {
    val codec = new Icodec[ProposeResponse] {
        override def codec(elem: ProposeResponse, buf: ByteBuf): Unit = {
            //     buf.writeByte(Verb.ProposeResponse.id)
            buf.writeLong(elem.iid)
            buf.writeLong(elem.proposeId)
        }

        override def decodec(buf: ByteBuf): ProposeResponse = {
            val iid = buf.readLong()
            val pId = buf.readLong()
            new ProposeResponse(iid, pId)
        }
    }
}

class ProposeResponse(override val iid: Long, val proposeId: Long) extends Commit(iid) {

}
