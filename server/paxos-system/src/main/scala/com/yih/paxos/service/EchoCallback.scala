package com.yih.paxos.service

class EchoCallback extends IAsycCallback [Echo] {
  override def doCallback(messageIn: MessageIn[Echo]): Unit = {
    println("receive heartbeat echo from " + messageIn.remote)
  }
}

class Echo {

}