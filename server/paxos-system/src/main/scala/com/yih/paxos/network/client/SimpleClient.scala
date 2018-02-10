package com.yih.paxos.network.client

import java.net.InetSocketAddress
import java.nio.charset.Charset

import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging
import akka.io.{IO, Tcp}
import akka.util.ByteString
import com.yih.paxos.network.Receive
import com.yih.paxos.tool.Convertor

object SimpleClient {
  def props(remote: InetSocketAddress, replies: ActorRef) =
    Props(classOf[SimpleClient], remote, replies)
}

class SimpleClient(remote: InetSocketAddress, listener: ActorRef) extends Actor {

  val log = Logging(context.system, this)

  import Tcp._
  import context.system

  IO(Tcp) ! Connect(remote)

  def receive = {
    case CommandFailed(any: Connect) =>
      log.error(s"connect failed $any")
      listener ! "connect failed"
      context stop self

    case c@Connected(remote, local) =>
      log.info(s"client connected $remote, $local")
      listener ! c
      val connection = sender()
      log.error(s"$connection")
      connection ! Register(self)
      context become {
        case data2: String => {
          System.out.print(data2)
          connection ! Write(ByteString(data2))
        }
        case data: ByteString =>
          connection ! Write(data)
        case CommandFailed(w: Write) =>
          // O/S buffer was full
          listener ! "write failed"
        case Received(data) =>
          val str = data.decodeString(Charset.defaultCharset())
          var packet = Convertor.unpack(str)
          listener ! Receive(packet)
        case "close" =>
          connection ! Close
        case _: ConnectionClosed =>
          listener ! "connection closed"
          context stop self
        case any =>
          log.error(s"error in client, $any")
      }
    case any =>
      log.error(s"Client error $any")
  }
}