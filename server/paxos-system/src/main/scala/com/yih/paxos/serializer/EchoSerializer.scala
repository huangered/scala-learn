package com.yih.paxos.serializer

import java.nio.ByteBuffer

import com.yih.paxos.service.Echo

class EchoSerializer extends ISerializer[Echo] {
  override def serialize(elem: Echo): ByteBuffer = {
    null
  }

  override def deserialize(buf: ByteBuffer): Echo = {
    null
  }
}
