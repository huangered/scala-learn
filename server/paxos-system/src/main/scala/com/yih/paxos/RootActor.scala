package com.yih.paxos

import java.net.InetSocketAddress

import akka.actor.{Actor, Props}
import akka.event.Logging
import com.yih.paxos.actor.ProposerActor
import com.yih.paxos.network.Send
import com.yih.paxos.network.client.{Client, ListenerActor}

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
      context.actorOf(Props(classOf[Client], new InetSocketAddress(element._1, element._2), listener), "client-" + element._2)

    }
  }

  override def receive = {
    case "test" => proposerActor ! Register1(123, "test")
    case "ddd" => log.info("ggg")
    case _ => log.error("Root unknown")

  }

  class ProposeHandler extends Handler {
    override def send(packet: Packet): Unit = {
      listenerActors.foreach(a => a ! Send(packet))
    }
  }

  class LearnHandler extends Handler {
    override def send(packet: Packet): Unit = {
      listenerActors.foreach(a => a ! Send(packet))
    }
  }

}