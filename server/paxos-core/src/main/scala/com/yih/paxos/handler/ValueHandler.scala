package com.yih.paxos.handler

trait ValueHandler {
  def deal(value: AnyRef): Unit
}
