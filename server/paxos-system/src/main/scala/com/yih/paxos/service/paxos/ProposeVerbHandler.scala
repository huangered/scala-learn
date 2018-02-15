package com.yih.paxos.service.paxos

import com.typesafe.scalalogging.Logger
import com.yih.paxos.service.{IVerbHandler, MessageIn, MessageOut, MessageService}

class ProposeVerbHandler extends IVerbHandler[Propose] {
    val logger = Logger(classOf[ProposeVerbHandler])

    override def doVerb(elem: MessageIn[Propose]): Unit = {
        logger.info("do prepare...")

        val addr = elem.remote
        val ip = addr.getHostString
        val port = addr.getPort

        val proposeResponse = new ProposeResponse(elem.body.iid, elem.body.proposeId)

        val msgOut = new MessageOut[ProposeResponse](12345, proposeResponse)

        MessageService.send(msgOut, (ip, port))
    }
}