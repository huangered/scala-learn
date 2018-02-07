package com.yih.paxos.actor

import akka.actor.Actor
import akka.event.Logging
import com.yih.paxos.Code._

class AcceptorActor extends Actor {
  val log = Logging(context.system, this)
  var maxProposeId: Int = 0
  var acceptProposeId: Int = 0
  var acceptValue: AnyRef = null;

  def receive = {
    case (Prepare, id, proposeId: Int) => {
      log.info("Receive prepare {} ", proposeId)
      if (maxProposeId == 0) {
        maxProposeId = proposeId;
        sender() ! (Pok, id)
      } else if (maxProposeId > proposeId) {
        sender() ! (Error1, id)
      } else if (maxProposeId < proposeId) {
        if (acceptProposeId == 0) {
          sender() ! (Pok, id)
        } else {
          sender() ! (Pok, id, acceptProposeId, acceptValue)
        }
      }
    }
    case (Accept, id, proposeId: Int, proposeValue: AnyRef) => {
      log.info("Receive accept {} {}", proposeId, proposeValue)
      if (proposeId >= maxProposeId) {
        acceptProposeId = proposeId
        acceptValue = proposeValue
        sender() ! (Aok, id)
      } else {
        sender() ! (Error2, id)
      }
    }
    case _ => log.info("received unknown message")
  }
}