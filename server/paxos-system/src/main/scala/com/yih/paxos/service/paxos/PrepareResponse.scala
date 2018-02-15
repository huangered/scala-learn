package com.yih.paxos.service.paxos

import com.yih.paxos.codec.Icodec
import io.netty.buffer.ByteBuf


object PrepareResponse {
    val codec = new Icodec[PrepareResponse] {
        override def codec(elem: PrepareResponse, buf: ByteBuf): Unit = {
            //buf.writeByte(Verb.PrepareResponse.id)
            buf.writeLong(elem.iid)
            buf.writeLong(elem.proposeId)
        }

        override def decodec(buf: ByteBuf): PrepareResponse = {
            val iid = buf.readLong()
            val pId = buf.readLong()
            new PrepareResponse(iid, pId)
        }
    }
}

class PrepareResponse(override val iid: Long, val proposeId: Long) extends Commit(iid) {

}
