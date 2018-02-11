package com.yih.paxos

import java.net.InetSocketAddress

import akka.actor.{Actor, Props}
import akka.event.Logging
import com.yih.paxos.actor.ProposerActor
import com.yih.paxos.config.PaxosContext
import com.yih.paxos.handler.ConnectHandler
import com.yih.paxos.model.{Echo, Packet, Register1}
import com.yih.paxos.network.Send
import com.yih.paxos.network.client.{ListenerActor, SimpleClient}

class RootActor(contexts: List[PaxosContext]) extends Actor {

  val log = Logging(context.system, this)

  val proposerActor = context.actorOf(Props(classOf[ProposerActor], contexts.size, new ProposeHandler, new LearnHandler), "proposer_actor")

  val listenerActors = contexts.map(r => {
    val listener = context.actorOf(Props(classOf[ListenerActor], proposerActor), s"listener-${r.ip}-${r.port}")
    listener
  })

  val clientActors = contexts.zipWithIndex.map {
    case (element, index) => {
      val listener = listenerActors(index)
      context.actorOf(Props(classOf[SimpleClient], new InetSocketAddress(element.ip, element.port), listener), s"client-${element.ip}-${element.port}")

    }
  }

  override def receive = {
    case "echo" => proposerActor ! Echo()
    case "test" => proposerActor ! Register1(123, "test")
    case _ => log.error("Root unknown")

  }

  class ProposeHandler extends ConnectHandler {
    override def send(packet: Packet): Unit = {
      listenerActors.foreach(a => a ! Send(packet))
    }
  }

  class LearnHandler extends ConnectHandler {
    override def send(packet: Packet): Unit = {
      listenerActors.foreach(a => a ! Send(packet))
    }
  }

}