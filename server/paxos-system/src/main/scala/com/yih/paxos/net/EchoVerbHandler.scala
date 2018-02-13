package com.yih.paxos.net

import com.yih.paxos.service._

class EchoVerbHandler extends IVerbHandler[Echo] {
  override def doVerb(elem: MessageIn[Echo]): Unit = {
    println("receive heartbeat echo from " + elem.remote)

    MessageService.send(new MessageOut[Echo](elem.uuid))
  }
}