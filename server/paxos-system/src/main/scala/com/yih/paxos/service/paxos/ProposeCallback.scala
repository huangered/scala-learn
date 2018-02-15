package com.yih.paxos.service.paxos

import com.typesafe.scalalogging.Logger

class ProposeCallback(required: Int) extends PaxosCallback[ProposeResponse](required) {

    val logger = Logger(classOf[ProposeCallback])

    override def doCallback0(messageIn: ProposeResponse): Unit = {
        latch.countDown()
        logger.info("Receive propose, count down left {}", latch.getCount)
    }
}
