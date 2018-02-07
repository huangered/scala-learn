package com.yih.paxos

trait Handler {
  def send(packet: Packet): Unit
}
