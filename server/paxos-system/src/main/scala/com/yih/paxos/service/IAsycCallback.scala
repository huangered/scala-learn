package com.yih.paxos.service

trait IAsycCallback[T] {
  def doCallback(messageIn: MessageIn[T])
}
