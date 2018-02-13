package com.yih.paxos.serializer

import java.nio.ByteBuffer

trait ISerializer[T] {
  def serialize(elem: T): ByteBuffer

  def deserialize(buf: ByteBuffer): T
}
