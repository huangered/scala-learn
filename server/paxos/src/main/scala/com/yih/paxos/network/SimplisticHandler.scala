package com.yih.paxos.network

import java.nio.charset.Charset

import akka.actor.Actor


class SimplisticHandler extends Actor {

  import akka.io.Tcp._

  def receive = {
    case Received(data) =>
      System.out.println(self.path.toString + ":" + data.decodeString(Charset.defaultCharset()))

      sender() ! Write(data)
    case PeerClosed => context stop self
  }
}
