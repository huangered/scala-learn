package com.yih.paxos.actor

import akka.actor.Actor
import akka.event.Logging
import com.yih.paxos.Code._

class AcceptorActor(instanceId: Int) extends Actor {
  val log = Logging(context.system, this)
  var maxProposeId: Int = 0
  var acceptProposeId: Int = 0
  var acceptValue: AnyRef = null;

  def receive = {
    case (Prepare, proposeId: Int) => {
      log.info("Receive prepare {} ", proposeId)
      if (maxProposeId == 0) {
        maxProposeId = proposeId;
        sender() ! (Pok)
      } else if (maxProposeId > proposeId) {
        sender() ! (Error1)
      } else if (maxProposeId < proposeId) {
        if (acceptProposeId == 0) {
          sender() ! (Pok)
        } else {
          sender() ! (Pok, acceptProposeId, acceptValue)
        }
      }
    }
    case (Accept, proposeId: Int, proposeValue: AnyRef) => {
      log.info("Receive accept {} {}", proposeId, proposeValue)
      if (proposeId >= maxProposeId) {
        acceptProposeId = proposeId
        acceptValue = proposeValue
        sender() ! (Aok)
      } else {
        sender() ! (Error2)
      }
    }
    case _ => log.info("received unknown message")
  }
}