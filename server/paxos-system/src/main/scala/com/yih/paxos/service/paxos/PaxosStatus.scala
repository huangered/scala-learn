package com.yih.paxos.service.paxos

import com.typesafe.scalalogging.Logger

class PaxosStatus {
    val logger = Logger(classOf[PaxosStatus])

    def doPrepare(prepare: Prepare): PrepareResponse = {
        logger.info("Receive {}", prepare)
        new PrepareResponse(prepare.iid, prepare.proposeId)
    }

    def doAccept(propose: Propose): ProposeResponse = {
        logger.info("Receive {}", propose)
        new ProposeResponse(propose.iid, propose.proposeId)
    }
}