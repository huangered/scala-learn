package com.yih.paxos.serializer

import java.nio.ByteBuffer

import com.yih.paxos.net.Echo

class EchoSerializer extends ISerializer[Echo] {
  override def serialize(echo: Echo): ByteBuffer = {
    val buf = ByteBuffer.allocate(8)
    buf.asLongBuffer().put(echo.timestamp)
    buf
  }

  override def deserialize(buf: ByteBuffer): Echo = {
    var timestamp = buf.asLongBuffer().get()
    new Echo(timestamp)
  }
}
