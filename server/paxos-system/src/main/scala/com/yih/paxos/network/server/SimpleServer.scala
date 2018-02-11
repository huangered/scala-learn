package com.yih.paxos.network.server

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef}
import akka.event.Logging
import akka.io.{IO, Tcp}
import akka.util.ByteString
import com.yih.paxos.config.PaxosContext

class SimpleServer(pContext: PaxosContext, handler: ActorRef) extends Actor {
  val log = Logging(context.system, this)


  import Tcp._
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress(pContext.ip, pContext.port))

  def receive = {
    case b@Bound(localAddress) => context.parent ! b

    case CommandFailed(_: Bind) => context stop self

    case c@Connected(remote, local) =>
      log.info(s"Receive connection $remote $local")
      val connection = sender()
      connection ! Register(handler)
      context become {
        case packet: String => connection ! Write(ByteString(packet))
        case any => log.error(s"unknown: $any")
      }
  }
}