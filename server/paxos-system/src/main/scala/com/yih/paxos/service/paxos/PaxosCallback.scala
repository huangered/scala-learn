package com.yih.paxos.service.paxos

import java.util.concurrent.CountDownLatch

import com.yih.paxos.service.IAsycCallback

import scala.concurrent.duration.TimeUnit

abstract class PaxosCallback[T](required: Int) extends IAsycCallback {
    val latch = new CountDownLatch(required)

    override def doCallback(messageIn: AnyRef): Unit = {
        val msgIn = messageIn.asInstanceOf[T]
        doCallback0(msgIn)
    }

    def doCallback0(messageIn: T): Unit

    def await(): Unit = {
        latch.await()
    }

    def await(timeout: Long, unit: TimeUnit): Boolean = {
        latch.await(timeout, unit)
    }
}