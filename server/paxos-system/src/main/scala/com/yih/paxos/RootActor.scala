package com.yih.paxos

import java.net.InetSocketAddress

import akka.actor.{Actor, Props}
import akka.event.Logging
import com.yih.paxos.actor.ProposerActor
import com.yih.paxos.handler.ConnectHandler
import com.yih.paxos.model.{Echo, Packet, Register1}
import com.yih.paxos.network.Send
import com.yih.paxos.network.client.{SimpleClient, ListenerActor}

class RootActor(addresses: List[(String, Int)]) extends Actor {

  val log = Logging(context.system, this)

  val proposerActor = context.actorOf(Props(classOf[ProposerActor], addresses.size, new ProposeHandler, new LearnHandler), "proposer_actor")

  val listenerActors = addresses.map(r => {
    val listener = context.actorOf(Props(classOf[ListenerActor], proposerActor), "listener-" + r._2)
    listener
  })

  val clientActors = addresses.zipWithIndex.map {
    case (element, index) => {
      val listener = listenerActors(index)
      context.actorOf(Props(classOf[SimpleClient], new InetSocketAddress(element._1, element._2), listener), "client-" + element._2)

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