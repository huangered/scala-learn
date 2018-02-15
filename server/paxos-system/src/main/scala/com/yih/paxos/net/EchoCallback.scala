package com.yih.paxos.net

import com.yih.paxos.service.IAsycCallback

class EchoCallback extends IAsycCallback {
    //  override def doCallback(messageIn: MessageIn[Echo]): Unit = {
    //    println("receive heartbeat echo from " + messageIn.remote)
    //    // todo: refresh servers' status
    //    MessageService.callbacks.remove(messageIn.uuid)
    //  }
    override def doCallback(messageIn: AnyRef): Unit = {

    }
}
