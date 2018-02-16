package com.yih.paxos.transition

import com.yih.paxos.codec.Icodec
import com.yih.paxos.net.Echo
import com.yih.paxos.service.Verb
import com.yih.paxos.service.Verb.Verb
import com.yih.paxos.service.paxos.{Prepare, PrepareResponse, Propose, ProposeResponse}
import io.netty.buffer.ByteBuf

object Frame {
    val codec = new Icodec[Frame] {
        override def codec(frame: Frame, buf: ByteBuf): Unit = {
            buf.writeByte(frame.h.version)
            buf.writeLong(frame.b.traceId)
            buf.writeInt(frame.b.len)
            buf.writeByte(frame.m.verb.id)
            frame.m.verb match {
                case Verb.Echo => {
                    val echo = frame.m.body.asInstanceOf[Echo]
                    Echo.codec.codec(echo, buf)
                }
                case Verb.Prepare => {
                    val p = frame.m.body.asInstanceOf[Prepare]
                    Prepare.codec.codec(p, buf)
                }
                case Verb.PrepareResponse => {
                    val pp = frame.m.body.asInstanceOf[PrepareResponse]
                    PrepareResponse.codec.codec(pp, buf)
                }
                case Verb.Propose => {
                    val pp = frame.m.body.asInstanceOf[Propose]
                    Propose.codec.codec(pp, buf)
                }
                case Verb.ProposeResponse => {
                    val pp = frame.m.body.asInstanceOf[ProposeResponse]
                    ProposeResponse.codec.codec(pp, buf)
                }
                case _ => {
                    println("unknown")
                }
            }

        }

        override def decodec(buf: ByteBuf): Frame = {
            val head = new Head(buf.readByte())
            val block = new Block(buf.readLong(), buf.readInt())

            val verb = Verb.apply(buf.readByte())
            val message = verb match {
                case Verb.Echo =>
                    val echo = Echo.codec.decodec(buf)
                    new Message(Verb.Echo, echo)
                case Verb.Prepare =>
                    val prepare = Prepare.codec.decodec(buf)
                    new Message(Verb.Prepare, prepare)
                case Verb.PrepareResponse =>
                    val pp = PrepareResponse.codec.decodec(buf)
                    new Message(Verb.PrepareResponse, pp)
                case Verb.Propose =>
                    val p = Propose.codec.decodec(buf)
                    new Message(Verb.Propose, p)
                case Verb.ProposeResponse =>
                    val pr = ProposeResponse.codec.decodec(buf)
                    new Message(Verb.ProposeResponse, pr)
                case _ =>
                    println("unknown")
                    new Message(Verb.Unknown, null)
            }
            new Frame(head, block, message)
        }
    }


}

class Frame(val h: Head, val b: Block, val m: Message) {
}

class Head(val version: Byte) {

}

class Block(val traceId: Long, val len: Int) {

}

class Message(val verb: Verb, val body: AnyRef) {
}
