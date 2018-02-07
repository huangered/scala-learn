package com.yih.paxos.actor

import akka.actor.Actor
import akka.event.Logging
import com.yih.paxos._

class LearnerActor extends Actor {
  val log = Logging(context.system, this)

  def receive = {

    case Store(iid, value) => {
      log.info("Store instance {} {} ", iid, value)
    }
    case _ => log.info("received unknown message")
  }
}