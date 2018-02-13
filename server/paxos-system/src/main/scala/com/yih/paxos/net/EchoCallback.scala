package com.yih.paxos.net

import com.yih.paxos.service.{IAsycCallback, MessageIn}

class EchoCallback extends IAsycCallback [Echo] {
  override def doCallback(messageIn: MessageIn[Echo]): Unit = {
    println("receive heartbeat echo from " + messageIn.remote)
    // todo: refresh servers' status
  }
}
