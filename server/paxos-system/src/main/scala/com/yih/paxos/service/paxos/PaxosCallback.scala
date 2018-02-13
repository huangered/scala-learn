package com.yih.paxos.service.paxos

import java.util.concurrent.CountDownLatch

import com.yih.paxos.service.IAsycCallback

import scala.concurrent.duration.TimeUnit

abstract class PaxosCallback[T](required:Int) extends IAsycCallback[T] {
  val latch = new CountDownLatch(required)

  def await(): Unit = {
    latch.await()
  }

  def await(timeout: Long, unit: TimeUnit): Boolean = {
    latch.await(timeout, unit)
  }
}
