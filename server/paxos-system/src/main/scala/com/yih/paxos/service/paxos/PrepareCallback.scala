package com.yih.paxos.service.paxos

import com.typesafe.scalalogging.Logger

class PrepareCallback(required: Int) extends PaxosCallback[PrepareResponse](required) {

    val logger = Logger(classOf[PrepareCallback])

    override def doCallback0(messageIn: PrepareResponse): Unit = {
        latch.countDown()
        logger.info("Receive prepare, count down left {}", latch.getCount)
    }
}
