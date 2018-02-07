package com.yih.paxos.actor

import akka.actor.Actor
import akka.event.Logging
import com.yih.paxos.Code._

class LearnerActor extends Actor {
  val log = Logging(context.system, this)

  def receive = {

    case (Store, id:Int, value: AnyRef) => {
      log.info("Receive store {} {} ", id, value)
    }
    case _ => log.info("received unknown message")
  }
}