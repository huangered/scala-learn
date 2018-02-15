package com.yih.paxos.codec

import io.netty.buffer.ByteBuf


trait Icodec[T] {
    def codec(elem: T, buf: ByteBuf): Unit

    def decodec(buf: ByteBuf): T
}