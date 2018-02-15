package com.yih.paxos.service.paxos

import com.typesafe.scalalogging.Logger
import com.yih.paxos.service.{IVerbHandler, MessageIn, MessageOut, MessageService}

class PrepareVerbHandler extends IVerbHandler[Prepare] {
    val logger = Logger(classOf[PrepareVerbHandler])

    override def doVerb(elem: MessageIn[Prepare]): Unit = {
        logger.info("do prepare...")

        val addr = elem.remote
        val ip = addr.getHostString
        val port = addr.getPort

        val prepareResponse = new PrepareResponse(elem.body.iid, elem.body.proposeId)

        val msgOut = new MessageOut[PrepareResponse](12345, prepareResponse)

        MessageService.send(msgOut, (ip, port))
    }
}
