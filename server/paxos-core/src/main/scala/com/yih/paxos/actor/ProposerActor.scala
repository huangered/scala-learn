package com.yih.paxos.actor

import akka.actor.Actor
import akka.event.Logging
import com.yih.paxos.handler.ConnectHandler
import com.yih.paxos.model._
import com.yih.paxos.tool.Helper

import scala.collection.mutable.Map


class ProposerActor(size: Int, acceptors: ConnectHandler, learners: ConnectHandler) extends Actor {
  val log = Logging(context.system, this)

  private val meta: Map[Int, ProposeMeta] = Map()

  private val Threshold = (size - 1) / 2

  def receive = {
    case Register1(iid, value) => {
      Helper.addKey(meta, iid)
      meta(iid).proposeId = Helper.timestamp()
      meta(iid).acceptValue = value
      acceptors.send(Prepare(iid, meta(iid).proposeId))
    }
    case Pok(iid) if meta(iid).prepareNum <= Threshold => {
      meta(iid).prepareNum += 1
      sendAccept(iid)
    }
    case Pok2(iid, acceptId, acceptValue)
      if meta(iid).prepareNum <= Threshold => {
      meta(iid).prepareNum += 1
      if (acceptId > meta(iid).maxAcceptId) {
        meta(iid).acceptValue = acceptValue
      }
      sendAccept(iid)
    }
    case Aok(iid) if meta(iid).acceptNum <= Threshold => {
      meta(iid).acceptNum += 1
      sendLearner(iid)
    }
    case Ptimeout(iid) => {

    }
    case Atimeout(iid) => {

    }
    case Error1(iid) => {
      meta(iid).notPrepareNum += 1
    }
    case Error2(iid) => {
      meta(iid).notAcceptNum += 1

    }
    case any => {
      log.info(s"\nreceived unknown message \n$any\n============")
    }
  }


  def sendAccept(iid: Int): Unit = {
    if (meta(iid).prepareNum >= Threshold) {
      acceptors.send(Accept(iid, meta(iid).proposeId, meta(iid).acceptValue))
    } else {
      log.warning(s"not more than threshold $Threshold, ignore")
    }
  }

  def sendLearner(iid: Int): Unit = {
    if (meta(iid).acceptNum >= Threshold) {
      log.info("send value to learner")
      learners.send(Store(iid, meta(iid).acceptValue))
    } else {
      log.warning(s"not more than threshold $Threshold, ignore")
    }
  }
}