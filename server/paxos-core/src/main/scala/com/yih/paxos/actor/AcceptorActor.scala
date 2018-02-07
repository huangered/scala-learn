package com.yih.paxos.actor

import akka.actor.Actor
import akka.event.Logging
import com.yih.paxos.model.AcceptMeta
import com.yih.paxos.{Helper, _}

import scala.collection.mutable.Map


class AcceptorActor extends Actor {
  val log = Logging(context.system, this)

  var meta: Map[Int, AcceptMeta] = Map()

  def receive = {
    case Echo(iid) => {
      sender ! Echo(iid)
    }
    case Prepare(iid, proposeId) => {
      log.info("Receive prepare instance {} propose {} ", iid, proposeId)
      log.info(sender().path.toString)
      Helper.addKey2(meta, iid)
      if (meta(iid).maxProposeId == 0) {
        meta(iid).maxProposeId = proposeId;
        sender ! Pok(iid)
      } else if (meta(iid).maxProposeId > proposeId) {
        sender ! Error1(iid)
      } else if (meta(iid).maxProposeId < proposeId) {
        if (meta(iid).acceptProposeId == 0) {
          sender ! Pok(iid)
        } else {
          sender ! Pok2(iid, meta(iid).acceptProposeId, meta(iid).acceptValue)
        }
      }
    }
    case Accept(id, proposeId, proposeValue) => {
      log.info("Receive accept for instance {} propose {} value {}",
        id, proposeId, proposeValue)
      if (proposeId >= meta(id).maxProposeId) {
        meta(id).acceptProposeId = proposeId
        meta(id).acceptValue = proposeValue
        sender ! Aok(id)
      } else {
        sender ! Error2(id)
      }
    }
    case _ => log.info("received unknown message")
  }
}