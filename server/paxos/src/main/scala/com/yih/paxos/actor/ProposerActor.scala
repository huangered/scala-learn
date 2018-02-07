package com.yih.paxos.actor

import akka.actor.{Actor, ActorRef}
import akka.event.Logging
import com.yih.paxos.Code._
import com.yih.paxos.{Helper, Instance, Propose}

import scala.collection.mutable.Map


class ProposerActor(acceptors: List[ActorRef], learners: List[ActorRef]) extends Actor {
  val log = Logging(context.system, this)

  val meta: Map[Int, Propose] = Map()

  def receive = {
    case (Prepare, id: Int, value: AnyRef) => {
      Helper.addKey(meta, id)
      meta(id).proposeId = Helper.timestamp()
      meta(id).acceptValue = value
      acceptors.foreach(f =>
        f ! (Prepare, id, meta(id).proposeId))
    }
    case (Pok, id: Int) => {
      meta(id).prepareNum += 1
      sendAccept(id)
    }
    case (Pok, id: Int, acceptId: Int, acceptValue: AnyRef) => {
      meta(id).prepareNum += 1
      if (acceptId > meta(id).maxAcceptId) {
        meta(id).acceptValue = acceptValue
      }
      sendAccept(id)
    }
    case (Aok, id: Int) => {
      meta(id).acceptNum += 1
      sendLearner(id)
    }
    case (Error1, id) => {

    }
    case (Error2, id) => {

    }
    case _ => log.info("received unknown message")
  }


  def sendAccept(id: Int): Unit = {
    if (meta(id).prepareNum > (acceptors.size - 1) / 2) {
      acceptors.foreach(f => f ! (Accept, id, meta(id).proposeId, meta(id).acceptValue))
    }
  }

  def sendLearner(id: Int): Unit = {
    if (meta(id).acceptNum > (acceptors.size - 1) / 2) {
      log.info("send to learner")
      learners.foreach(l => l ! (Store, id, meta(id).acceptValue))
    }
  }
}