package com.yih.paxos.actor

import akka.actor.{Actor, ActorRef}
import akka.event.Logging
import com.yih.paxos.Code._
import com.yih.paxos.Instance

class ProposerActor(instance: Instance, acceptors: List[ActorRef], learners:List[ActorRef]) extends Actor {
  val log = Logging(context.system, this)

  var pRespNum = 0

  var aRespNum = 0

  var proposeId = 0;

  var maxAcceptId: Int = 0

  var maxAcceptValue: AnyRef = instance.value

  def receive = {
    case (Prepare, k: Int) => {
      proposeId = k
      acceptors.foreach(f => f ! (Prepare, k))
    }
    case (Pok) => {
      pRespNum += 1
      sendAccept()
    }
    case (Pok, acceptId: Int, acceptValue: AnyRef) => {
      pRespNum += 1
      if (acceptId > maxAcceptId) {
        maxAcceptValue = acceptValue
      }
      sendAccept()
    }
    case (Aok) => {
      aRespNum += 1
      sendLearner()
    }
    case (Error1) => {

    }
    case (Error2) => {

    }
    case _ => log.info("received unknown message")
  }


  def sendAccept(): Unit = {
    if (pRespNum > (acceptors.size - 1) / 2) {
      acceptors.foreach(f => f ! (Accept, proposeId, maxAcceptValue))
    }
  }

  def sendLearner(): Unit = {
    if (aRespNum > (acceptors.size - 1) / 2) {
      log.info("send to learner")
      learners.foreach(l => l ! (Store, maxAcceptValue))
    }
  }
}