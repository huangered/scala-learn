package com.yih.paxos.handler

import com.yih.paxos.model.Packet

trait ConnectHandler {
  def send(packet: Packet): Unit
}
