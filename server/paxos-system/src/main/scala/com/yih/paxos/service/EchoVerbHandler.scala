package com.yih.paxos.service

class EchoVerbHandler extends IVerbHandler[Echo] {
  override def doVerb(elem: MessageIn[Echo]): Unit = {
    println("receive heartbeat echo from " + elem.remote)

    MessageService.send(new MessageOut[Echo](elem.uuid))
  }
}