package com.yih.paxos.net

import com.typesafe.scalalogging.Logger
import com.yih.paxos.service._

class EchoVerbHandler extends IVerbHandler[Echo] {
    val logger = Logger(classOf[EchoVerbHandler])

    override def doVerb(elem: MessageIn[Echo]): Unit = {
        logger.info("Receive heartbeat echo from {}", elem.remote)
        if (elem.body.counter > 0) {
            val ip = elem.remote.getHostString
            val port = elem.remote.getPort
            val echo = new Echo(elem.body.timestamp, elem.body.counter - 1)
            MessageService.send(new MessageOut[Echo](elem.uuid, echo), (ip, port))
        } else {

        }
    }
}