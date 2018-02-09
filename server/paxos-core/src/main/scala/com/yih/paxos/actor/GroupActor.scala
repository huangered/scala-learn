package com.yih.paxos.actor

import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging
import com.yih.paxos.model.Role.Role
import com.yih.paxos._
import com.yih.paxos.model.Store

class GroupActor(val roles: List[Role]) extends Actor {
  val log = Logging(context.system, this)

  val learnerActor: ActorRef = context.actorOf(Props(classOf[LearnerActor]))
  val acceptorActor: ActorRef = context.actorOf(Props(classOf[AcceptorActor]))
  val proposerActor: ActorRef = context.actorOf(Props(classOf[ProposerActor]))

  def receive = {

    case Store(iid, value) => {
      log.info("Store instance {} {} ", iid, value)
    }
    case _ => log.info("received unknown message")
  }
}